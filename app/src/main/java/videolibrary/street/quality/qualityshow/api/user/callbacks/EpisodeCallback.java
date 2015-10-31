package videolibrary.street.quality.qualityshow.api.user.callbacks;

import android.util.Log;

import com.strongloop.android.remoting.JsonUtil;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.listeners.EpisodeListener;
import videolibrary.street.quality.qualityshow.api.user.repositories.EpisodeRepository;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by elerion on 10/31/15.
 */
public class EpisodeCallback {

    public static class GetEpisodesCallback extends Adapter.JsonArrayCallback{

        private EpisodeListener listener;

        public GetEpisodesCallback(EpisodeListener listener) {
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
            ArrayList<Episode> episodes = new ArrayList<>();
            EpisodeRepository repository = new EpisodeRepository();
            if(response != null){
                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = null;

                    try {
                        object = response.getJSONObject(i);

                        Episode episode = object != null
                                ? repository.createObject(JsonUtil.fromJson(object))
                                : null;
                        if (episode != null)
                            episodes.add(episode);

                    } catch (JSONException e) {
                        Log.e(Constants.Log.TAG, Constants.Log.ERROR_MSG + getClass().getSimpleName(), e);
                        this.listener.onError(e);
                    }
                }
            }
            Log.d(Constants.Log.TAG, "Epsodes received");
            this.listener.getEpisodes(episodes);
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

    public static class AddEpisodeCallback extends Adapter.JsonObjectCallback{

        private EpisodeListener listener;

        public AddEpisodeCallback(EpisodeListener listener) {
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
            EpisodeRepository repository = new EpisodeRepository();
            Episode episode = response != null
                    ? repository.createObject(JsonUtil.fromJson(response))
                    : null;
            this.listener.episodeIsAdded(episode);
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

    public static class DeleteEpisodeCallback implements Adapter.Callback{

        private EpisodeListener listener;

        public DeleteEpisodeCallback(EpisodeListener listener) {
            this.listener = listener;
        }

        /**
         * The method invoked when the call completes successfully.
         *
         * @param response The HTTP response body.
         */
        @Override
        public void onSuccess(String response) {
            Log.d(Constants.Log.TAG, "Epispde deleted");
            this.listener.episodeIsDelete();
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
