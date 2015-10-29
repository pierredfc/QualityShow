package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.helpers.ApiConstants;

/**
 * Created by elerion on 10/26/15.
 */
public class FilmRepository extends ModelRepository<Film> {

    public FilmRepository() {
        super(ApiConstants.FILM_MODEL_NAME, ApiConstants.FILM_API_NAME, Film.class);
    }
}
