package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.strongloop.android.loopback.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Serie extends Model implements Parcelable {

    private String title;
    private String country;
    private String first_aired;
    private String language;
    private String overview;
    private String status;
    private String trailer;
    private Integer aired_episode;
    private Integer year;
    private HashMap<String, Ids> ids;
    private HashMap<String, Fanart> fanart;
    private HashMap<String, Poster> poster;
    private HashMap<String, Airs> airs;

    private ArrayList<Saison> saisons;
    private ArrayList<Category> genres;

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

    public ArrayList<Category> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Category> genres) {
        this.genres = genres;
    }

    public Serie() {
    }


    protected Serie(Parcel in) {
        title = in.readString();
        country = in.readString();
        first_aired = in.readString();
        language = in.readString();
        overview = in.readString();
        status = in.readString();
        trailer = in.readString();
        aired_episode = in.readByte() == 0x00 ? null : in.readInt();
        year = in.readByte() == 0x00 ? null : in.readInt();
        ids = (HashMap) in.readValue(HashMap.class.getClassLoader());
        fanart = (HashMap) in.readValue(HashMap.class.getClassLoader());
        poster = (HashMap) in.readValue(HashMap.class.getClassLoader());
        airs = (HashMap) in.readValue(HashMap.class.getClassLoader());
        if (in.readByte() == 0x01) {
            saisons = new ArrayList<Saison>();
            in.readList(saisons, Saison.class.getClassLoader());
        } else {
            saisons = null;
        }
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
        dest.writeString(country);
        dest.writeString(first_aired);
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeString(status);
        dest.writeString(trailer);
        if (aired_episode == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(aired_episode);
        }
        if (year == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(year);
        }
        dest.writeValue(ids);
        dest.writeValue(fanart);
        dest.writeValue(poster);
        dest.writeValue(airs);
        if (saisons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(saisons);
        }
        if (genres == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genres);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Serie> CREATOR = new Parcelable.Creator<Serie>() {
        @Override
        public Serie createFromParcel(Parcel in) {
            return new Serie(in);
        }

        @Override
        public Serie[] newArray(int size) {
            return new Serie[size];
        }
    };
}