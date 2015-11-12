package videolibrary.street.quality.qualityshow.ui.holders;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarChild {

    private String serieTitle;
    private Integer seasonNumber;
    private Episode episode;
    private Integer daysLeft;
    private String thumb;

    public CalendarChild(String serieTitle, Integer seasonNumber, Episode episode, Integer daysLeft, String thumb) {
        this.serieTitle = serieTitle;
        this.seasonNumber = seasonNumber;
        this.episode = episode;
        this.daysLeft = daysLeft;
        this.thumb = thumb;
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

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
