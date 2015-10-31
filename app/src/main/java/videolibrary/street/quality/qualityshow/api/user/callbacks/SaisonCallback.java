package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.listeners.SaisonListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.SaisonRepository;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/31/15.
 */
public class SaisonCallback {

    public static class GetSaisonsCallback extends Adapter.JsonArrayCallback{

        private SaisonListener listener;

        public GetSaisonsCallback(SaisonListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON array or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON array.
         */
        @Override
        public void onSuccess(JSONArray response) {
            ArrayList<Saison> saisons = new ArrayList<>();
            SaisonRepository repository = new SaisonRepository();
            if(response != null){
                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = null;

                    try {
                        object = response.getJSONObject(i);

                        Saison saison = object != null
                                ? repository.createObject(JsonUtil.fromJson(object))
                                : null;
                        if (saison != null)
                            saisons.add(saison);
                    } catch (JSONException e) {
                        Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), e);
                        this.listener.onError(e);
                    }
                }
            }
            Log.d(Constants.Log.TAG, "Saisons received");
            this.listener.getSaisons(saisons);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class AddSaisonCallback extends Adapter.JsonObjectCallback{
        private SaisonListener listener;

        public AddSaisonCallback(SaisonListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully and the
         * response is a JSON object or <code>null</code> if the response
         * string is "null".
         *
         * @param response The JSON object.
         */
        @Override
        public void onSuccess(JSONObject response) {
            Log.d(Constants.Log.TAG, "Saison added");
            SaisonRepository repository = new SaisonRepository();
            Saison saison = response != null
                    ? repository.createObject(JsonUtil.fromJson(response))
                    : null;
            this.listener.saisonIsAdded(saison);
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }

    public static class DeleteSaisonCallback implements Adapter.Callback{
        private SaisonListener listener;

        public DeleteSaisonCallback(SaisonListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully.
         *
         * @param response The HTTP response body.
         */
        @Override
        public void onSuccess(String response) {
            Log.d(Constants.Log.TAG, "Saison delete");
            this.listener.saisonIsDeleted();
        }

        /**
         * The method invoked when an error occurs.
         *
         * @param t The Throwable.
         */
        @Override
        public void onError(Throwable t) {
            Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), t);
            this.listener.onError(t);
        }
    }
}
