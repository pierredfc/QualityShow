package videolibrary.street.quality.qualityshow.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.strongloop.android.remoting.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.FilmHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.SaisonHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.SerieHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategoryListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.EpisodeListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SaisonListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.CategoryRepository;
import videolibrary.street.quality.qualityshow.fragments.EpisodeFragment;
import videolibrary.street.quality.qualityshow.fragments.ShowFragment;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by Sacael on 04/11/2015.
 */
public class ShowActivity extends AppCompatActivity implements FilmListener, SerieListener, ClickListener, View.OnClickListener, ListView.OnItemClickListener, SaisonListener, EpisodeListener, CategoryListener {
    private Toolbar toolbar;
    private User user;
    private Object show;
    private Boolean IsMovie;

    private Boolean isFollow;

    private ShowFragment showFragment;
    private FloatingActionButton actionButtonActivity;
    public Fragment fragment;

    private Menu menu;

    private Serie userSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        isFollow = false;

        Intent intent = getIntent();
        IsMovie = intent.getBooleanExtra("isMovie", true);
        user = QualityShowApplication.getUserHelper().getCurrentUser();
        boolean isSearch = intent.getBooleanExtra("isSearch", false);

        if((user != null) && !isSearch){
            int showId = intent.getIntExtra("show", -1);
            if(IsMovie){
                show = user.getFilmById(showId);
            } else {
                show = user.getSerieById(showId);
            }
        }else {
            show = intent.getParcelableExtra("show");
        }


