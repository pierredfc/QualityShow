package videolibrary.street.quality.qualityshow.adapters;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.api.user.dao.Episode;

/**
 * Created by Sacael on 06/11/2015.
 */
public class ParentEpisode {
    private String mTitle;
    private ArrayList<Episode> mArrayChildren;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<Episode> getArrayChildren() {
        return mArrayChildren;
    }

    public void setArrayChildren(ArrayList<Episode> arrayChildren) {
        mArrayChildren = arrayChildren;
    }
}
