package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.google.gson.JsonObject;
import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.UserRepository;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.FilmRepository;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/26/15.
 * Class contaning all callback for by userRepository
 * Each need an listener {@link UserListener}
 */
public class UserCallbacks {


    /**
     * Callback for delete function on UserRepository
     */
    public static class DeleteCallback implements VoidCallback {

        private UserListener listener;

        public DeleteCallback(UserListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess() {
            Log.d(Constants.Log.TAG, "User deleted");
            this.listener.isDeleted(true);
        }

        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + DeleteCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    /**
     * Callback for login function on UserRepository
     */
    public static class LoginCallback implements UserRepository.LoginCallback<User> {

        UserListener listener;

        public LoginCallback(UserListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess(AccessToken token, User currentUser) {
            this.listener.isLogged(token, currentUser);
            Log.d(Constants.Log.TAG, currentUser.toString());
        }

        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + LoginCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    /**
     * Callback for update function on UserRepository
     */
    public static class UpdateCallback implements VoidCallback {

        private UserListener listener;

        public UpdateCallback(UserListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess() {
            Log.d(Constants.Log.TAG, "User is update");
            this.listener.isUpdated(true);
        }

        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + UpdateCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    /**
     * Callback for create function on UserRepository
     */
    public static class CreateCallback implements VoidCallback {

        private UserListener listener;

        public CreateCallback(UserListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess() {
            Log.d(Constants.Log.TAG, "User created");
            this.listener.isCreated(true);
        }

        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + CreateCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    /**
     * Callback when we want received user and films
     */
    public static class FilmsCallback extends Adapter.JsonArrayCallback{

        private UserListener listener;

        public FilmsCallback(UserListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON object or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON object.
         */
//        @Override
//        public void onSuccess(JSONObject response) {
//            Log.d(Constants.Log.TAG, "User and films received");
//            videolibrary.street.quality.qualityshow.api.user.repositories.UserRepository repository = new videolibrary.street.quality.qualityshow.api.user.repositories.UserRepository();
//            JSONObject userJson = response.optJSONObject("user");
//            User user = userJson != null
//                    ? repository.createObject(JsonUtil.fromJson(userJson))
//                    : null;
//            this.listener.gettingFilms(user);
//        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + CreateCallback.class.getSimpleName(), t);
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
            this.listener.gettingFilms(films);
        }
    }

}
