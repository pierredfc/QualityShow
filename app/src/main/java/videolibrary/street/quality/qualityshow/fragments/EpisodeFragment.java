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
import android.widget.CheckBox;
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

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.helpers.SaisonHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.SerieHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.EpisodeListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SaisonListener;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.async.SeasonRequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.ui.adapters.EpisodeExpandableAdapter;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeChild;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeParentObject;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeFragment extends Fragment implements RequestListener, View.OnClickListener,EpisodeListener {
    Saison season;
    Integer serieId =null;
    View rootView;
    RecyclerView resultsView;
    List<Episode> episodes = null;

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        episodes = episodes;
    }


    public void setSeason(Saison season) {
        this.season = season;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ShowActivity showActivity = (ShowActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_season, container, false);
        resultsView = (RecyclerView) rootView.findViewById(R.id.season_episodes);
        resultsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (season != null) {
            if (episodes != null) {
                buildRecyclerFromEpisodes(episodes);
            } else {
                SeasonRequestAsyncTask seasonRequestAsyncTask = new SeasonRequestAsyncTask(this);
                seasonRequestAsyncTask.execute(String.valueOf(serieId), String.valueOf(season.getNumber()));
            }
        }
        return rootView;
    }

    private void buildRecycler(List<Object> episodes) {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (Object o : episodes) {
            Episode e = (Episode) o;
            EpisodeParentObject ep = new EpisodeParentObject();
            ep.setTitle(String.format("E%02d: %s", e.getNumber(), e.getTitle()));
            ArrayList<Object> childList = new ArrayList<>();
            String over = e.getOverview();
            if (over == null) {
                over = "No description for this episode available";
            }
            childList.add(new EpisodeChild(over, false,e));
            ep.setChildObjectList(childList);
            parentObjects.add(ep);
        }
        if (getActivity() != null) {
            EpisodeExpandableAdapter episodeExpandableAdapter = new EpisodeExpandableAdapter(getActivity(), parentObjects,this);
            episodeExpandableAdapter.setParentAndIconExpandOnClick(true);
            resultsView.setAdapter(episodeExpandableAdapter);
        }
    }

    private void buildRecyclerFromEpisodes(List<Episode> episodes) {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (Object o : episodes) {
            Episode e = (Episode) o;
            EpisodeParentObject ep = new EpisodeParentObject();
            ep.setTitle(String.format("E%02d: %s", e.getNumber(), e.getTitle()));
            ArrayList<Object> childList = new ArrayList<>();
            String over = e.getOverview();
            if (over == null) {
                over = "No description for this episode available";
            }
            childList.add(new EpisodeChild(over,e.getSee(),e));
            ep.setChildObjectList(childList);
            parentObjects.add(ep);
        }
        if (getActivity() != null) {
            EpisodeExpandableAdapter episodeExpandableAdapter = new EpisodeExpandableAdapter(getActivity(), parentObjects,this);
            episodeExpandableAdapter.setParentAndIconExpandOnClick(true);
            resultsView.setAdapter(episodeExpandableAdapter);
        }
    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if (response != null && response.size() > 0) {
            buildRecycler(response);
        }
    }


    @Override
    public void episodeIsAdded(Episode episode) {

    }

    @Override
    public void getEpisodes(ArrayList<Episode> episodes) {

    }

    @Override
    public void episodeIsDelete() {

    }

    @Override
    public void episodeIsUpdated() {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onClick(View view) {
        if (serieId != null && QualityShowApplication.getUserHelper().getCurrentUser()!=null && QualityShowApplication.getUserHelper().getCurrentUser().getSerieById(serieId)!=null) {
            SaisonHelper saisonHelper = new SaisonHelper(QualityShowApplication.getContext());
            CheckBox cb = (CheckBox) view;
            if (cb.isChecked()) {
                saisonHelper.addEpisode(season, ((EpisodeChild) cb.getTag()).getEpisode(), this);
            } else {
                saisonHelper.deleteEpisode(season, ((EpisodeChild) cb.getTag()).getEpisode(), this);
            }
            ((EpisodeChild) cb.getTag()).setSeen(cb.isChecked());
            ((EpisodeChild) cb.getTag()).getEpisode().setSee(cb.isChecked());
        }
    }
}
