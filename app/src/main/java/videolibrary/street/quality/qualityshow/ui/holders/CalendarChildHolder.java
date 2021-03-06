package videolibrary.street.quality.qualityshow.ui.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarChildHolder extends ChildViewHolder {
    public TextView serieName;
    public TextView saisonNumber;
    public TextView episodeNumber;
    public TextView episodeName;
    public Episode episode;
    public ImageView image;

    public CalendarChildHolder(View itemView) {
        super(itemView);

        episodeNumber = (TextView) itemView.findViewById(R.id.calendar_episode_entry);
        serieName = (TextView) itemView.findViewById(R.id.calendar_serie_name);
        saisonNumber = (TextView) itemView.findViewById(R.id.calendar_saison_entry);
        episodeName = (TextView) itemView.findViewById(R.id.calendar_episode_name_entry);
        image = (ImageView) itemView.findViewById(R.id.calendar_image_view);
    }
    public void setView(Episode episode) {
        this.episode=episode;
    }
}