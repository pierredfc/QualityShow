package videolibrary.street.quality.qualityshow.async;


import android.os.AsyncTask;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.AccountListener;

public class HomeAsyncTask extends AsyncTask<String, Integer, List<Serie>> {

    private final AccountListener accountListener;

    public HomeAsyncTask(AccountListener requestListener) {
        accountListener = requestListener;
    }

    @Override
    protected List<Serie> doInBackground(String... params) {
        return QualityShowApplication.getUserHelper().getCurrentUser().getSeries();
    }

    @Override
    protected void onPostExecute(List<Serie> response) {
        accountListener.onShowRetrieved(response);
    }
}
