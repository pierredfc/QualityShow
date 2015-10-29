package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

/**
 * Created by elerion on 10/26/15.
 */
public class Film extends Model {

    private String title, released, year, overview, language;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return String.format("film { \n title : %s \n id : %s \n}", this.getTitle(), this.getId().toString());
    }
}
