package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.strongloop.android.loopback.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Saison extends Model implements Parcelable {
    private Integer aired_episodes;
    private Integer episodes_count;
    private Integer number;
    private Integer serieId;
    private HashMap<String, Ids> ids;
    private HashMap<String, Poster> poster;

    private ArrayList<Episode> episodes;

    public Integer getAired_episodes() {
        return aired_episodes;
    }

    public void setAired_episodes(Integer aired_episodes) {
        this.aired_episodes = aired_episodes;
    }

    public Integer getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Integer episodes_count) {
        this.episodes_count = episodes_count;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public HashMap<String, Ids> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Ids> ids) {
        this.ids = ids;
    }

    public HashMap<String, Poster> getPoster() {
        return poster;
    }

    public void setPoster(HashMap<String, Poster> poster) {
        this.poster = poster;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public Saison(){ }

    protected Saison(Parcel in) {
        aired_episodes = in.readByte() == 0x00 ? null : in.readInt();
        episodes_count = in.readByte() == 0x00 ? null : in.readInt();
        number = in.readByte() == 0x00 ? null : in.readInt();
        serieId = in.readByte() == 0x00 ? null : in.readInt();
        ids = (HashMap) in.readValue(HashMap.class.getClassLoader());
        poster = (HashMap) in.readValue(HashMap.class.getClassLoader());
        if (in.readByte() == 0x01) {
            episodes = new ArrayList<Episode>();
            in.readList(episodes, Episode.class.getClassLoader());
        } else {
            episodes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (aired_episodes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(aired_episodes);
        }
        if (episodes_count == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(episodes_count);
        }
        if (number == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(number);
        }
        if (serieId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(serieId);
        }
        dest.writeValue(ids);
        dest.writeValue(poster);
        if (episodes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(episodes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Saison> CREATOR = new Parcelable.Creator<Saison>() {
        @Override
        public Saison createFromParcel(Parcel in) {
            return new Saison(in);
        }

        @Override
        public Saison[] newArray(int size) {
            return new Saison[size];
        }
    };
}