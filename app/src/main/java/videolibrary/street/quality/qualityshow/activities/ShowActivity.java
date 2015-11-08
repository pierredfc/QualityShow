package videolibrary.street.quality.qualityshow.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.fragments.EpisodeFragment;
import videolibrary.street.quality.qualityshow.fragments.ExploreFragment;
import videolibrary.street.quality.qualityshow.fragments.ProfilFragment;
import videolibrary.street.quality.qualityshow.fragments.SettingsFragment;
import videolibrary.street.quality.qualityshow.fragments.ShowFragment;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;

/**
 * Created by Sacael on 04/11/2015.
 */
public class ShowActivity extends AppCompatActivity implements FilmListener, SerieListener, ClickListener, View.OnClickListener, ListView.OnItemClickListener {
    private Toolbar toolbar;
    private User user;
    private Object show;
    private Boolean IsMovie;
    private ProfilFragment profilFragment;
    private ExploreFragment exploreFragment;
    private SettingsFragment settingsFragment;
    private ShowFragment showFragment;
    private FloatingActionButton actionButtonActivity;
    public Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        IsMovie = intent.getBooleanExtra("isMovie", true);
        show=intent.getParcelableExtra("show");
        toolbar = (Toolbar) findViewById(R.id.show_toolbar);
        actionButtonActivity = (FloatingActionButton) findViewById(R.id.add_watch_activity);
        actionButtonActivity.setOnClickListener(this);
        setSupportActionBar(toolbar);
        ImageView imagev = (ImageView) findViewById(R.id.show_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Object p;
        if (IsMovie) {
            Film film = (Film) show;
            getSupportActionBar().setTitle(film.getTitle());
            p = film.getFanart().get("thumb");
        } else {
            Serie serie = (Serie) show;
            p = serie.getFanart().get("thumb");
            getSupportActionBar().setTitle(serie.getTitle());
        }


        String image = (String) p;
        if (image == null) {
            Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
            imagev.setImageDrawable(drawable);
        } else {
            Picasso.with(QualityShowApplication.getContext()).load(image).into(imagev);
        }
        user = QualityShowApplication.getUserHelper().getCurrentUser();
        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
        }

        showFragment = new ShowFragment();
        showFragment.setShow(intent.getParcelableExtra("show"));
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.show_frame_container, showFragment);
        transaction.commit();
        fragment = showFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
                this.addSerieToUser();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void filmIsAdded(Film film) {

    }

    @Override
    public void filmIsDeleted() {

    }

    @Override
    public void getFilms(ArrayList<Film> films) {

    }

    @Override
    public void serieIsAdded(Serie serie) {
        User user = QualityShowApplication.getUserHelper().getCurrentUser();
        user.addSerie(serie);
        Toast.makeText(getApplicationContext(), "Serie added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onItemClick(Object item) {

    }

    @Override
    public void onClick(View v) {
        this.addSerieToUser();
    }

    private void addSerieToUser() {
        UserHelper userHelper = QualityShowApplication.getUserHelper();
        User user = userHelper.getCurrentUser();
        if (!userHelper.serieIsExist((Serie)this.show)) {
            userHelper.addSerie(user,(Serie)this.show, this);
        } else {
            Toast.makeText(getApplicationContext(), "Serie already exist", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        if (item instanceof Saison) {
            EpisodeFragment episodeFragment = new EpisodeFragment();
            episodeFragment.setSeason((Saison) item);
            episodeFragment.setSerieId(((Serie)this.show).getIds().get("trakt"));
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.show_frame_container, episodeFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            fragment = episodeFragment;
        }
    }

    @Override
    public void onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then emulate 'onBackPressed' behaviour, because in default, it is not working
        if (!getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

}

