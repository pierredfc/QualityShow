package videolibrary.street.quality.qualityshow.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;

/**
 * Created by Sacael on 06/11/2015.
 */
public class SeasonAdapter extends ArrayAdapter<Object> {
    List<Object> results;
    private ClickListener clickListener;

    public SeasonAdapter(Context context,List<Object> saisons) {
        super(context,0,saisons);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.season_items, parent, false);
        }

        SeasonHolder holder = new SeasonHolder(convertView);
        Saison item = (Saison)getItem(position);

        if (item != null) {
            Object p = item.getPoster().get("thumb");
            String image = (String) p;


            if (image == null) {
                Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
                holder.image.setImageDrawable(drawable);
            } else {
                Picasso.with(QualityShowApplication.getContext()).load(image).into(holder.image);
            }
            holder.number.setText("Seasons " + item.getNumber().toString());

            holder.episodes.setText(item.getAired_episodes().toString() + " Episodes aired");
            holder.setView(item, clickListener);
        }

        return convertView;
    }
}
