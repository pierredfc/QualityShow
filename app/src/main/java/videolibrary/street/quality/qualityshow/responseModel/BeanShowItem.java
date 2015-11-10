package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanShowItem extends BeanItem {

    @SerializedName("show")
    private BeanMovie show;

    public BeanShowItem(String type, String score, BeanMovie show) {
        super(type, score);
        this.show = show;
    }

    public BeanMovie getShow() {
        return show;
    }

    public void setShow(BeanMovie show) {
        this.show = show;
    }
}
