package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.strongloop.android.loopback.Model;

import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Episode extends Model implements Parcelable {
    private String title;
    private String overview;
    private Integer saisonid;
    private Integer number;
    private Boolean See;
    private HashMap<String, Screenshot> screenshot;
    private HashMap<String, Integer> ids;
    private String first_aired;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getSaisonid() {
        return saisonid;
    }

    public void setSaisonid(Integer saisonid) {
        this.saisonid = saisonid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public HashMap<String, Screenshot> getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(HashMap<String, Screenshot> screenshot) {
        this.screenshot = screenshot;
    }

    public HashMap<String, Integer> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Integer> ids) {
        this.ids = ids;
    }


    public Boolean getSee() {
        return See;
    }

    public void setSee(Boolean see) {
        See = see;
    }

    public String getFirst_aired() {
        return first_aired;
    }

    public void setFirst_aired(String first_aired) {
        this.first_aired = first_aired;
    }

    public Episode() {
    }


    protected Episode(Parcel in) {
        title = in.readString();
        overview = in.readString();
        saisonid = in.readByte() == 0x00 ? null : in.readInt();
        number = in.readByte() == 0x00 ? null : in.readInt();
        byte seeVal = in.readByte();
        See = seeVal == 0x02 ? null : seeVal != 0x00;
        screenshot = (HashMap) in.readValue(HashMap.class.getClassLoader());
        ids = (HashMap) in.readValue(HashMap.class.getClassLoader());
        first_aired = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        if (saisonid == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(saisonid);
        }
        if (number == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(number);
        }
        if (See == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (See ? 0x01 : 0x00));
        }
        dest.writeValue(screenshot);
        dest.writeValue(ids);
        dest.writeString(first_aired);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };
}