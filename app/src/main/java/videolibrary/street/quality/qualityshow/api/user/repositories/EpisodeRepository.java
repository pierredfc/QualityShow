package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/31/15.
 */
public class EpisodeRepository extends ModelRepository<Episode> {

    public EpisodeRepository() {
        super(ApiConstants.EPISODE_MODEL_NAME, ApiConstants.EPISODEE_API_NAME, Episode.class);
    }
}
