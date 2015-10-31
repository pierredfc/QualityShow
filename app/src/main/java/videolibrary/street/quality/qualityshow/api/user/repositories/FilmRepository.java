package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/26/15.
 */
public class FilmRepository extends ModelRepository<Film> {

    public FilmRepository() {
        super(ApiConstants.FILM_MODEL_NAME, ApiConstants.FILM_API_NAME, Film.class);
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        String className = getClassName();
        String restUrl = getNameForRestUrl();

        contract.addItem(new RestContractItem("/" + restUrl + "/:id/categories", "GET"), className + ".getCategories");
        contract.addItem(new RestContractItem("/" + restUrl + "/:id/categories", "POST"), className + ".addCategory");
        contract.addItem(new RestContractItem("/" + restUrl + "/:filmId/categories/:categoryId", "DELETE"), className + ".deleteCategory");

        return contract;
    }

    public void getCategories(int filmId, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", filmId);
        invokeStaticMethod("getCategories", params, callback);
    }

    public void addCategory(int filmId, Category category, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", filmId);
        params.putAll(category.toMap());
        invokeStaticMethod("addCategory", params, callback);
    }

    public void deleteCategory(int filmId, int categoryId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("filmId", filmId);
        params.put("categoryId", categoryId);
        invokeStaticMethod("deleteCategory", params, callback);
    }
}
