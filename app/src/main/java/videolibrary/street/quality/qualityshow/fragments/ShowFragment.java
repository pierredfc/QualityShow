package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Airs;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.ui.adapters.SeasonAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 05/11/2015.
 */
public class ShowFragment extends Fragment implements RequestListener {

    ListView resultsView;
    View rootView;
    Object show;

    public void setShow(Object show) {
        this.show = show;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_show, container, false);
        resultsView = (ListView)rootView.findViewById(R.id.SeasonsView);

        if (show instanceof Serie){
            Serie serie = (Serie) show;
            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.SERIE_SEASONS, (serie.getIds().get("trakt")).toString());
            fillView(serie);

        } else if(show instanceof Film){
            Film film = (Film)show;
            fillView(film);
        }

        return rootView;
    }


    private void fillView(Serie show){
        ((TextView)rootView.findViewById(R.id.synopsis)).setText(show.getOverview());
        String genres = "";

        if(show.getGenres() != null){
            ArrayList<Category> categories = show.getGenres();
            for(int i = 0; i < categories.size(); i++){
                if(i == categories.size() -1){
                    genres += categories.get(i) + ".";
                } else {
                    genres += categories.get(i) + ", ";
                }
            }
            ((TextView)rootView.findViewById(R.id.s_genres)).setText(genres);
        }

        String aired = "";

        HashMap<String, Airs> aired_map =  show.getAirs();

        Iterator it = aired_map.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            aired += pair.getValue();
            switch(pair.getKey().toString()){
                case "day":
                    aired += ", ";
                    break;
                case "time":
                    aired += " - ";
                    break;
                case "timezone":
                    aired += ".";
                    break;
                default:
                    break;
            }
        }

        ((TextView) rootView.findViewById(R.id.s_aired)).setText(aired);

    }


    private void fillView(Film show){
        ((TextView)rootView.findViewById(R.id.synopsis)).setText(show.getOverview());
        String genres = "";

        ArrayList<Category> categories = show.getGenres();
        if (categories != null) {
            for(int i = 0; i < categories.size(); i++){
                if(i == categories.size() -1){
                    genres += categories.get(i) + ".";
                } else {
                    genres += categories.get(i) + ", ";
                }
            }
            ((TextView)rootView.findViewById(R.id.s_genres)).setText(genres);
        }

        ((TextView) rootView.findViewById(R.id.s_aired)).setText(String.valueOf(show.getYear()));
        ((TextView) rootView.findViewById(R.id.title_seasons)).setVisibility(View.GONE);
    }


    @Override
    public void onResponseReceived(List<Object> response) {
        if( response != null && response.size() > 0){
            SeasonAdapter seasonAdapter = new SeasonAdapter((ShowActivity) getActivity(),response);
            resultsView.setAdapter(seasonAdapter);
            justifyListViewHeightBasedOnChildren(resultsView);
            resultsView.setOnItemClickListener((ShowActivity) getActivity());
        }

    }

    public void justifyListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
