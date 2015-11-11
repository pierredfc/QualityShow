package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ExploreActivity;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;
import videolibrary.street.quality.qualityshow.ui.utils.DividerItemDecoration;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Pierre on 03/11/2015.
 */
public class ExploreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestListener, ClickListener {
    public SwipeRefreshLayout rootView;
    private RecyclerView showsView;

    boolean userConnected;
    private ShowsAdapter showsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_explore, container, false);
        showsView = (RecyclerView) rootView.findViewById(R.id.explore_recyclerView);
        this.showsView.setHasFixedSize(true);
        this.showsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        checkUserConnected();

        ((ExploreActivity) getActivity()).getSupportActionBar().setTitle(R.string.explore);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
        requestAsyncTask.execute(Requests.SERIE_TRENDING, "");
    }

    private void checkUserConnected() {
        if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
            userConnected = false;
        } else {
            userConnected = true;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if (response != null) {
            Serie[] array_series = response.toArray(new Serie[response.size()]);
            List<Serie> series = Arrays.asList(array_series);

            showsAdapter = new ShowsAdapter(series, null, this);
            showsView.setAdapter(showsAdapter);
        }
    }

    @Override
    public void onItemClick(Object item) {
        if(item instanceof  Serie) {
            Intent intent = new Intent(QualityShowApplication.getContext(), ShowActivity.class);
            intent.putExtra("isSearch", true);
            intent.putExtra("isMovie", false);
            intent.putExtra("show", (Serie) item);
            startActivity(intent);
        } else if(item instanceof Film){
            Intent intent = new Intent(QualityShowApplication.getContext(),ShowActivity.class);
            intent.putExtra("isSearch", true);
            intent.putExtra("isMovie", true);
            intent.putExtra("show", (Film) item);
            startActivity(intent);
        }
    }
}
