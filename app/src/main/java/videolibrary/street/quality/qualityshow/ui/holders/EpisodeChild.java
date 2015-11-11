package videolibrary.street.quality.qualityshow.ui.holders;

/**
 * Created by Sacael on 07/11/2015.
 */
public class EpisodeChild {
    private String overview;
    private String date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EpisodeChild(String overview, String date, boolean seen){
        this.overview=overview;
        this.date = date;
        this.seen=seen;
    }
}
