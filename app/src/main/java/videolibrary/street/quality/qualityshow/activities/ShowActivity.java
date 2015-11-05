package videolibrary.street.quality.qualityshow.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.fragments.ProfilFragment;
import videolibrary.street.quality.qualityshow.fragments.RecommandationsFragment;
import videolibrary.street.quality.qualityshow.fragments.SettingsFragment;

/**
 * Created by Sacael on 04/11/2015.
 */
public class ShowActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {
    private Toolbar toolbar;
    private User user;
    private Serie serie;
    private Film film;
    private Boolean IsMovie;
    private ProfilFragment profilFragment;
    private RecommandationsFragment recommandationsFragment;
    private SettingsFragment settingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent=getIntent();
        IsMovie = intent.getBooleanExtra("isMovie",true);
        toolbar = (Toolbar) findViewById(R.id.show_toolbar);
        setSupportActionBar(toolbar);
        if(IsMovie)
        {
            film=(Film)intent.getParcelableExtra("show");
            fillView(film);
        }
        else{
            serie = (Serie)intent.getParcelableExtra("show");
            fillView(serie);
        }

        user = QualityShowApplication.getUserHelper().getCurrentUser();
        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
        }

        setDrawer(savedInstanceState);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.add_watch:
                Toast.makeText(ShowActivity.this, "add", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void fillView(Serie serie){
        /*ArrayList<Category> categories=serie.get;
        String catstring="genres :";*/
        /*ArrayList<Saison> seasons = serie.getSaisons();
        String seasonstring="";*/
        ImageView imagev=(ImageView)findViewById(R.id.show_image);
        Object p = serie.getPoster().get("full");
        String image = (String) p;
        if (image == null) {
            Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
            imagev.setImageDrawable(drawable);
        } else {
            Picasso.with(QualityShowApplication.getContext()).load(image).into(imagev);
        }
        getSupportActionBar().setTitle(serie.getTitle());
        ((TextView)findViewById(R.id.show_title)).setText(serie.getTitle());
        ((TextView)findViewById(R.id.synopsis)).setText(serie.getOverview());
        ((TextView)findViewById(R.id.s_status)).setText(serie.getStatus());
                /*for(Category c : categories) {
            catstring.concat(" " + c.getName());

        }
        ((TextView) findViewById(R.id.s_genres)).setText(catstring);*/
        /*for(Saison s : seasons) {
            seasonstring.concat("" + s.getNumber() + "\n");
        }
        ((TextView)findViewById(R.id.s_seasons)).setText(seasonstring);*/
    }







    private void fillView(Film film){
        ArrayList<Category> categories=film.getGenres();
        String catstring="genres :";
        ImageView imagev=(ImageView)findViewById(R.id.show_image);
        Object p = film.getPoster().get("full");
        String image = (String) p;
        if (image == null) {
            Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
            imagev.setImageDrawable(drawable);
        } else {
            Picasso.with(QualityShowApplication.getContext()).load(image).into(imagev);
        }
        getSupportActionBar().setTitle(film.getTitle());
        ((TextView) findViewById(R.id.show_title)).setText(film.getTitle());
        ((TextView)findViewById(R.id.s_status)).setText(String.valueOf(film.getYear()));
        ((TextView)findViewById(R.id.synopsis)).setText(film.getOverview());
        /*for(Category c : categories) {
            catstring.concat(" " + c.getName());

        }
        ((TextView) findViewById(R.id.s_genres)).setText(catstring);*/
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
    public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
        switch (position) {
            case 1:
                profilFragment = new ProfilFragment();
                FragmentTransaction profilTransaction = getFragmentManager().beginTransaction();
                profilTransaction.add(R.id.frame_container, profilFragment);
                profilTransaction.commit();
                return true;
            case 2:
                //planning
                break;
            case 3:
                recommandationsFragment = new RecommandationsFragment();
                FragmentTransaction recommandationsTransaction = getFragmentManager().beginTransaction();
                recommandationsTransaction.add(R.id.frame_container, recommandationsFragment);
                recommandationsTransaction.commit();
                return true;
            case 4:
                settingsFragment = new SettingsFragment();
                FragmentTransaction settingsTransaction = getFragmentManager().beginTransaction();
                settingsTransaction.add(R.id.frame_container, settingsFragment);
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
}
