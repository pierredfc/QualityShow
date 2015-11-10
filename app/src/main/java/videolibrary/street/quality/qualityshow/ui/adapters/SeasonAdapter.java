package videolibrary.street.quality.qualityshow.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;
import videolibrary.street.quality.qualityshow.ui.holders.SeasonHolder;

/**
 * Created by Sacael on 06/11/2015.
 */
public class SeasonAdapter extends ArrayAdapter<Saison> {
    ArrayList<Saison> results;
    private ClickListener clickListener;

    public SeasonAdapter(Context context,ArrayList<Saison> saisons) {
        super(context,0,saisons);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.season_items, parent, false);
        }

        SeasonHolder holder = new SeasonHolder(convertView);
        Saison item = (Saison) getItem(position);

        if (item != null) {
            Object p = item.getPoster().get("thumb");
            String image = (String) p;


            if (image == null) {
                Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
                holder.image.setImageDrawable(drawable);
            } else {
                Picasso.with(QualityShowApplication.getContext()).load(image).into(holder.image);
            }
            holder.number.setText("Season " + item.getNumber().toString());

            holder.episodes.setText(item.getAired_episodes().toString() + " episodes aired.");
            if(item.getEpisodes() != null)
                holder.setView(item, clickListener);
        }

        return convertView;
    }
}

