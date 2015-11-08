package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;

import videolibrary.street.quality.qualityshow.api.user.callbacks.EpisodeCallback;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.listeners.EpisodeListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.SaisonRepository;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiAdapter;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/31/15.
 */
public class SaisonHelper {

    private ApiAdapter adapter;
    private SaisonRepository saisonRepository;

    public SaisonHelper(Context context) {
        this.adapter = new ApiAdapter(context, ApiConstants.API_URL);
        this.saisonRepository = new SaisonRepository();
    }

    public void getEpisodes(Saison saison, EpisodeListener listener){
        saisonRepository.getEpisodes((int) saison.getId(), new EpisodeCallback.GetEpisodesCallback(listener));
    }

    public void addEpisode(Saison saison, Episode episode, EpisodeListener listener){
        saisonRepository.addEpisode((int) saison.getId(), episode, new EpisodeCallback.AddEpisodeCallback(listener));
    }

    public void deleteEpisode(Saison saison, Episode episode, EpisodeListener listener){
        saisonRepository.deleteEpisode((int) saison.getId(), (int) episode.getId(), new EpisodeCallback.DeleteEpisodeCallback(listener));
    }

    public void updateEpisode(Saison saison, Episode episode, EpisodeListener listener){
        saisonRepository.updateEpisode((int) saison.getId(), episode, new EpisodeCallback.UpdateEpisodeCallback(listener));
    }
}
