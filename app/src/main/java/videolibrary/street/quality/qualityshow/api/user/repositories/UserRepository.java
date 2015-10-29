package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;

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

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        String clasName = getClassName();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/films", "GET"), clasName + ".films");

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id", "DELETE"), clasName + ".delete");


        return contract;
    }

    public void films(int userId, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", userId);
//        params.put("filter[include][films]", "categories");
//        params.put("filter[include][series][saisons]", "episodes");
//        params.put("filter[include][series]", "categories");

        invokeStaticMethod("films", params, callback);
    }

    public void create(User user, Adapter.JsonObjectCallback callback){

    }
}
