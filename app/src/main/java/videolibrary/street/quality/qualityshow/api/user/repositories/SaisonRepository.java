package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/31/15.
 */
public class SaisonRepository extends ModelRepository<Saison> {

    public SaisonRepository() {
        super(ApiConstants.SAISON_MODEL_NAME, ApiConstants.SAISON_API_NAME, Saison.class);
    }
}
