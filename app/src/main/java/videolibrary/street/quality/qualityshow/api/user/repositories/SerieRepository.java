package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/30/15.
 */
public class SerieRepository extends ModelRepository<Serie> {
    /**
     * Creates a new Repository, associating it with the named remote class.
     *
     */
    public SerieRepository() {
        super(ApiConstants.SERIE_MODEL_NAME, ApiConstants.SERIE_API_NAME, Serie.class);
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        String className = getClassName();
        String restUrl = getNameForRestUrl();

        contract.addItem(new RestContractItem("/" + restUrl + "/:id/categories", "GET"), className + ".getCategories");
        contract.addItem(new RestContractItem("/" + restUrl + "/:id/categories", "POST"), className + ".addCategory");
        contract.addItem(new RestContractItem("/" + restUrl + "/:serieId/categories/:categoryId", "DELETE"), className + ".deleteCategory");

        contract.addItem(new RestContractItem("/" + restUrl + "/:id/saisons", "GET"), className + ".getSaisons");
        contract.addItem(new RestContractItem("/" + restUrl + "/:id/saisons", "POST"), className + ".addSaison");
        contract.addItem(new RestContractItem("/" + restUrl + "/:serieId/saisons/:saisonId", "DELETE"), className + ".deleteSaison");

        return contract;
    }

    public void getCategories(int serieId, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", serieId);
        invokeStaticMethod("getCategories", params, callback);
    }

    public void addCategory(int serieId, Category category, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", serieId);
        params.putAll(category.toMap());
        invokeStaticMethod("addCategory", params, callback);
    }

    public void deleteCategory(int serieId, int categoryId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("filmId", serieId);
        params.put("categoryId", categoryId);
        invokeStaticMethod("deleteCategory", params, callback);
    }

    public void getSaisons(int serieId, boolean episode, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", serieId);
        if (episode)
            params.put("filter[include]", "episodes");
        invokeStaticMethod("getSaisons", params, callback);
    }

    public void addSaison(int serieId, Saison saison, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", serieId);
        params.putAll(saison.toMap());
        invokeStaticMethod("addSaison", params, callback);
    }

    public void deleteSaison(int serieId, int saisonId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("filmId", serieId);
        params.put("saisonId", saisonId);
        invokeStaticMethod("deleteSaison", params, callback);
    }
}
