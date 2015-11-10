package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanImages {

    @SerializedName("poster")
    private BeanImage poster;
    @SerializedName("fanart")
    private BeanImage fanart;

    public BeanImages(BeanImage poster, BeanImage fanart) {
        this.poster = poster;
        this.fanart = fanart;
    }

    public BeanImage getPoster() {
        return poster;
    }

    public void setPoster(BeanImage poster) {
        this.poster = poster;
    }

    public BeanImage getFanart() {
        return fanart;
    }

    public void setFanart(BeanImage fanart) {
        this.fanart = fanart;
    }
}
