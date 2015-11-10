package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanMovieItem extends BeanItem {

    @SerializedName("movie")
    private BeanMovie movie;

    public BeanMovieItem(String type, String score, BeanMovie movie) {
        super(type, score);
        this.movie = movie;
    }

    public BeanMovie getMovie() {
        return movie;
    }

    public void setMovie(BeanMovie movie) {
        this.movie = movie;
    }
}
