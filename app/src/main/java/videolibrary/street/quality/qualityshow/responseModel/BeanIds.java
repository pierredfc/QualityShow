package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanIds {

    @SerializedName("trakt")
    private String trakt;
    @SerializedName("slug")
    private String slug;
    @SerializedName("tvdb")
    private String tvdb;
    @SerializedName("imdb")
    private String imdb;
    @SerializedName("tmdb")
    private String tmdb;
    @SerializedName("tvrage")
    private String tvrage;

    public BeanIds(String trakt, String slug, String tvdb, String imdb, String tmdb, String tvrage) {
        this.trakt = trakt;
        this.slug = slug;
        this.tvdb = tvdb;
        this.imdb = imdb;
        this.tmdb = tmdb;
        this.tvrage = tvrage;
    }

    public String getTrakt() {
        return trakt;
    }

    public void setTrakt(String trakt) {
        this.trakt = trakt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTvdb() {
        return tvdb;
    }

    public void setTvdb(String tvdb) {
        this.tvdb = tvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getTmdb() {
        return tmdb;
    }

    public void setTmdb(String tmdb) {
        this.tmdb = tmdb;
    }

    public String getTvrage() {
        return tvrage;
    }

    public void setTvrage(String tvrage) {
        this.tvrage = tvrage;
    }
}
