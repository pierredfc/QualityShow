package videolibrary.street.quality.qualityshow.responseModel;

import com.google.gson.annotations.SerializedName;

public class BeanItem {

    @SerializedName("type")
    protected String type;
    @SerializedName("score")
    protected String score;

    public BeanItem(String type, String score) {
        this.type = type;
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
