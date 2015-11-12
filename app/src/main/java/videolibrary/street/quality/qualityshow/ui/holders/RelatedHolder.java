package videolibrary.street.quality.qualityshow.ui.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;

/**
 * Created by Sacael on 12/11/2015.
 */
public class RelatedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View view;
    public ImageView image;
    public TextView name;

    private ClickListener clickListener;
    private Object item;

    public RelatedHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        this.image = (ImageView) itemView.findViewById(R.id.related_image_view);
        this.name = (TextView) itemView.findViewById(R.id.related_name_view);
    }

    public void setView(Object item, ClickListener listener) {
        this.clickListener = listener;
        this.item = item;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.clickListener.onItemClick(item);
    }
    }