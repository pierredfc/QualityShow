package videolibrary.street.quality.qualityshow.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.ui.holders.ShowsHolder;


public class ShowsAdapter extends RecyclerView.Adapter<ShowsHolder> {

    ClickListener clickListener;
    List<Serie> series;

    public ShowsAdapter(List<Serie> list_serie, ClickListener listener) {
        series = list_serie;
        clickListener = listener;
    }

    @Override
    public ShowsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shows_items, parent, false);
        return new ShowsHolder(v);
    }

    @Override
    public void onBindViewHolder(ShowsHolder holder, int position) {
        if (position < getItemCount()) {
            Serie serie = series.get(position);

            if (serie != null) {
                Object photo = serie.getPoster().get("thumb");

                Picasso.with(QualityShowApplication.getContext())
                        .load((String) photo)
                        .placeholder(R.drawable.undefined_poster)
                        .error(R.drawable.undefined_poster)
                        .into(holder.imageView);

                holder.setView(serie, clickListener);
            }
        }


    }

    @Override
    public int getItemCount() {
        return series.size();
    }
}
