package videolibrary.street.quality.qualityshow.api.user.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by elerion on 10/26/15.
 * Class look like model on server api
 */
public class User extends com.strongloop.android.loopback.User implements Parcelable {

    String created;
    String lastUpdated;
    String username;

    public ArrayList<Film> films;

    public ArrayList<Serie> series;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    public ArrayList<Serie> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<Serie> series) {
        this.series = series;
    }

    public void addSerie(Serie serie){
        this.series.add(serie);
    }

    public void deleteSerie(Serie s){
        for (int i = 0; i < this.series.size(); i++) {
            if(String.valueOf(this.series.get(i).getIds().get("slug")).equals(String.valueOf(s.getIds().get("slug")))){
                deleteSerieAt(i);
                return;
            }
        }
    }

    public void deleteSerieAt(int index){
        this.series.remove(index);
    }

    public Serie getSerieById(int id){
        for (Serie serie :
                series) {
            if ((int) serie.getId() == id)
                return serie;
        }
        return null;
    }

    public Film getFilmById(int id){
        for (Film film : films) {
            if ((int) film.getId() == id)
                return film;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("User : " + this.getCreationParameters().toString());
    }

    public User(){ }

    protected User(Parcel in) {
        created = in.readString();
        lastUpdated = in.readString();
        username = in.readString();
        if (in.readByte() == 0x01) {
            films = new ArrayList<Film>();
            in.readList(films, Film.class.getClassLoader());
        } else {
            films = null;
        }
        if (in.readByte() == 0x01) {
            series = new ArrayList<Serie>();
            in.readList(series, Serie.class.getClassLoader());
        } else {
            series = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created);
        dest.writeString(lastUpdated);
        dest.writeString(username);
        if (films == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(films);
        }
        if (series == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(series);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}