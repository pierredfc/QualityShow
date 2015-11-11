package videolibrary.street.quality.qualityshow.api.user.repositories;

import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.security.interfaces.ECPublicKey;
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
        contract.addItem(new RestContractItem("/" + restUrl + "/:urlSaisonId/episodes", "POST"), className + ".addEpisode");
        contract.addItem(new RestContractItem("/" + restUrl + "/:urlSaisonId/episodes/:urlEpisodeId", "DELETE"), className + ".deleteEpisode");
        contract.addItem(new RestContractItem("/" + restUrl + "/:urlSaisonId/episodes/:urlEpisodeId", "PUT"), className + ".updateEpisode");

        return contract;
    }

    public void getEpisodes(int saisonId, Adapter.JsonArrayCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", saisonId);
        invokeStaticMethod("getEpisodes", params, callback);
    }

    public void addEpisode(int saisonId, Episode episode, Adapter.JsonObjectCallback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("urlSaisonId", saisonId);
        params.putAll(episode.toMap());
        invokeStaticMethod("addEpisode", params, callback);
    }

    public void deleteEpisode(int saisonId, int episodeId, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("urlSaisonId", saisonId);
        params.put("urlEpisodeId", episodeId);
        invokeStaticMethod("deleteEpisode", params, callback);
    }

    public void updateEpisode(int saisonId, Episode episode, Adapter.Callback callback){
        HashMap<String, Object> params = new HashMap<>();
        params.put("urlSaisonId", saisonId);
        params.put("urlEpisodeId", (int)episode.getId());
        params.putAll(episode.toMap());
        params.remove("see");
        params.remove("See");
        params.put("See", episode.getSee());
        invokeStaticMethod("updateEpisode", params, callback);
    }
}
