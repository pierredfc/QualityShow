package videolibrary.street.quality.qualityshow.utils;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.SeasonRequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.ShowAdderAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;

/**
 * Created by Sacael on 10/11/2015.
 */
public class ShowAdder implements SerieListener,FilmListener,RequestListener {
    private Serie selectedSerie;
    RequestAsyncTask requestAsyncTask;
    ShowAdderAsyncTask showAdderAsyncTask;
    private int airedEpisodeSerie = 0;
    private int episodeAdded = 0;
    private int currentSaison = 0;
    private int episodeAddedToCurrentSaison = 0;

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

    }

    @Override
    public void filmIsDeleted() {

    }

    @Override
    public void getFilms(ArrayList<Film> films) {

    }

    @Override
    public void serieIsAdded(Serie serie) {

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
    public void onResponseReceived(List<Object> response) {
        requestAsyncTask=new RequestAsyncTask(this);
        if(response.size() > 0){
            if(response.get(0) instanceof  Serie) {
                this.selectedSerie = (Serie) response.get(0);
                requestAsyncTask.execute(Requests.SERIE_SEASONS, (this.selectedSerie.getIds().get("trakt")).toString());

            }
            else if(response.get(0) instanceof Film){
                QualityShowApplication.getUserHelper().addFilm(QualityShowApplication.getUserHelper().getCurrentUser(), (Film)response.get(0), this);
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
            if((this.selectedSerie.getSaisons() != null) &&(this.episodeAddedToCurrentSaison == this.selectedSerie.getSaisons().get(this.currentSaison).getEpisode_count())){
                this.currentSaison++;
                this.episodeAddedToCurrentSaison = 0;
            }
            if((this.episodeAdded == this.airedEpisodeSerie) && (this.episodeAdded != 0)){
                QualityShowApplication.getUserHelper().addSerie(QualityShowApplication.getUserHelper().getCurrentUser(),selectedSerie,this);
            }
        }

    }
    }
