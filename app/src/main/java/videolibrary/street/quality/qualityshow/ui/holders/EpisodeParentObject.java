package videolibrary.street.quality.qualityshow.ui.holders;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by Sacael on 07/11/2015.
 */
public class EpisodeParentObject implements ParentObject {
    private List<Object> mChildrenList;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList=list;
    }
}
