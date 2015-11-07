package videolibrary.street.quality.qualityshow.ui.adapters;

import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeChild;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeChildHolder;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeParentHolder;
import videolibrary.street.quality.qualityshow.ui.holders.EpisodeParentObject;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeExpandableAdapter extends ExpandableRecyclerAdapter<EpisodeParentHolder,EpisodeChildHolder> {


    private LayoutInflater mInflater;
    private ArrayList<ParentObject> mParent;

    public EpisodeExpandableAdapter(Context context, ArrayList<ParentObject> parentItemList) {
        super(context, parentItemList);
        mParent=parentItemList;
        mInflater=LayoutInflater.from(context);

    }

    @Override
    public EpisodeParentHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.episode_item, viewGroup, false);
        return new EpisodeParentHolder(view);
    }

    @Override
    public EpisodeChildHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.episode_content_item, viewGroup, false);
        return new EpisodeChildHolder(view);
    }

    @Override
    public void onBindParentViewHolder(EpisodeParentHolder episodeParentHolder, int i, Object o) {
        EpisodeParentObject episode = (EpisodeParentObject) o;
        episodeParentHolder.episodeTitle.setText(episode.getTitle());
    }

    @Override
    public void onBindChildViewHolder(EpisodeChildHolder episodeChildHolder, int i, Object o) {
        EpisodeChild episodeChild=(EpisodeChild) o;
        episodeChildHolder.synopsys.setText(episodeChild.getOverview());
        episodeChildHolder.episodeSeen.setChecked(episodeChild.isSeen());
    }
}