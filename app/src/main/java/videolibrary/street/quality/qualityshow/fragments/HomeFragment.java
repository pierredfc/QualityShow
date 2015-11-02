package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import videolibrary.street.quality.qualityshow.MainActivity;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.adapters.PlanningAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.HomeAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.AccountListener;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AccountListener{

    public SwipeRefreshLayout rootView;
    private RecyclerView yesterdayView;
    private RecyclerView todayView;
    private RecyclerView tomorrowView;


    private PlanningAdapter yesterdayAdapter;
    private PlanningAdapter todayAdapter;
    private PlanningAdapter tomorrowAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.home_fragment, container, false);

        yesterdayView = (RecyclerView)  rootView.findViewById(R.id.yesterday_listview);
        todayView = (RecyclerView)  rootView.findViewById(R.id.today_listview);
        tomorrowView = (RecyclerView)  rootView.findViewById(R.id.tomorrow_listview);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        new HomeAsyncTask(this).execute();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onShowRetrieved(List<Serie> series) {
        List<Episode> episodes;
        for(Serie serie: series){
            //@TODO
        }

        yesterdayAdapter = new PlanningAdapter(episodes, (MainActivity) getActivity());
    }
}
