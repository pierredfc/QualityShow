package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.SeasonRequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.ui.adapters.EpisodeExpandableAdapter;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeChild;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeParentObject;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeFragment extends Fragment{
    Saison season;
    Integer serieId;
    View rootView;
    RecyclerView resultsView;
    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }



    public void setSeason(Saison season) {
        this.season = season;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ShowActivity showActivity=(ShowActivity)getActivity();
        rootView = inflater.inflate(R.layout.fragment_season, container, false);
        resultsView = (RecyclerView)rootView.findViewById(R.id.season_episodes);
        resultsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(season!=null) {
            ArrayList<ParentObject> parentObjects = new ArrayList<>();
            for(Object o: season.getEpisodes()){
                Episode e = (Episode)o;
                EpisodeParentObject ep= new EpisodeParentObject();
                ep.setTitle(String.format("E%02d: %s", e.getNumber(), e.getTitle()));
                ArrayList<Object> childList=new ArrayList<>();
                String over = e.getOverview();
                if( over==null){
                    over="No description for this episode available";
                }
                childList.add(new EpisodeChild(over,false));
                ep.setChildObjectList(childList);
                parentObjects.add(ep);
            }
            if (getActivity()!=null) {
                EpisodeExpandableAdapter episodeExpandableAdapter = new EpisodeExpandableAdapter(getActivity(), parentObjects);
                episodeExpandableAdapter.setParentAndIconExpandOnClick(true);
                resultsView.setAdapter(episodeExpandableAdapter);
            }
        }
        return rootView;
    }
}
