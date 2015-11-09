package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;

import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

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

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/films", "GET"), clasName + ".getFilms");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:userId/films", "POST"), clasName + ".addFilm");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:userId/films/:filmId", "DELETE"), clasName + ".deleteFilm");

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/series", "GET"), clasName + ".getSeries");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:userId/series", "POST"), clasName + ".addSerie");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:userId/series/:serieId", "DELETE"), clasName + ".deleteSerie");

        return contract;
    }

    public void getFilms(int userId, boolean categories, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", userId);
        if(categories)
            params.put("filter[include]", "categories");
        invokeStaticMethod("getFilms", params, callback);
    }

    public void addFilm(int userId, Film film, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.putAll(film.toMap());
        invokeStaticMethod("addFilm", params, callback);
    }

    public void deleteFilm(int userId, int filmId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("filmId", filmId);
        invokeStaticMethod("deleteFilm", params, callback);
    }


    public void getSeries(int userId, boolean categories, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", userId);
        if(categories)
            params.put("filter[include]", "categories");
        invokeStaticMethod("getSeries", params, callback);
    }

    public void addSerie(int userId, Serie serie, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.putAll(serie.toMap());
        invokeStaticMethod("addSerie", params, callback);
    }

    public void deleteSerie(int userId, int serieId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("serieId", serieId);
        invokeStaticMethod("deleteSerie", params, callback);
    }

}
