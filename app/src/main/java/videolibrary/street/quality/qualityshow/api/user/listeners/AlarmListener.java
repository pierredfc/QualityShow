package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;

/**
 * Created by Pierre on 12/11/2015.
 */
public interface AlarmListener {
    void setSeries(ArrayList<Serie> series);
}
