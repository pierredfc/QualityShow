package videolibrary.street.quality.qualityshow.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;


public class ShowsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View view;
    public ImageView imageView;

    private ClickListener clickListener;
    private Object show;

    public ShowsHolder(View itemView){
        super(itemView);
        view = itemView;
        imageView = (ImageView) itemView.findViewById(R.id.p_image_serie);
    }

    public void setView(Object show, ClickListener listener){
        clickListener = listener;
        this.show = show;
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickListener.onItemClick(show);
    }
}
