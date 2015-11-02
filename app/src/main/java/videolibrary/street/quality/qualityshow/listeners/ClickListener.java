package videolibrary.street.quality.qualityshow.listeners;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;


public interface ClickListener {
    void onEpisodeClicked(Episode episode);
}
