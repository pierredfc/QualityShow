package videolibrary.street.quality.qualityshow;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import java.util.List;

import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanMovieItem;
import videolibrary.street.quality.qualityshow.utils.Requests;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handleIntent(getIntent());

       /* RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
        requestAsyncTask.execute(Requests.MOVIE_SEARCH, "shining");*/
    }

  /*  @Override
    public void onResponseReceived(List<BeanItem> response) {
        for (BeanItem item : response) {
            Log.d("Search", ((BeanMovieItem)item).getMovie().getTitle());
            Log.d("Search", ((BeanMovieItem)item).getMovie().getIds().getImdb());
        }
    }*/

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private void handleIntent(Intent intent) {
        Log.d("handleIntent","oK");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Log.d("SEARCH", SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
