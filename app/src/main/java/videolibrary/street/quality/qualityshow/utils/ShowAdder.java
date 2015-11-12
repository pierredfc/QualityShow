package videolibrary.street.quality.qualityshow.utils;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
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
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.SeasonRequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.ShowAdderAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;

/**
 * Created by Sacael on 10/11/2015.
 */
public class ShowAdder implements SerieListener,FilmListener,RequestListener,SaisonListener,EpisodeListener, CategoryListener {
    private Serie selectedSerie;
    RequestAsyncTask requestAsyncTask;
    ShowAdderAsyncTask showAdderAsyncTask;
    private int airedEpisodeSerie = 0;
    private int episodeAdded = 0;
    private int currentSaison = 0;
    private int episodeAddedToCurrentSaison = 0;
    private Film selectedfilm;

    public void addShow(String id,String Movie){
        requestAsyncTask=new RequestAsyncTask(this);
        if (Movie=="movie"){
            requestAsyncTask.execute(Requests.MOVIE_FIND,id);
        }
        else{
            requestAsyncTask.execute(Requests.SERIE_FIND,id);
        }


    }
    @Override
    public void filmIsAdded(Film film) {
        FilmHelper helper = new FilmHelper(QualityShowApplication.getContext());
        QualityShowApplication.getUserHelper().getCurrentUser().addFilm(film);
        if(((Film) selectedfilm).getGenres() != null){
            JSONArray array = new JSONArray(((Film) selectedfilm).getGenres());
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
        SerieHelper helper = new SerieHelper(QualityShowApplication.getContext());
        QualityShowApplication.getUserHelper().getCurrentUser().addSerie(serie);
        for (Saison s : ((Serie) selectedSerie).getSaisons()){
            helper.addSaison(serie, s, this);
        }
        if(((Serie) selectedSerie).getGenres() != null){
            JSONArray array = new JSONArray(((Serie) selectedSerie).getGenres());
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
    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {

    }

    @Override
    public void episodeIsAdded(Episode episode) {

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
    public void saisonIsAdded(Saison saison) {
        for (Saison s : ( selectedSerie).getSaisons()) {
            if(String.valueOf(s.getIds().get("trakt")).equals(String.valueOf(saison.getIds().get("trakt")))){
                SaisonHelper saisonHelper = new SaisonHelper(QualityShowApplication.getContext());
                for (Episode e : s.getEpisodes()){
                    saisonHelper.addEpisode(saison, e, this);
                }
            }
        }
    }

    @Override
    public void getSaisons(ArrayList<Saison> saisons) {

    }

    @Override
    public void saisonIsDeleted() {

    }

    @Override
    public void categorieIsAdded(Category category) {

    }

    @Override
    public void getCategories(ArrayList<Category> categories) {

    }

    @Override
    public void categorieIsDeleted() {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onResponseReceived(List<Object> response) {
        requestAsyncTask=new RequestAsyncTask(this);
        if(response.size() > 0){
            if(response.get(0) instanceof  Serie) {
                this.selectedSerie = (Serie) response.get(0);
                requestAsyncTask.execute(Requests.SERIE_SEASONS, (this.selectedSerie.getIds().get("trakt")).toString());

            }
            else if(response.get(0) instanceof Film){
                selectedfilm=(Film)response.get(0);
                QualityShowApplication.getUserHelper().addFilm(QualityShowApplication.getUserHelper().getCurrentUser(), (Film) response.get(0), this);
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
            }
            if((this.selectedSerie!=null)&&(this.selectedSerie.getSaisons() != null) &&(this.episodeAddedToCurrentSaison == this.selectedSerie.getSaisons().get(this.currentSaison).getEpisode_count())){
                this.currentSaison++;
                this.episodeAddedToCurrentSaison = 0;
            }
            if((this.selectedSerie!=null)&&(this.episodeAdded == this.airedEpisodeSerie) && (this.episodeAdded != 0)){
                QualityShowApplication.getUserHelper().addSerie(QualityShowApplication.getUserHelper().getCurrentUser(), selectedSerie, this);
            }
        }

    }
    }
