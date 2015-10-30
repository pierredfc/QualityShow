package videolibrary.street.quality.qualityshow.api.user.listeners;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.User;

/**
 * Created by elerion on 10/29/15.
 * Listener for User Actions, onError is call each time that an action not work
 */
public interface UserListener {
    /**
     * Call when login is done
     * @param accessToken   accessToken use to consume the api server
     * @param user          User informations.
     */
    public void isLogged(AccessToken accessToken, User user);

    /**
     * Call when update is done
     * @param isUpdated     Say if the user is updated or not
     */
    public void isUpdated(boolean isUpdated);

    /**
     * Call when delete is done
     * @param isDeleted     Say if the user is deleted or not
     */
    public void isDeleted(boolean isDeleted);

    /**
     * Say if the user is created or not
     * @param user          boolean User we have created
     */
    public void isCreated(boolean user);

    /**
     * Get user with all films include
     * @param films         List of films received
     */
    public void gettingFilms(ArrayList<Film> films);

    /**
     * Call when an action was not executed correctly
     * @param e             Exception return by action
     */
    public void onError(Throwable e);
}
