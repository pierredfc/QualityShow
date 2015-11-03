package videolibrary.street.quality.qualityshow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.responseModel.BeanItem;
import videolibrary.street.quality.qualityshow.responseModel.BeanShowItem;


public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    List<BeanItem> results;

    public SearchAdapter(List<BeanItem> results){
        this.results = results;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        if(position < getItemCount()){
            BeanShowItem item = (BeanShowItem) results.get(position);

            if(item != null){
                Picasso.with(QualityShowApplication.getContext()).load(item.getShow().getImages().getPoster().getThumb()).into(holder.image);
                holder.name.setText(item.getShow().getTitle());
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
