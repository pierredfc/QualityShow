package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;
import android.util.Log;

import com.strongloop.android.loopback.callbacks.ListCallback;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;

/**
 * Created by elerion on 10/26/15.
 */
public class FilmHelper {

    private final ApiAdapter apiAdapter;
    private final FilmRepository filmRepository;

    private final String tag = FilmHelper.class.getName().toString();

    public FilmHelper(Context context) {
        this.apiAdapter = new ApiAdapter(context, ApiConstants.API_URL);
        this.filmRepository = this.apiAdapter.createRepository(FilmRepository.class);
    }

    public void getAllFilms(){
        this.filmRepository.findAll(new ListCallback<Film>() {
            @Override
            public void onSuccess(List<Film> objects) {
                for (Film film: objects) {
                    Log.d(tag, film.toString());
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e(tag, "getAllFilms error", t);
            }
        });
    }
}
