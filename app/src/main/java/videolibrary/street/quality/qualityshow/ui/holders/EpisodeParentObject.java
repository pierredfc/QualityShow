package videolibrary.street.quality.qualityshow.ui.holders;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Sacael on 07/11/2015.
 */
public class EpisodeParentObject implements ParentObject {
    private List<Object> mChildrenList;
    private String title;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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
