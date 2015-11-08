package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.strongloop.android.loopback.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elerion on 10/26/15.
 */
public class Film extends Model implements Parcelable {

    private String title;
    private String released;
    private String overview;
    private String language;
    private String trailer;
    private Integer year;

    private HashMap<String, String> poster;
    private HashMap<String, String> fanart;
    private HashMap<String, Integer> ids;

    private ArrayList<Category> genres;

    public ArrayList<Category> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Category> genres) {
        this.genres = genres;
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

    public HashMap<String, String> getPoster() {
        return poster;
    }

    public void setPoster(HashMap<String, String> poster) {
        this.poster = poster;
    }

    public HashMap<String, String> getFanart() {
        return fanart;
    }

    public void setFanart(HashMap<String, String> fanart) {
        this.fanart = fanart;
    }

    public HashMap<String, Integer> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return String.format("film { \n title : %s \n id : %s \n}", this.getTitle(), this.getId().toString());
    }

    public Film(){ }

    protected Film(Parcel in) {
        title = in.readString();
        released = in.readString();
        overview = in.readString();
        language = in.readString();
        trailer = in.readString();
        year = in.readByte() == 0x00 ? null : in.readInt();
        poster = (HashMap) in.readValue(HashMap.class.getClassLoader());
        fanart = (HashMap) in.readValue(HashMap.class.getClassLoader());
        ids = (HashMap) in.readValue(HashMap.class.getClassLoader());
        if (in.readByte() == 0x01) {
            genres = new ArrayList<Category>();
            in.readList(genres, Category.class.getClassLoader());
        } else {
            genres = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(released);
        dest.writeString(overview);
        dest.writeString(language);
        dest.writeString(trailer);
        if (year == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(year);
        }
        dest.writeValue(poster);
        dest.writeValue(fanart);
        dest.writeValue(ids);
        if (genres == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genres);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}