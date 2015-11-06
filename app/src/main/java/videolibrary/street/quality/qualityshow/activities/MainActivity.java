package videolibrary.street.quality.qualityshow.activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.fragments.HomeFragment;
import videolibrary.street.quality.qualityshow.fragments.SearchFragment;
import videolibrary.street.quality.qualityshow.listeners.CalendarListener;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.DrawerMenuUtils;
import videolibrary.street.quality.qualityshow.utils.Requests;

public class MainActivity extends AppCompatActivity implements UserListener, ClickListener, CalendarListener, RequestListener {

    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    public DrawerMenuUtils drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = new DrawerMenuUtils(savedInstanceState, this, toolbar);
        drawer.getDrawer().setSelection(2);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setVoiceSearch(true);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(QualityShowApplication.getContext(), SearchActivity.class);
                Bundle extras = new Bundle();
                extras.putString(getString(R.string.query), query);
                intent.putExtras(extras);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        homeFragment = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //handleIntent(getIntent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

   /*     searchView = (CustomSearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setActivity(this);
        searchView.setIconified(true);*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_menu_about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            searchFragment = SearchFragment.newInstance(query);
            transaction.add(R.id.frame_container, searchFragment);
            transaction.addToBackStack("searchFragment");
            transaction.commit();
        }
    }


    @Override
    public void onItemClick(Object item) {

        if (item instanceof Serie) {

            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.SERIE_FIND, String.valueOf(((Serie) item).getIds().get("slug")));

        }
        if (item instanceof Film) {
            //intent.putExtra("isMovie",true);
            //intent.putExtra("show",(Film) item);
        }

    }

    @Override
    public void onBackPressed() {
        int entryCount = getFragmentManager().getBackStackEntryCount();
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else if (entryCount > 1) {
            getFragmentManager().popBackStack();
        } else {
            askLeaveOrLogout();
        }
    }

    private void askLeaveOrLogout() {
        Toast.makeText(QualityShowApplication.getContext(), "askLeaveOrLogout", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarRequestReceived(List<String> response) {

    }

    @Override
    public void onResponseReceived(List<Object> response) {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra("isMovie", false);
        intent.putExtra("show", (Serie) response.get(0));
        startActivity(intent);
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
}
