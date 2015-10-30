package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.helpers.ApiConstants;

/**
 * Created by elerion on 10/30/15.
 */
public class SerieRepository extends ModelRepository<Serie> {
    /**
     * Creates a new Repository, associating it with the named remote class.
     *
     */
    public SerieRepository() {
        super(ApiConstants.SERIE_MODEL_NAME, ApiConstants.SERIE_API_NAME, Serie.class);
    }
}
