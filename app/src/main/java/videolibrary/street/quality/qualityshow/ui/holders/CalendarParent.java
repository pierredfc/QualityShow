package videolibrary.street.quality.qualityshow.ui.holders;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by Baptiste on 11/12/2015.
 */
public class CalendarParent implements ParentObject {
    private List<Object> mChildrenList;
    private Integer daysLeft;
    private String title;

    public CalendarParent(List<Object> mChildrenList, Integer daysLeft, String title) {
        this.mChildrenList = mChildrenList;
        this.daysLeft = daysLeft;
        this.title = title;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
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