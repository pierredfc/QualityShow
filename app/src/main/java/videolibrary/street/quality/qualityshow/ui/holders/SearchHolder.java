package videolibrary.street.quality.qualityshow.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;


public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View view;
    public ImageView image;
    public TextView name;
    public TextView year;

    private ClickListener clickListener;
    private Object item;

    public SearchHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        this.image = (ImageView) itemView.findViewById(R.id.search_image_view);
        this.name = (TextView) itemView.findViewById(R.id.search_name_view);
        this.year = (TextView) itemView.findViewById(R.id.search_year_view);
    }

    public void setView(Object item, ClickListener listener){
        this.clickListener = listener;
        this.item = item;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.clickListener.onItemClick(item);
    }
}
