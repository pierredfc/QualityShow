package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elerion on 10/26/15.
 */
public class Film extends Model {

    private String title;
    private String released;
    private String overview;
    private String language;
    private String trailer;
    private Integer year;

    private HashMap<String, Poster> poster;
    private HashMap<String, Fanart> fanart;
    private HashMap<String, Ids> ids;

    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public HashMap<String, Poster> getPoster() {
        return poster;
    }

    public void setPoster(HashMap<String, Poster> poster) {
        this.poster = poster;
    }

    public HashMap<String, Fanart> getFanart() {
        return fanart;
    }

    public void setFanart(HashMap<String, Fanart> fanart) {
        this.fanart = fanart;
    }

    public HashMap<String, Ids> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Ids> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return String.format("film { \n title : %s \n id : %s \n}", this.getTitle(), this.getId().toString());
    }
}
