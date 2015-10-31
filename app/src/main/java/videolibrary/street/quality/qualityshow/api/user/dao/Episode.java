package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Episode extends Model{
    private String title, overview;
    private Integer saisonid, number;
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
}
