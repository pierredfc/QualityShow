package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;

/**
 * Created by elerion on 10/30/15.
 */
public interface FilmListener {

    public void filmIsAdded(Film film);
    public void filmIsDeleted();
    public void getFilms(ArrayList<Film> films);

    public void onError(Throwable t);
}
