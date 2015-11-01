package videolibrary.street.quality.qualityshow;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.utils.Constants;

public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener{

    Toolbar toolbar;
    User user;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_menu_search:
                return true;
            case R.id.main_menu_settings:
                Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //     finish();
    }

    private void setDrawer(Bundle savedInstanceState){
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.purple)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getUsername()).withEmail(user.getEmail())
                )
                .build();

        PrimaryDrawerItem profil = new PrimaryDrawerItem().withName("Profil");
        SecondaryDrawerItem planning = new SecondaryDrawerItem().withName("Mon planning");
        SecondaryDrawerItem series = new SecondaryDrawerItem().withName("Mes séries");
        SecondaryDrawerItem films = new SecondaryDrawerItem().withName("Mes films");
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withName("Réglages");
        SecondaryDrawerItem login = new SecondaryDrawerItem().withName("Se déconnecter");

        Drawer result = new DrawerBuilder().withActivity(this).withToolbar(toolbar)
                .addDrawerItems(
                        profil,
                        planning,
                        series,
                        films,
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
    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        return false;
    }
}
