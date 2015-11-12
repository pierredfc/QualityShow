package videolibrary.street.quality.qualityshow.ui.holders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import videolibrary.street.quality.qualityshow.R;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarParentHolder extends ParentViewHolder {
    public TextView daysLeftString;

    public CalendarParentHolder(View itemView) {
        super(itemView);
        this.daysLeftString = (TextView) itemView.findViewById(R.id.days_left_string);
    }
}
