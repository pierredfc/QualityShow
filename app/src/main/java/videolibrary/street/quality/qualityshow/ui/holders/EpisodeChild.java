package videolibrary.street.quality.qualityshow.ui.holders;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Sacael on 07/11/2015.
 */
public class EpisodeChild {

    private Episode episode;
    private String overview;
    private boolean seen;

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getOverview() {

        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public EpisodeChild(String overview,boolean seen,Episode e){
        this.episode = e;
        this.overview=overview;
        this.seen=seen;
    }
    public Episode getEpisode() {
        return episode;
    }

}
