package videolibrary.street.quality.qualityshow.api.user.listeners;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by elerion on 10/31/15.
 */
public interface EpisodeListener {

    void episodeIsAdded(Episode episode);
    void getEpisodes(ArrayList<Episode> episodes);
    void episodeIsDelete();
    void episodeIsUpdated();

    void onError(Throwable t);

}
