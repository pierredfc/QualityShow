package videolibrary.street.quality.qualityshow.listeners;


import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;

public interface AccountListener {
    void onShowRetrieved(List<Serie> series);
}
