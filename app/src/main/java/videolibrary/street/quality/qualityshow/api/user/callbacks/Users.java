package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.loopback.callbacks.ListCallback;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.User;

/**
 * Created by elerion on 10/26/15.
 */
public class Users implements ListCallback<User> {

    private final String tag = Users.class.getName().toString();

    @Override
    public void onSuccess(List<User> objects) {
        for (User object : objects) {
            Log.d(tag, object.getEmail().toString());
        }
    }

    @Override
    public void onError(Throwable t) {
        Log.e(tag, "Get all user error", t);
    }
}
