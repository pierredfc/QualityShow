package videolibrary.street.quality.qualityshow.api.user.dao;

import com.strongloop.android.remoting.Repository;

import java.util.Map;

/**
 * Created by elerion on 10/26/15.
 * Class look like model on server api
 */
public class User extends com.strongloop.android.loopback.User {

    String created, lastUpdated;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return String.format("User : " + this.getCreationParameters().toString());
    }
}
