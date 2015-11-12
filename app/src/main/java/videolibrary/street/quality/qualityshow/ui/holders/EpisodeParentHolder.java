package videolibrary.street.quality.qualityshow.ui.holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import videolibrary.street.quality.qualityshow.R;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeParentHolder extends ParentViewHolder {
    public TextView episodeTitle;
    public TextView numeroEpisode;

    public EpisodeParentHolder(View itemView) {
        super(itemView);

        episodeTitle = (TextView) itemView.findViewById(R.id.title_episode);
        numeroEpisode = (TextView) itemView.findViewById(R.id.numero_episode);
    }
}
