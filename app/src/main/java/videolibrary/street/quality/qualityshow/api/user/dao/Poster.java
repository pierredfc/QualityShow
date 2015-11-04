package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Poster extends HashMap<String, String> implements Parcelable {

    @Override
    public String get(Object key) {
        String tmpKey = (String) key;
        switch (tmpKey) {
            case "thumb":
                return this.values().toArray()[0].toString();
            case "medium":
                return this.values().toArray()[1].toString();
            case "full":
                return this.values().toArray()[2].toString();
            default:
                return null;
        }
    }

    public Poster(){ }

    protected Poster(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Poster> CREATOR = new Parcelable.Creator<Poster>() {
        @Override
        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        @Override
        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };
}