package videolibrary.street.quality.qualityshow.api.user.dao;

/**
 * Created by elerion on 10/26/15.
 */
public class User extends com.strongloop.android.loopback.User {

    @Override
    public String toString() {
        return String.format("User : { \n id : %s \n email : %s \n}", this.getId(), this.getEmail());
    }
}
