package videolibrary.street.quality.qualityshow.utils;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.fragments.ProfilFragment;
import videolibrary.street.quality.qualityshow.fragments.RecommandationsFragment;
import videolibrary.street.quality.qualityshow.fragments.SettingsFragment;


public class DrawerMenuUtils implements Drawer.OnDrawerItemClickListener {

    AccountHeader accountHeader;
    Drawer drawer;

    Activity activity;
    User user;
    Toolbar toolbar;
    Bundle savedInstanceState;


    public DrawerMenuUtils(Bundle savedInstanceState, Activity activity, Toolbar toolbar) {
        this.savedInstanceState = savedInstanceState;
        this.activity = activity;
        this.toolbar = toolbar;
        this.user = QualityShowApplication.getUserHelper().getCurrentUser();

        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
        }

        setAccountHeader();
        setDrawer();
    }

    private void setAccountHeader() {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.grayQ)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getUsername()).withEmail(user.getEmail())
                )
                .build();
    }

    public void setDrawer() {
        PrimaryDrawerItem profil = new PrimaryDrawerItem().withName("Profile");
        SecondaryDrawerItem planning = new SecondaryDrawerItem().withName("Agenda");
        SecondaryDrawerItem recommandations = new SecondaryDrawerItem().withName("Explore");
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Settings");

        SecondaryDrawerItem login;

        if (user.getUsername() != "Anonyme") {
            login = new SecondaryDrawerItem().withName("Log out");
        } else {
            login = new SecondaryDrawerItem().withName("Log in");
        }

        drawer = new DrawerBuilder().withActivity(activity).withToolbar(toolbar)
                .addDrawerItems(
                        profil,
                        planning,
                        recommandations,
                        new DividerDrawerItem(),
                        settings,
                        login
                ).withSavedInstance(savedInstanceState)
                .withAccountHeader(accountHeader)
                .withOnDrawerItemClickListener(this)
                .build();
    }


    public Drawer getDrawer(){
        return drawer;
    }
    @Override
    public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
        switch (position) {
            case 1:
               /*ProfilFragment profilFragment = new ProfilFragment();
                FragmentTransaction profilTransaction = getFragmentManager().beginTransaction();
                profilTransaction.add(R.id.frame_container, profilFragment);
                     profilTransaction.addToBackStack(null);
                profilTransaction.commit();*/
                break;
            case 2:
                //planning
                break;
            case 3:
                /*RecommandationsFragment recommandationsFragment = new RecommandationsFragment();
                FragmentTransaction recommandationsTransaction = getFragmentManager().beginTransaction();
                recommandationsTransaction.add(R.id.frame_container, recommandationsFragment);
                     recommandationsTransaction.addToBackStack(null);
                recommandationsTransaction.commit();*/
                break;
            case 4:
                /*SettingsFragment settingsFragment = new SettingsFragment();
                FragmentTransaction settingsTransaction = getFragmentManager().beginTransaction();
                settingsTransaction.add(R.id.frame_container, settingsFragment);
                      settingsTransaction.addToBackStack(null);
                settingsTransaction.commit();*/
                break;
            case 5:
                // se déconnecter ou se connecter (y)
                break;
            default:
                return false;
        }
        return false;
    }
}