        toolbar = (Toolbar) findViewById(R.id.show_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionButtonActivity = (FloatingActionButton) findViewById(R.id.add_watch_activity);


        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) actionButtonActivity.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            actionButtonActivity.setLayoutParams(p);
            actionButtonActivity.setVisibility(View.GONE);
        } else {
            setActionButtonIcon();
            actionButtonActivity.setOnClickListener(this);
        }

        ImageView imagev = (ImageView) findViewById(R.id.show_image);

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

        showFragment = new ShowFragment();
        showFragment.setShow(show);
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
        this.menu = menu;
        if(user.getUsername().equals("Anonyme")){
            menu.findItem(R.id.add_watch).setVisible(false);
            menu.findItem(R.id.share).setVisible(false);
        } else {
            if(isFollow){
                menu.findItem(R.id.add_watch).setIcon(getDrawable(R.drawable.ic_favorite));
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.add_watch:
                handleShows();
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
        FilmHelper helper = new FilmHelper(getApplicationContext());
        if(((Film) show).getGenres() != null){
            JSONArray array = new JSONArray(((Film) show).getGenres());
            CategoryRepository repository = new CategoryRepository();
            for (int i = 0; i < array.length(); i++) {
                try {
                    String cate = array.getString(i);
                    Category category = new Category();
                    category.setName(cate);
                    helper.addCategorie(film, category, this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void filmIsDeleted() {

    }

    @Override
    public void getFilms(ArrayList<Film> films) {

    }

    @Override
    public void serieIsAdded(Serie serie) {
        SerieHelper helper = new SerieHelper(getApplicationContext());
        for (Saison s : ((Serie) show).getSaisons()){
            helper.addSaison(serie, s, this);
        }
        if(((Serie) show).getGenres() != null){
            JSONArray array = new JSONArray(((Serie) show).getGenres());
            CategoryRepository repository = new CategoryRepository();
            for (int i = 0; i < array.length(); i++) {
                try {
                    String cate = array.getString(i);
                    Category category = new Category();
                    category.setName(cate);
                    helper.addCategorie(serie, category, this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        this.userSerie = serie;
    }

    @Override
    public void serieIsDeleted() {
        user.deleteSerie((Serie) show);
        Toast.makeText(getApplicationContext(), "Serie deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSeries(ArrayList<Serie> series) {

    }

    @Override
    public void saisonIsAdded(Saison saison) {
        for (Saison s : ((Serie) show).getSaisons()) {
            if(String.valueOf(s.getIds().get("trakt")).equals(String.valueOf(saison.getIds().get("trakt")))){
                SaisonHelper saisonHelper = new SaisonHelper(getApplicationContext());
                for (Episode e : s.getEpisodes()){
                    saisonHelper.addEpisode(saison, e, this);
                }
            }
        }
        this.userSerie.addSaison(saison);
    }

    @Override
    public void getSaisons(ArrayList<Saison> saisons) {

    }

    @Override
    public void saisonIsDeleted() {

    }

    @Override
    public void episodeIsAdded(Episode episode) {
        for (Saison s : this.userSerie.getSaisons()) {
            if(((int)s.getId()) == ((int) episode.getSaisonid())){
                s.addEpisode(episode);
            }
        }
    }

    @Override
    public void getEpisodes(ArrayList<Episode> episodes) {

    }

    @Override
    public void episodeIsDelete() {

    }

    @Override
    public void episodeIsUpdated() {

    }

    @Override
    public void categorieIsAdded(Category category) {
        Toast.makeText(getApplicationContext(), "categorie added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCategories(ArrayList<Category> categories) {

    }

    @Override
    public void categorieIsDeleted() {

    }

    @Override
    public void onError(Throwable t) {
        Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
    }

    @Override
    public void onItemClick(Object item) {

    }

    @Override
    public void onClick(View v) {
        handleShows();
    }

    private void handleShows(){
        if(!IsMovie){
            if(!isFollow){
                if(this.addSerieToUser()){
                    isFollow = true;
                    changeIcons();
                }
            } else {
                if(this.deleteSerieToUser()){
                    isFollow = false;
                    changeIcons();
                }
            }
        } else {
            if(!isFollow){
                if(this.addFilmToUser()){
                    isFollow = true;
                    changeIcons();
                }
            } else {
                if(this.deleteFilmToUser()){
                    isFollow = false;
                    changeIcons();
                }
            }
        }
    }

    private void changeIcons(){
        if(isFollow){
            actionButtonActivity.setImageDrawable(getDrawable(R.drawable.ic_favorite));
            menu.findItem(R.id.add_watch).setIcon(getDrawable(R.drawable.ic_favorite));
        } else {
            actionButtonActivity.setImageDrawable(getDrawable(R.drawable.ic_action_add));
            menu.findItem(R.id.add_watch).setIcon(getDrawable(R.drawable.ic_action_add));
        }
    }

    private boolean addSerieToUser() {
        UserHelper userHelper = QualityShowApplication.getUserHelper();
        User user = userHelper.getCurrentUser();
        if (!userHelper.serieIsExist((Serie)this.show)) {
            userHelper.addSerie(user, (Serie) this.show, this);
            return true;
        } else {
            return false;
        }
    }
    private boolean deleteSerieToUser(){
        UserHelper userHelper = QualityShowApplication.getUserHelper();
        User user = userHelper.getCurrentUser();
        if (userHelper.serieIsExist((Serie)this.show)) {
            userHelper.deleteSerie(user, (int) ((Serie) this.show).getId(), this);
            return true;
        } else {
            return false;
        }
    }

    private boolean addFilmToUser(){
        UserHelper userHelper = QualityShowApplication.getUserHelper();
        User user = userHelper.getCurrentUser();
        if (!userHelper.filmIsExist((Film)this.show)) {
            userHelper.addFilm(user, (Film) this.show, this);
            return true;
        } else {
            return false;
        }
    }

    private boolean deleteFilmToUser(){
        UserHelper userHelper = QualityShowApplication.getUserHelper();
        User user = userHelper.getCurrentUser();
        if (userHelper.filmIsExist((Film) this.show)) {
            userHelper.deleteFilm(user, (int)((Film) this.show).getId(), this);
            return true;
        } else {
            return false;
        }
    }

    private void setActionButtonIcon(){
        Boolean exist = false;

        if(IsMovie){
            ArrayList<Film> films = QualityShowApplication.getUserHelper().getCurrentUser().getFilms();
            if(films != null){
                for(Film s: films){
                    if(s.getTitle().equals(((Film) show).getTitle())){
                        exist = true;
                        isFollow = true;
                    }
                }
            }
        } else {
            ArrayList<Serie> series = QualityShowApplication.getUserHelper().getCurrentUser().getSeries();
            if (series != null){
                for(Serie s: series){
                    if(s.getTitle().equals(((Serie) show).getTitle())){
                        exist = true;
                        isFollow = true;
                    }
                }
            }
        }

        if(exist){
            actionButtonActivity.setImageDrawable(getDrawable(R.drawable.ic_favorite));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        if (item instanceof Saison) {
            if(((Saison) item).getEpisodes() != null){
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
    }

    @Override
    public void onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then emulate 'onBackPressed' behaviour, because in default, it is not working
        if(QualityShowApplication.getUserHelper().getCurrentUser() != null){
            QualityShowApplication.getUserHelper().getCurrentUser().setSeries(user.series);
        }
        if (!getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

}

