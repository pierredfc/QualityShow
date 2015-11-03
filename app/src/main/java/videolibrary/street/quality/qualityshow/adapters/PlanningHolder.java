package videolibrary.street.quality.qualityshow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;


public class PlanningHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View view;
    public ImageView imageView;
    public TextView serie_titleView;
    public TextView episodeView;
    public TextView episode_titleView;


    private ClickListener clickListener;
    private Serie serie;

    public PlanningHolder(View itemView){
        super(itemView);
        view = itemView;

        imageView = (ImageView) itemView.findViewById(R.id.p_image_serie);
      /*  serie_titleView = (TextView) itemView.findViewById(R.id.p_serie_title);
        episodeView = (TextView) itemView.findViewById(R.id.p_serie_season_and_episode);
        episode_titleView = (TextView) itemView.findViewById(R.id.p_serie_episode_name);*/
    }

    public void setView(Serie serie, ClickListener listener){
        clickListener = listener;
        this.serie = serie;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
