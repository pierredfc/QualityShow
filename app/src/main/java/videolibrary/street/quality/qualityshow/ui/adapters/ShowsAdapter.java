package videolibrary.street.quality.qualityshow.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.ui.holders.ShowsHolder;


public class ShowsAdapter extends RecyclerView.Adapter<ShowsHolder> {

    ClickListener clickListener;
    List<Serie> series;
    List<Film> films;


    public ShowsAdapter(List<Serie> list_series, List<Film> list_movies, ClickListener listener) {
        series = list_series;
        films = list_movies;
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
            if(series != null && films == null){
                Serie serie = series.get(position);

                if (serie != null) {
                    Object photo = serie.getPoster().get("thumb");

                    Picasso.with(QualityShowApplication.getContext())
                            .load((String) photo)
                            .error(R.drawable.undefined_poster)
                            .into(holder.imageView);

                    holder.setView(serie, clickListener);
                }
            } else if(films != null && series == null) {
                Film film = films.get(position);

                if (film != null) {
                    HashMap<String, String> posters = film.getPoster();
                    if(posters != null){
                        Object photo = film.getPoster().get("thumb");

                        Picasso.with(QualityShowApplication.getContext())
                                .load((String) photo)
                                .error(R.drawable.undefined_poster)
                                .into(holder.imageView);

                        holder.setView(film, clickListener);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(series != null && films == null){
            return series.size();
        } else if(films != null && series == null) {
            return films.size();
        } else {
            return films.size() + series.size();
        }
    }
}
