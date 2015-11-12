package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Saison;

/**
 * Created by elerion on 10/31/15.
 */
public interface SaisonListener {

    void saisonIsAdded(Saison saison);
    void getSaisons(ArrayList<Saison> saisons);
    void saisonIsDeleted();

    void onError(Throwable t);
}
