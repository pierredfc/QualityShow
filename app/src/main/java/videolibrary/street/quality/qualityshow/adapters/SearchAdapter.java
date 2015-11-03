package videolibrary.street.quality.qualityshow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;


public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    List<Serie> results;

    public SearchAdapter(List<Serie> results) {
        this.results = results;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        if (position < getItemCount()) {
            Serie item = (Serie) results.get(position);

            if (item != null) {
                Object p = item.getPoster().get("thumb");
                String image = (String) p;
                Picasso.with(QualityShowApplication.getContext()).load(image).into(holder.image);
                holder.name.setText(item.getTitle());
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
