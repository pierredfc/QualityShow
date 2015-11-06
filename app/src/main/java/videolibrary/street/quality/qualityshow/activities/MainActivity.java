package videolibrary.street.quality.qualityshow.activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import java.util.List;

import videolibrary.street.quality.qualityshow.CustomSearchView;
import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.async.CalendarRequestAsyncTask;
import videolibrary.street.quality.qualityshow.fragments.HomeFragment;
import videolibrary.street.quality.qualityshow.fragments.ProfilFragment;
import videolibrary.street.quality.qualityshow.fragments.RecommandationsFragment;
import videolibrary.street.quality.qualityshow.fragments.SearchFragment;
import videolibrary.street.quality.qualityshow.fragments.SettingsFragment;
import videolibrary.street.quality.qualityshow.listeners.CalendarListener;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;

public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, UserListener, ClickListener, CalendarListener {

    private Toolbar toolbar;
    private User user;
    private CustomSearchView searchView;

    private HomeFragment homeFragment;

    private ProfilFragment profilFragment;
    private RecommandationsFragment recommandationsFragment;
    private SettingsFragment settingsFragment;
    private SearchFragment searchFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");


        user = QualityShowApplication.getUserHelper().getCurrentUser();
        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
        }

        setDrawer(savedInstanceState);

        homeFragment = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        handleIntent(getIntent());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        searchView = (CustomSearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setActivity(this);
        searchView.setIconified(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_menu_settings:
                Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.deconnexion:
                QualityShowApplication.getUserHelper().logout(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
        switch (position) {
            case 1:
                profilFragment = new ProfilFragment();
                FragmentTransaction profilTransaction = getFragmentManager().beginTransaction();
                profilTransaction.add(R.id.frame_container, profilFragment);
           //     profilTransaction.addToBackStack(null);
                profilTransaction.commit();
                return true;
            case 2:
                //planning
                break;
            case 3:
                recommandationsFragment = new RecommandationsFragment();
                FragmentTransaction recommandationsTransaction = getFragmentManager().beginTransaction();
                recommandationsTransaction.add(R.id.frame_container, recommandationsFragment);
           //     recommandationsTransaction.addToBackStack(null);
                recommandationsTransaction.commit();
                return true;
            case 4:
                settingsFragment = new SettingsFragment();
                FragmentTransaction settingsTransaction = getFragmentManager().beginTransaction();
                settingsTransaction.add(R.id.frame_container, settingsFragment);
          //      settingsTransaction.addToBackStack(null);
                settingsTransaction.commit();
                return true;
            case 5:
                // se déconnecter
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
        finish();
    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void userIsRetrieved(User user) {

    }

    @Override
    public void onError(Throwable t) {

    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
          /*  if (searchFragment != null) {
                transaction.remove(searchFragment);
            }*/
       /*     if (homeFragment != null) {
                transaction.remove(homeFragment);
            }*/

            searchFragment = SearchFragment.newInstance(query);
            transaction.add(R.id.frame_container, searchFragment);
            transaction.addToBackStack("searchFragment");
            transaction.commit();
        }
    }



    @Override
    public void onItemClick(Object item) {
        Intent intent=new Intent(this,ShowActivity.class);
        if (item instanceof Serie) {
            intent.putExtra("isMovie",false);
            intent.putExtra("show",(Serie) item);
        }
        if (item instanceof Film) {
            intent.putExtra("isMovie", true);
            intent.putExtra("show", (Film) item);
        }
        startActivity(intent);
    }

    private void setDrawer(Bundle savedInstanceState) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.purple)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getUsername()).withEmail(user.getEmail())
                )
                .build();

        PrimaryDrawerItem profil = new PrimaryDrawerItem().withName("Profil");
        SecondaryDrawerItem planning = new SecondaryDrawerItem().withName("Mon planning");
        SecondaryDrawerItem recommandations = new SecondaryDrawerItem().withName("Recommandations");
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Réglages");

        SecondaryDrawerItem login;

        if (user.getUsername() != "Anonyme") {
            login = new SecondaryDrawerItem().withName("Se déconnecter");
        } else {
            login = new SecondaryDrawerItem().withName("Se connecter");
        }

        Drawer result = new DrawerBuilder().withActivity(this).withToolbar(toolbar)
                .addDrawerItems(
                        profil,
                        planning,
                        recommandations,
                        new DividerDrawerItem(),
                        settings,
                        login
                ).withSavedInstance(savedInstanceState)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(this)
                .build();

        result.setSelection(planning);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            askLeaveOrLogout();
        }
    }

    private void askLeaveOrLogout(){
        Toast.makeText(QualityShowApplication.getContext(), "askLeaveOrLogout", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarRequestReceived(List<String> response) {

    }
}
