package videolibrary.street.quality.qualityshow.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;


public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    List<Object> results;
    private ClickListener clickListener;

    public SearchAdapter(List<Object> results, ClickListener listener) {
        this.results = results;
        clickListener = listener;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        if (position < getItemCount()) {
            if (results.get(position) instanceof Serie) {
                Serie item = (Serie) results.get(position);

                if (item != null) {
                    Object p = item.getPoster().get("thumb");
                    String image = (String) p;


                    if (image == null) {
                        Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
                        holder.image.setImageDrawable(drawable);
                    } else {
                        Picasso.with(QualityShowApplication.getContext()).load(image).into(holder.image);
                    }
                    holder.name.setText(item.getTitle());
                    Integer year = item.getYear();
                    if(year != null){
                        holder.year.setText(Integer.toString(year));
                    } else {
                        holder.year.setText(" ");
                    }

                    holder.setView(item, clickListener);
                }
            }
            if (results.get(position) instanceof Film) {
                Film item = (Film) results.get(position);
                if (item != null) {
                    Object p = item.getPoster().get("thumb");
                    String image = (String) p;


                    if (image == null) {
                        Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
                        holder.image.setImageDrawable(drawable);
                    } else {
                        Picasso.with(QualityShowApplication.getContext()).load(image).into(holder.image);
                    }
                    holder.name.setText(item.getTitle());
                    Integer year = item.getYear();
                    if(year != null){
                        holder.year.setText(Integer.toString(year));
                    } else {
                        holder.year.setText(" ");
                    }

                    holder.setView(item, clickListener);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
