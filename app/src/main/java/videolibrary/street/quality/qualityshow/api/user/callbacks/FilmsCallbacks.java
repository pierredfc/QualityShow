package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/30/15.
 */
public class FilmsCallbacks {


    /**
     * Callback when we want received user and films
    */
    public static class GetFilmsCallback extends Adapter.JsonArrayCallback{

        private FilmListener listener;

        public GetFilmsCallback(FilmListener listener) {
            this.listener = listener;
        }
        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + GetFilmsCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON array or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON array.
         */
        @Override
        public void onSuccess(JSONArray response) {
            ArrayList<Film> films = new ArrayList<>();
            FilmRepository repository = new FilmRepository();
            for (int i = 0; i < response.length(); i++){
                JSONObject object = null;
                try {
                    object  = response.getJSONObject(i);

                    Film film = object != null
                            ? repository.createObject(JsonUtil.fromJson(object))
                            : null;
                    films.add(film);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.d(Constants.Log.TAG, "Films received");
            this.listener.getFilms(films);
        }
    }

    public static class AddFilmCallback extends Adapter.JsonObjectCallback{

        private FilmListener listener;

        public AddFilmCallback(FilmListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON object or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON object.
         */
        @Override
        public void onSuccess(JSONObject response) {
            Log.d(Constants.Log.TAG, "Film added");
            FilmRepository repository = new FilmRepository();
            Film film = response != null
                    ? repository.createObject(JsonUtil.fromJson(response))
                    : null;
            this.listener.filmIsAdded(film);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + AddFilmCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class DeleteFilmCallback implements Adapter.Callback{

        private FilmListener listener;

        public DeleteFilmCallback(FilmListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON object or <code>null</code> if the response
         * string is "null".
         *
         */
        @Override
        public void onSuccess(String response) {
            Log.d(Constants.Log.TAG, "film deleted");
            this.listener.filmIsDeleted();
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + DeleteFilmCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }
}
