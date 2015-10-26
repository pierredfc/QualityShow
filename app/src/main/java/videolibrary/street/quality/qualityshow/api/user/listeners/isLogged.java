package videolibrary.street.quality.qualityshow.api.user.listeners;

import com.strongloop.android.loopback.AccessToken;

import videolibrary.street.quality.qualityshow.api.user.dao.User;

/**
 * Created by elerion on 10/26/15.
 */
public interface IsLogged {
    public void isLogged(AccessToken accessToken, User user);
}
