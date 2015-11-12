package videolibrary.street.quality.qualityshow.ui.holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarChildHolder extends ChildViewHolder {
    public TextView episodeEntry;
    public Episode episode;

    public CalendarChildHolder(View itemView) {
        super(itemView);

        episodeEntry = (TextView) itemView.findViewById(R.id.calendar_episode_entry);
    }
    public void setView(Episode episode) {
        this.episode=episode;
    }
}