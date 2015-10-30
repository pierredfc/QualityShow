package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.content.Context;
import android.os.SystemClock;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import videolibrary.street.quality.qualityshow.api.user.callbacks.FilmsCallback;
import videolibrary.street.quality.qualityshow.api.user.callbacks.UserCallbacks;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.UserRepository;

/**
 * Created by elerion on 10/26/15.
 * Class containing all function util for use User on api Server
 */
public class UserHelper {

    private final ApiAdapter apiAdapter;
    private final UserRepository userRepository;

    private final String EMAIL      = "email";
    private final String USENAME    = "username";
    private final String PASSWORD   = "password";
    private final String REALM      = "realm";

    /**
     * Contructor
     * @param context context of current application
     */
    public UserHelper(Context context) {
        apiAdapter = new ApiAdapter(context, ApiConstants.API_URL);
        userRepository = apiAdapter.createRepository(UserRepository.class);
    }

    /**
     * Login function
     * @param email     email of user who want to login
     * @param password  password of user who want to login
     * @param listener  listener for received all informations about login
     */
    public void login(String email, String password, UserListener listener){
        userRepository.loginUser(email, password, new UserCallbacks.LoginCallback(listener));
    }

    /**
     * Update an existing user, the user must contain only [realm, username, email, password, created(optional), lastupdate(optional)]
     * @param user      user need to update
     * @param listener  listener for received all informations
     */
    public void update(User user, UserListener listener){
        user.save(new UserCallbacks.UpdateCallback(listener));
    }

    /**
     * Delete an existing user
     * @param user      user that we want delete
     * @param listener  listener for received all informations
     */
    public void delete(User user, UserListener listener){
        user.destroy(new UserCallbacks.DeleteCallback(listener));
    }

    /**
     * Create a new user, you need to create user just with [realm, username, email, password, created(optional), lastupdate(optional)]
     * @param listener  listener for received all informations
     */
    public void create(String username, String email, String password, String realm, UserListener listener){
        Map<String, String> map = new LinkedHashMap<>();
        map.put(this.EMAIL, email);
        map.put(this.USENAME, username);
        map.put(this.REALM, realm);
        map.put(this.PASSWORD, password);

        userRepository.createObject(map).save(new UserCallbacks.CreateCallback(listener));
    }

    /**
     * Get user and films in the same time, you can choose if you want the categories
     * @param user          Current user
     * @param listener      Listener about user actions
     * @param categories    Boolean about include categories
     */
    public void films(User user,boolean categories, UserListener listener){
        userRepository.getFilms((int) user.getId(), categories, new FilmsCallback.GetFilmsCallback(listener));
    }

    public void addFilm(int userId, Film film, FilmListener listener){
        userRepository.addFilm(userId, film, new FilmsCallback.AddFilmCallback(listener));
    }

    public void deleteFilm(int userId, int filmId, FilmListener listener){
        userRepository.deleteFilm(userId, filmId, new FilmsCallback.DeleteFilmCallback(listener));
    }

}
