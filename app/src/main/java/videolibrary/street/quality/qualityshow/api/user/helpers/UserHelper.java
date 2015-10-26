package videolibrary.street.quality.qualityshow.api.user.helpers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.strongloop.android.loopback.AccessToken;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.IsLogged;
import videolibrary.street.quality.qualityshow.api.user.repositories.UserRepository;

/**
 * Created by elerion on 10/26/15.
 */
public class UserHelper implements IsLogged {

    User currentUser;
    AccessToken accessToken;

    private final ApiAdapter apiAdapter;
    private final UserRepository userRepository;

    public UserHelper(Context context) {
        apiAdapter = new ApiAdapter(context, ApiConstants.API_URL);
        userRepository = apiAdapter.createRepository(UserRepository.class);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public void login(String email, String password){
        userRepository.loginUser(email, password, new videolibrary.street.quality.qualityshow.api.user.callbacks.Login((IsLogged)this));
    }

    public void login(String email, String password, IsLogged isLogged){
        userRepository.loginUser(email, password, new videolibrary.street.quality.qualityshow.api.user.callbacks.Login(isLogged));
    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {
        this.setAccessToken(accessToken);
        this.setCurrentUser(user);
    }
}
