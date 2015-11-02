package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Serie extends Model {

    private String title, country, first_aired, language, overview, status, trailer;
    private Integer aired_episode, year;
    private HashMap<String, Ids> ids;
    private HashMap<String, Fanart> fanart;
    private HashMap<String, Poster> poster;
    private HashMap<String, Airs> airs;

    private ArrayList<Saison> saisons;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirst_aired() {
        return first_aired;
    }

    public void setFirst_aired(String first_aired) {
        this.first_aired = first_aired;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Integer getAired_episode() {
        return aired_episode;
    }

    public void setAired_episode(Integer aired_episode) {
        this.aired_episode = aired_episode;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public HashMap<String, Ids> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Ids> ids) {
        this.ids = ids;
    }

    public HashMap<String, Fanart> getFanart() {
        return fanart;
    }

    public void setFanart(HashMap<String, Fanart> fanart) {
        this.fanart = fanart;
    }

    public HashMap<String, Poster> getPoster() {
        return poster;
    }

    public void setPoster(HashMap<String, Poster> poster) {
        this.poster = poster;
    }

    public HashMap<String, Airs> getAirs() {
        return airs;
    }

    public void setAirs(HashMap<String, Airs> airs) {
        this.airs = airs;
    }

    public ArrayList<Saison> getSaisons() {
        return saisons;
    }

    public void setSaisons(ArrayList<Saison> saisons) {
        this.saisons = saisons;
    }
}
