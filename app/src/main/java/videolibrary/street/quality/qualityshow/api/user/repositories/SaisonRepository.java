package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.utils.ApiConstants;

/**
 * Created by elerion on 10/31/15.
 */
public class SaisonRepository extends ModelRepository<Saison> {

    public SaisonRepository() {
        super(ApiConstants.SAISON_MODEL_NAME, ApiConstants.SAISON_API_NAME, Saison.class);
    }

    @Override
    public RestContract createContract() {
        RestContract contract =  super.createContract();

        String className = getClassName();
        String restUrl = getNameForRestUrl();

        contract.addItem(new RestContractItem("/" + restUrl + "/:id/episodes", "GET"), className + ".getEpisodes");
        contract.addItem(new RestContractItem("/" + restUrl + "/:id/episodes", "POST"), className + ".addEpisode");
        contract.addItem(new RestContractItem("/" + restUrl + "/:saispnId/episodes/:episodeId", "DELETE"), className + ".deleteEpisode");

        return contract;
    }

    public void getEpisodes(int saisonId, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", saisonId);
        invokeStaticMethod("getEpisodes", params, callback);
    }

    public void addEpisode(int saisonId, Episode episode, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", saisonId);
        params.putAll(episode.toMap());
        invokeStaticMethod("addEpisode", params, callback);
    }

    public void deleteEpisode(int saisonId, int episodeId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("saisonId", saisonId);
        params.put("episodeId", episodeId);
        invokeStaticMethod("deleteEpisode", params, callback);
    }
}
