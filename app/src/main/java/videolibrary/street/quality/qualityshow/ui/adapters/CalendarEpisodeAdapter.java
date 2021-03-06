package videolibrary.street.quality.qualityshow.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.ui.holders.CalendarChild;
import videolibrary.street.quality.qualityshow.ui.holders.CalendarChildHolder;
import videolibrary.street.quality.qualityshow.ui.holders.CalendarParent;
import videolibrary.street.quality.qualityshow.ui.holders.CalendarParentHolder;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarEpisodeAdapter extends ExpandableRecyclerAdapter<CalendarParentHolder, CalendarChildHolder> {


    private LayoutInflater mInflater;
    private ArrayList<ParentObject> mParent;
    private View.OnClickListener clickListener;

    public CalendarEpisodeAdapter(Context context, ArrayList<ParentObject> parentItemList,View.OnClickListener clistener) {
        super(context, parentItemList);
        clickListener=clistener;
        mParent=parentItemList;
        mInflater=LayoutInflater.from(context);

    }

    @Override
    public CalendarParentHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.calendar_parent_item, viewGroup, false);
        return new CalendarParentHolder(view);
    }

    @Override
    public CalendarChildHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.calendar_content_item, viewGroup, false);
        return new CalendarChildHolder(view);
    }

    @Override
    public void onBindParentViewHolder(CalendarParentHolder calendarParentHolder, int i, Object o) {
        CalendarParent calendarParent = (CalendarParent) o;
        calendarParentHolder.daysLeftString.setText(calendarParent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(final CalendarChildHolder calendarChildHolder, int i, Object o) {
        CalendarChild calendarChild = (CalendarChild) o;
        calendarChildHolder.serieName.setText(calendarChild.getSerieTitle());
        calendarChildHolder.saisonNumber.setText("Season " + calendarChild.getSeasonNumber());
        calendarChildHolder.episodeNumber.setText("episode " + calendarChild.getEpisode().getNumber());
        calendarChildHolder.episodeName.setText(calendarChild.getEpisode().getTitle());

        Object p = calendarChild.getSaison().getPoster().get("thumb");
        String image = (String) p;
        if (image == null) {
            Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
            calendarChildHolder.image.setImageDrawable(drawable);
        } else {
            Picasso.with(QualityShowApplication.getContext()).load(image).into(calendarChildHolder.image);
        }
    }
}

