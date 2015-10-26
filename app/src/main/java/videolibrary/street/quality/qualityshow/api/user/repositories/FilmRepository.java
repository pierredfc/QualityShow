package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;

/**
 * Created by elerion on 10/26/15.
 */
public class FilmRepository extends ModelRepository<Film> {

    public FilmRepository() {
        super("film", "Films", Film.class);
    }
}
