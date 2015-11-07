<<<<<<< HEAD:app/src/main/java/videolibrary/street/quality/qualityshow/adapters/EpisodeAdapter.java
package videolibrary.street.quality.qualityshow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeAdapter extends BaseExpandableListAdapter {


    private LayoutInflater inflater;
    private ArrayList<Object> mParent;

    public EpisodeAdapter(Context context, ArrayList<Object> parent) {
        mParent = parent;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return (Episode)mParent.get(i);
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        holder.groupPosition=i;
        if(view==null){
            view = inflater.inflate(R.layout.episode_item,viewGroup,false);
        }
        Episode ep=(Episode)getGroup(i);
        ((TextView) view.findViewById(R.id.title_episode)).setText("E"+String.format("%02d",ep.getNumber())+": "+ep.getTitle());
        view.setTag(holder);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        holder.groupPosition=i;
        if(view==null){
            view = inflater.inflate(R.layout.episode_content_item,viewGroup,false);
        }
        Episode ep=(Episode)getGroup(i);
        ((TextView) view.findViewById(R.id.synopsys_episode)).setText(ep.getOverview());
        view.setTag(holder);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    protected class ViewHolder {
        protected int childPosition;
        protected int groupPosition;
        protected Button button;
    }
}
=======
package videolibrary.street.quality.qualityshow.ui.adapters;

/**
 * Created by Sacael on 06/11/2015.
 */
public class EpisodeAdapter {
}
>>>>>>> b6416dd5cad1c72df45c5de21b219706285e1ea2:app/src/main/java/videolibrary/street/quality/qualityshow/ui/adapters/EpisodeAdapter.java
