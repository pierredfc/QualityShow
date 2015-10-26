package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.remoting.adapters.RestContract;

import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.ApiConstants;

/**
 * Created by elerion on 10/26/15.
 */
public class UserRepository extends com.strongloop.android.loopback.UserRepository<User> {


    public UserRepository(){
        super(ApiConstants.USER_MODEL_NAME, ApiConstants.USER_API_NAME, User.class);
    }

    public UserRepository(String className, Class<User> userClass) {
        super(className, userClass);
    }

    public UserRepository(String className, String nameForRestUrl, Class<User> userClass) {
        super(className, nameForRestUrl, userClass);
    }

}
