package videolibrary.street.quality.qualityshow.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.fragments.ExploreFragment;
import videolibrary.street.quality.qualityshow.ui.utils.DrawerMenuUtils;
import videolibrary.street.quality.qualityshow.utils.SearchPreferences;

/**
 * Created by Pierre on 07/11/2015.
 */
public class ExploreActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private ExploreFragment exploreFragment;
    public DrawerMenuUtils drawer;

    private SearchPreferences searchPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        searchPreferences = new SearchPreferences(this);

        toolbar = (Toolbar) findViewById(R.id.explore_toolbar);
        setSupportActionBar(toolbar);

        drawer = new DrawerMenuUtils(savedInstanceState, this, toolbar);
        drawer.getDrawer().setSelection(2);

        searchView = (MaterialSearchView) findViewById(R.id.explore_search_view);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setSuggestions(searchPreferences.getSearchPreferences());
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = parent.getItemAtPosition(position).toString();
                searchView.closeSearch();
                startSearchActivity(query);
            }
        });

        exploreFragment = new ExploreFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.explore_frame_container, exploreFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void startSearchActivity(String query){
        searchPreferences.setSearchPreferences(query);
        searchView.setSuggestions(searchPreferences.getSearchPreferences());

        Intent intent = new Intent(QualityShowApplication.getContext(), SearchActivity.class);
        Bundle extras = new Bundle();
        extras.putString(getString(R.string.query), query);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.explore_menu, menu);
        MenuItem item = menu.findItem(R.id.explore_action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.explore_menu_about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
