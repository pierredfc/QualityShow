package videolibrary.street.quality.qualityshow.api.user.listeners;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;

/**
 * Created by elerion on 10/30/15.
 */
public interface FilmListener {

    public void isAdded(Film films);
    public void isDeleted(boolean success);

    public void OnError(Throwable t);
}
