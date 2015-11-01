package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.UserRepository;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/26/15.
 * Class contaning all callback for by userRepository
 * Each need an listener {@link UserListener}
 */
public class UserCallbacks {

    public static class GetAllUsers implements ListCallback<User> {
        private UserListener listener;

        public GetAllUsers(UserListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess(List<User> objects) {
            Log.d(Constants.Log.TAG, "Users received");
            ArrayList<User> users = new ArrayList<User>();
            users = (ArrayList<User>)objects;
            this.listener.getAllUsers(users);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

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
            this.listener.userIsDeleted(true);
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
            Log.d(Constants.Log.TAG, "User is logged");
            this.listener.isLogged(token, currentUser);
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
            this.listener.userIsUpdated(true);
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
            this.listener.userIsCreated(true);
        }

        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + CreateCallback.class.getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class LogoutOutCallback implements VoidCallback{

        private UserListener listener;

        public LogoutOutCallback(UserListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSuccess() {
            Log.d(Constants.Log.TAG, "User logout");
            this.listener.userIsLogout();
        }

        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG,Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class FindUserByIdCallback implements ObjectCallback<User> {

        private UserListener listener;

        public FindUserByIdCallback(UserListener listener) {
            this.listener = listener;
        }


        @Override
        public void onSuccess(User object) {
            Log.d(Constants.Log.TAG, "User is find");
            this.listener.userIsFind(object);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

}
