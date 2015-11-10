package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Airs extends HashMap<String, String> implements Parcelable {

    @Override
    public String get(Object key) {
        String tmpKey = (String) key;
        switch (tmpKey) {
            case "time":
                return this.values().toArray()[0].toString();
            case "day":
                return this.values().toArray()[1].toString();
            case "timezone":
                return this.values().toArray()[2].toString();
            default:
                return null;
        }
    }

    public Airs(){ }

    protected Airs(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Airs> CREATOR = new Parcelable.Creator<Airs>() {
        @Override
        public Airs createFromParcel(Parcel in) {
            return new Airs(in);
        }

        @Override
        public Airs[] newArray(int size) {
            return new Airs[size];
        }
    };
}