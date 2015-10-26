package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.UserRepository;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.IsLogged;

/**
 * Created by elerion on 10/26/15.
 */
public class Login implements UserRepository.LoginCallback<User> {

    private String tag = Login.class.getName().toString();

    IsLogged isLogged;

    public Login(IsLogged isLogged) {
        this.isLogged = isLogged;
    }

    @Override
    public void onSuccess(AccessToken token, User currentUser) {
        this.isLogged.isLogged(token, currentUser);
        Log.d(tag, currentUser.toString());
    }

    @Override
    public void onError(Throwable t) {
        Log.e(tag, "Callback login error", t);
    }
}
