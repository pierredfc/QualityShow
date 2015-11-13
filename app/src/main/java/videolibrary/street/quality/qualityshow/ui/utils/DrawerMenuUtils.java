package videolibrary.street.quality.qualityshow.ui.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ExploreActivity;
import videolibrary.street.quality.qualityshow.activities.LoginActivity;
import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.activities.ProfileActivity;
import videolibrary.street.quality.qualityshow.activities.SettingsActivity;
import videolibrary.street.quality.qualityshow.activities.StartActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;


public class DrawerMenuUtils implements Drawer.OnDrawerItemClickListener, UserListener {

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
                        new ProfileDrawerItem().withName(user.getUsername()).withEmail(user.getEmail()).withIcon(R.drawable.iconprofile)
                )
                .withSelectionListEnabledForSingleProfile(false)
                .build();
    }

    public void setDrawer() {
        PrimaryDrawerItem profil = new PrimaryDrawerItem().withName("Profile").withIcon(CommunityMaterial.Icon.cmd_account_circle);
        SecondaryDrawerItem planning = new SecondaryDrawerItem().withName("Agenda").withIcon(CommunityMaterial.Icon.cmd_calendar);
        SecondaryDrawerItem recommandations = new SecondaryDrawerItem().withName("Explore").withIcon(CommunityMaterial.Icon.cmd_compass);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Settings").withIcon(CommunityMaterial.Icon.cmd_settings);

        SecondaryDrawerItem login;

        if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
            login = new SecondaryDrawerItem().withName("Log in").withIcon(CommunityMaterial.Icon.cmd_login);
        } else {
            login = new SecondaryDrawerItem().withName("Log out").withIcon(CommunityMaterial.Icon.cmd_logout);
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

        if(activity instanceof MainActivity){
            drawer.setSelection(planning, false);
        } else if (activity instanceof ProfileActivity){
            drawer.setSelection(profil, false);
        } else if(activity instanceof ExploreActivity){
            drawer.setSelection(recommandations, false);
        }
    }

    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
        switch (position) {
            case 1:
                    Intent profileIntent = new Intent(QualityShowApplication.getContext(), ProfileActivity.class);
                    activity.startActivity(profileIntent);
                break;
            case 2:
                Intent agendaIntent = new Intent(QualityShowApplication.getContext(), MainActivity.class);
                activity.startActivity(agendaIntent);
                break;
            case 3:
                Intent exploreIntent = new Intent(QualityShowApplication.getContext(), ExploreActivity.class);
                activity.startActivity(exploreIntent);
                break;
            case 5:
                Intent settingsIntent = new Intent(QualityShowApplication.getContext(), SettingsActivity.class);
                activity.startActivity(settingsIntent);
                break;
            case 6:
                if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
                    Intent loginIntent = new Intent(QualityShowApplication.getContext(), LoginActivity.class);
                    activity.startActivity(loginIntent);
                } else {
                    QualityShowApplication.getUserHelper().logout(this);
                }
                break;
            default:
                return false;
        }
        return false;
    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {

    }

    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    @Override
    public void userIsCreated(boolean user) {

    }

    @Override
    public void userIsLogout() {
        Intent intent = new Intent(QualityShowApplication.getContext(), StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void userIsRetrieved(User user) {

    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(QualityShowApplication.getContext(), "No connected account ?", Toast.LENGTH_LONG).show();
    }
}
