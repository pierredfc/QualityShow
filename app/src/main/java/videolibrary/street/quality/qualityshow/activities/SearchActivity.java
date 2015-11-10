package videolibrary.street.quality.qualityshow.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.SeasonRequestAsyncTask;
import videolibrary.street.quality.qualityshow.fragments.SearchFragment;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Constants;
import videolibrary.street.quality.qualityshow.utils.Requests;


public class SearchActivity extends AppCompatActivity implements ClickListener, RequestListener {

    String query;
    Toolbar toolbar;
    SearchFragment searchFragment;
    Serie selectedSerie = null;

    RequestAsyncTask requestAsyncTask;

    private int airedEpisodeSerie = 0;
    private int episodeAdded = 0;
    private int currentSaison = 0;
    private int episodeAddedToCurrentSaison = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        query = getIntent().getStringExtra(getString(R.string.query));

        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.requestAsyncTask = new RequestAsyncTask(this);


        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        searchFragment = SearchFragment.newInstance(query);
        transaction.add(R.id.search_frame_container, searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
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
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof Serie) {

            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.SERIE_FIND, String.valueOf(((Serie) item).getIds().get("slug")));

        }
        if (item instanceof Film) {
            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.MOVIE_FIND, String.valueOf(((Film) item).getIds().get("slug")));

        }
    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if(response.size() > 0){
            if(response.get(0) instanceof  Serie) {
                this.selectedSerie = (Serie) response.get(0);
                requestAsyncTask.execute(Requests.SERIE_SEASONS, (this.selectedSerie.getIds().get("trakt")).toString());

            }
            else if(response.get(0) instanceof Film){
                Intent intent = new Intent(this, ShowActivity.class);
                intent.putExtra("isSearch", true);
                intent.putExtra("isMovie",true);
                intent.putExtra("show",(Film)response.get(0));
                startActivity(intent);
            }else if((response.get(0) instanceof Saison) && this.selectedSerie != null){
                for (Object o : response){
                    Saison s = (Saison)o;
                    this.airedEpisodeSerie += s.getEpisode_count();
                    this.selectedSerie.addSaison(s);
                    SeasonRequestAsyncTask seasonRequestAsyncTask = new SeasonRequestAsyncTask(this);
                    seasonRequestAsyncTask.execute(String.valueOf(this.selectedSerie.getIds().get("trakt")), String.valueOf(s.getNumber()));
                }
            }else if(response.get(0) instanceof Episode){
                for(Object o : response){
                    this.selectedSerie.getSaisons().get(this.currentSaison).addEpisode((Episode) o);
                    this.episodeAdded++;
                    this.episodeAddedToCurrentSaison++;
                }
            }else if((this.selectedSerie.getSaisons() != null) &&(this.episodeAddedToCurrentSaison == this.selectedSerie.getSaisons().get(this.currentSaison).getEpisode_count())){
                this.currentSaison++;
                this.episodeAddedToCurrentSaison = 0;
            }
            if((this.episodeAdded == this.airedEpisodeSerie) && (this.episodeAdded != 0)){
                Intent intent = new Intent(this, ShowActivity.class);
                intent.putExtra("isSearch", true);
                intent.putExtra("isMovie", false);
                intent.putExtra("show", (Serie) this.selectedSerie);
                startActivity(intent);
            }
        }

    }
}

