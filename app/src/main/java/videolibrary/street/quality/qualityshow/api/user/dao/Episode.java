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
    private Boolean see;
    private HashMap<String, Screenshot> screenshot;
    private HashMap<String, Ids> ids;

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

    public HashMap<String, Ids> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Ids> ids) {
        this.ids = ids;
    }

    public Boolean getSee() {
        return see;
    }

    public void setSee(Boolean see) {
        this.see = see;
    }

    public Episode(){ }

    protected Episode(Parcel in) {
        title = in.readString();
        overview = in.readString();
        saisonid = in.readByte() == 0x00 ? null : in.readInt();
        number = in.readByte() == 0x00 ? null : in.readInt();
        byte seeVal = in.readByte();
        see = seeVal == 0x02 ? null : seeVal != 0x00;
        screenshot = (HashMap) in.readValue(HashMap.class.getClassLoader());
        ids = (HashMap) in.readValue(HashMap.class.getClassLoader());
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
        if (see == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (see ? 0x01 : 0x00));
        }
        dest.writeValue(screenshot);
        dest.writeValue(ids);
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