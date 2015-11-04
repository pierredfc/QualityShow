package videolibrary.street.quality.qualityshow.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;


public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View view;
    public ImageView image;
    public TextView name;
    public TextView year;

    private ClickListener clickListener;
    private Serie serie;

    public SearchHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        this.image = (ImageView) itemView.findViewById(R.id.search_image_view);
        this.name = (TextView) itemView.findViewById(R.id.search_name_view);
        this.year = (TextView) itemView.findViewById(R.id.search_year_view);
    }

    public void setView(Serie serie, ClickListener listener){
        this.clickListener = listener;
        this.serie = serie;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.clickListener.onSerieClick(serie);
    }
}
