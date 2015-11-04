package videolibrary.street.quality.qualityshow.async;

import android.os.AsyncTask;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.repositories.SerieRepository;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanMovieItem;
import videolibrary.street.quality.qualityshow.utils.Requests;

public class RequestAsyncTask extends AsyncTask<String, Integer, List<Object>> {
    private final RequestListener requestListener;
    public RequestAsyncTask(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    @Override
    protected List<Object> doInBackground(String... params) {
        return Requests.search(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(List<Object> response) {
        requestListener.onResponseReceived(response);
    }
}

