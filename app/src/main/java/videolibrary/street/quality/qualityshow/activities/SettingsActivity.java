package videolibrary.street.quality.qualityshow.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
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
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.fragments.AccountFragment;
import videolibrary.street.quality.qualityshow.fragments.NotificationFragment;


public class SettingsActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, UserListener {

    private Toolbar toolbar;
    private Drawer drawer;

    private TextView account;
    private TextView notifications;

    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.settings);

        buildDrawer(savedInstanceState);

        account = (TextView) findViewById(R.id.settings_account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountFragment = new AccountFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.settings_frame_container, accountFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        notifications = (TextView) findViewById(R.id.settings_notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationFragment = new NotificationFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.settings_frame_container, notificationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void buildDrawer(Bundle savedInstanceState) {
        PrimaryDrawerItem profil = new PrimaryDrawerItem().withName("Profile").withIcon(CommunityMaterial.Icon.cmd_account_circle);
        SecondaryDrawerItem planning = new SecondaryDrawerItem().withName("Agenda").withIcon(CommunityMaterial.Icon.cmd_calendar);
        SecondaryDrawerItem recommandations = new SecondaryDrawerItem().withName("Explore").withIcon(CommunityMaterial.Icon.cmd_compass);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Settings").withIcon(CommunityMaterial.Icon.cmd_settings);

        SecondaryDrawerItem login;
        User user = QualityShowApplication.getUserHelper().getCurrentUser();


        if (user == null) {
            login = new SecondaryDrawerItem().withName("Log in").withIcon(CommunityMaterial.Icon.cmd_login);
            user = new User();
            user.setUsername("Anonyme");
            user.setEmail("anonyme@qualityshow.fr");
        } else {
            login = new SecondaryDrawerItem().withName("Log out").withIcon(CommunityMaterial.Icon.cmd_logout);
        }

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.grayQ)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getUsername()).withEmail(user.getEmail()).withIcon(R.drawable.iconprofile)
                )
                .withSelectionListEnabledForSingleProfile(false)
                .build();


        drawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar)
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
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View view) {
                        onBackPressed();
                        return true;
                    }
                })
                .build();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    //change to back arrow
                    drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    //if you dont want the drawer to be opened in Fragment
                    drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                } else {
                    //change to hamburger icon

                    drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    //call this method to display hamburger icon
                    drawer.getActionBarDrawerToggle().syncState();

                    drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }
        });

        drawer.setSelection(settings, false);
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        switch (i) {
            case 1:
                Intent profileIntent = new Intent(QualityShowApplication.getContext(), ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case 2:
                Intent agendaIntent = new Intent(QualityShowApplication.getContext(), MainActivity.class);
                startActivity(agendaIntent);
                break;
            case 3:
                Intent exploreIntent = new Intent(QualityShowApplication.getContext(), ExploreActivity.class);
                startActivity(exploreIntent);
                break;
            case 5:
                Intent settingsIntent = new Intent(QualityShowApplication.getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case 6:
                if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
                    Intent loginIntent = new Intent(QualityShowApplication.getContext(), LoginActivity.class);
                    startActivity(loginIntent);
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
        super.finish();
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
