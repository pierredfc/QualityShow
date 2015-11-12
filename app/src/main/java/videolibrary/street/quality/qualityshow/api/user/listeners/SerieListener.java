package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;

/**
 * Created by elerion on 10/30/15.
 */
public interface SerieListener {

    public void serieIsAdded(Serie serie);
    public void serieIsDeleted();
    public  void getSeries(ArrayList<Serie> series);

    public void onError(Throwable t);
}
