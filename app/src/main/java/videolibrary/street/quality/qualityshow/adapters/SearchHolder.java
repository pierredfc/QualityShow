package videolibrary.street.quality.qualityshow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.R;


public class SearchHolder extends RecyclerView.ViewHolder{

    private View view;
    public ImageView image;
    public TextView name;

    public SearchHolder(View itemView) {
        super(itemView);
        view = itemView;
        image = (ImageView) itemView.findViewById(R.id.search_image_view);
        name = (TextView) itemView.findViewById(R.id.search_name_view);
    }

}
