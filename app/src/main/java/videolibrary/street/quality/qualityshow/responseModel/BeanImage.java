package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanImage {

    @SerializedName("full")
    private String full;
    @SerializedName("medium")
    private String medium;
    @SerializedName("thumb")
    private String thumb;

    public BeanImage(String full, String medium, String thumb) {
        this.full = full;
        this.medium = medium;
        this.thumb = thumb;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
