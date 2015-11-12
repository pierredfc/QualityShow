package videolibrary.street.quality.qualityshow.ui.holders;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarChild {

    private String serieTitle;
    private Integer seasonNumber;
    private Integer daysLeft;
    private Saison saison;
    private Episode episode;

    public CalendarChild(String serieTitle, Integer seasonNumber, Integer daysLeft, Saison saison, Episode episode) {
        this.serieTitle = serieTitle;
        this.seasonNumber = seasonNumber;
        this.daysLeft = daysLeft;
        this.saison = saison;
        this.episode = episode;
    }

    public String getSerieTitle() {
        return serieTitle;
    }

    public void setSerieTitle(String serieTitle) {
        this.serieTitle = serieTitle;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Saison getSaison() {
        return saison;
    }

    public void setSaison(Saison saison) {
        this.saison = saison;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
