package videolibrary.street.quality.qualityshow.async;

import android.os.AsyncTask;

import videolibrary.street.quality.qualityshow.listeners.AdderListener;
import videolibrary.street.quality.qualityshow.utils.ShowAdder;

public class ShowAdderAsyncTask extends AsyncTask<String, Integer, Boolean> {
    private final AdderListener adderListener;
    public ShowAdderAsyncTask(AdderListener adderListener) {
        this.adderListener = adderListener;
    }

    @Override
    protected Boolean  doInBackground(String... params) {
        ShowAdder showadder = new ShowAdder();
        showadder.addShow(params[0], params[1]);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        adderListener.onshowAdded(response);
    }
}

