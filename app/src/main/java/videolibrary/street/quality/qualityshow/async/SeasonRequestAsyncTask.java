package videolibrary.street.quality.qualityshow.async;

import android.os.AsyncTask;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Requests;

public class SeasonRequestAsyncTask extends AsyncTask<String, Integer, List<Object>> {
    private final RequestListener requestListener;
    public SeasonRequestAsyncTask(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    @Override
    protected List<Object> doInBackground(String... params) {
        return Requests.SeasonSearch(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<Object> response) {
        requestListener.onResponseReceived(response);
    }
}

