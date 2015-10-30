package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.loopback.Model;

import java.util.HashMap;

/**
 * Created by elerion on 10/30/15.
 */
public class Saison extends Model {
    private Integer aired_episodes, episodes_count, number, serieId;
    private HashMap<String, Ids> ids;
    private HashMap<String, Poster> poster;

    public Integer getAired_episodes() {
        return aired_episodes;
    }

    public void setAired_episodes(Integer aired_episodes) {
        this.aired_episodes = aired_episodes;
    }

    public Integer getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Integer episodes_count) {
        this.episodes_count = episodes_count;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public HashMap<String, Ids> getIds() {
        return ids;
    }

    public void setIds(HashMap<String, Ids> ids) {
        this.ids = ids;
    }

    public HashMap<String, Poster> getPoster() {
        return poster;
    }

    public void setPoster(HashMap<String, Poster> poster) {
        this.poster = poster;
    }
}
