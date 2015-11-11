package videolibrary.street.quality.qualityshow.ui.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeChildHolder extends ChildViewHolder {
    public TextView synopsys;
    public CheckBox episodeSeen;
    public Episode episode;

    public EpisodeChildHolder(View itemView) {
        super(itemView);

        synopsys = (TextView) itemView.findViewById(R.id.synopsys_episode);
        episodeSeen = (CheckBox) itemView.findViewById(R.id.child_list_item_episode_viewd_check_box);
    }
    public void setView(Episode episode) {
        this.episode=episode;
    }
}
