package videolibrary.street.quality.qualityshow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;


public class PlanningAdapter extends RecyclerView.Adapter<PlanningHolder> {

    ClickListener clickListener;
    List<Episode> episodes;

    public PlanningAdapter(List<Episode> eps, ClickListener listener){
        episodes = eps;
        clickListener = listener;
    }

    @Override
    public PlanningHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.planning_items, parent, false);
        return new PlanningHolder(v);
    }

    @Override
    public void onBindViewHolder(PlanningHolder holder, int position) {
        Episode episode = episodes.get(position);

       // holder.episode_titleView.setText(); ;
        holder.episodeView.setText(episode.getNumber()); ;
        holder.serie_titleView.setText(episode.getTitle());

       // Picasso.with(QualityShowApplication.getContext()).load(episode.).into(holder.imageView);
        holder.setView(episode, clickListener);

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
