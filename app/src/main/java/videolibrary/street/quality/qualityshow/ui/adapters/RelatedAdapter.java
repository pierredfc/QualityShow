package videolibrary.street.quality.qualityshow.ui.adapters;

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
import videolibrary.street.quality.qualityshow.ui.holders.RelatedHolder;
import videolibrary.street.quality.qualityshow.ui.holders.SearchHolder;

/**
 * Created by Sacael on 12/11/2015.
 */
public class RelatedAdapter extends RecyclerView.Adapter<RelatedHolder> {

    List<Object> results;
    private ClickListener clickListener;

    public RelatedAdapter(List<Object> results, ClickListener listener) {
        this.results = results;
        clickListener = listener;
    }

    @Override
    public RelatedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_item, parent, false);
        return new RelatedHolder(v);
    }
    @Override
    public void onBindViewHolder(RelatedHolder holder, int position) {
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
                    holder.setView(item, clickListener);
                }
            }
            if (results.get(position) instanceof Film) {
                Film item = (Film) results.get(position);
                if (item != null) {
                    String image = null;
                    if (item.getPoster() != null) {
                        Object p = item.getPoster().get("thumb");
                        image = (String) p;
                    }

                    if (image == null) {
                        Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
                        holder.image.setImageDrawable(drawable);
                    } else {
                        Picasso.with(QualityShowApplication.getContext()).load(image).into(holder.image);
                    }
                    holder.name.setText(item.getTitle());
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
