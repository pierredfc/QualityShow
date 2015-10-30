package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.UserRepository;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
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

}
