package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanShow {

    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("year")
    private String year;
    @SerializedName("status")
    private String status;
    @SerializedName("images")
    private BeanImages images;
    @SerializedName("ids")
    private BeanIds ids;

    public BeanShow(String title, String overview, String year, String status, BeanImages images, BeanIds ids) {
        this.title = title;
        this.overview = overview;
        this.year = year;
        this.status = status;
        this.images = images;
        this.ids = ids;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BeanImages getImages() {
        return images;
    }

    public void setImages(BeanImages images) {
        this.images = images;
    }

    public BeanIds getIds() {
        return ids;
    }

    public void setIds(BeanIds ids) {
        this.ids = ids;
    }
}
