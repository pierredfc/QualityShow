package videolibrary.street.quality.qualityshow;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import java.util.List;

import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanMovieItem;
import videolibrary.street.quality.qualityshow.utils.Requests;

public class SearchActivity extends Activity implements RequestListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
        requestAsyncTask.execute(Requests.MOVIE_SEARCH, "shining");
    }

    @Override
    public void onResponseReceived(List<BeanItem> response) {
        for (BeanItem item : response) {
            Log.d("Search", ((BeanMovieItem)item).getMovie().getTitle());
            Log.d("Search", ((BeanMovieItem)item).getMovie().getIds().getImdb());
        }
    }
}
