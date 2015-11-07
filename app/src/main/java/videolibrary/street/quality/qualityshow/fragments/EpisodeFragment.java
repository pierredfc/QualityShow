package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.adapters.EpisodeAdapter;
import videolibrary.street.quality.qualityshow.adapters.SeasonAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.SeasonRequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeFragment extends Fragment implements RequestListener {
    Saison season;
    Integer serieId;

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    View rootView;
    ExpandableListView resultsView;

    public void setSeason(Saison season) {
        this.season = season;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_season, container, false);
        resultsView = (ExpandableListView)rootView.findViewById(R.id.season_episodes);
        SeasonRequestAsyncTask seasonRequestAsyncTask = new SeasonRequestAsyncTask(this);
        seasonRequestAsyncTask.execute(String.valueOf(serieId),String.valueOf(season.getNumber()));
        return rootView;
    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if( response != null && response.size() > 0){
            EpisodeAdapter episodeAdapter = new EpisodeAdapter((ShowActivity) getActivity(), (ArrayList<Object>) response);
            resultsView.setAdapter(episodeAdapter);
        }
    }
    public void justifyListViewHeightBasedOnChildren (ExpandableListView listView) {

        ExpandableListAdapter adapter = listView.getExpandableListAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int j = 0;j< adapter.getGroupCount();j++) {

            View listItem = adapter.getGroupView(j,listView.isGroupExpanded(j), null, vg);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            for (int i = 0; i < adapter.getChildrenCount(j); i++) {
                View listItem2 = adapter.getChildView(j,i,listView.isGroupExpanded(j), null, vg);
                listItem.measure(0, 0);
                totalHeight += listItem2.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getGroupCount() - 1))*2;
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


}
