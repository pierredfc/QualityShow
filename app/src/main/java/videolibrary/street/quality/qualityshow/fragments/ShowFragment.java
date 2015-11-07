package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.adapters.SeasonAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Ids;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.helpers.FilmHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.SerieHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategoryListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SaisonListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 05/11/2015.
 */
public class ShowFragment extends Fragment implements RequestListener

{
    ListView resultsView;
    View rootView;
    Object show;
    Film film;
    Serie serie;

    public void setShow(Object show) {
        this.show = show;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_show, container, false);
        resultsView = (ListView)rootView.findViewById(R.id.SeasonsView);
        if (show instanceof Serie){
            serie = (Serie)show;
            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);
            requestAsyncTask.execute(Requests.SERIE_SEASONS,(serie.getIds().get("trakt")).toString());
            fillView(serie);
        }
        else if(show instanceof Film){
            film= (Film)show;
            fillView(film);
        }

        return rootView;
    }


    private void fillView(Serie show){

        ((TextView)rootView.findViewById(R.id.synopsis)).setText(show.getOverview());
        ((TextView)rootView.findViewById(R.id.s_status)).setText(show.getStatus());
    }







    private void fillView(Film show){
        ((TextView)rootView.findViewById(R.id.synopsis)).setText(show.getOverview());
        ((TextView)rootView.findViewById(R.id.s_status)).setText(show.getYear().toString());
    }

    @Override
    public void onResponseReceived(List<Object> response) {
        if( response != null && response.size() > 0){
            SeasonAdapter seasonAdapter = new SeasonAdapter((ShowActivity) getActivity(),response);
            resultsView.setAdapter(seasonAdapter);
            justifyListViewHeightBasedOnChildren(resultsView);
            resultsView.setOnItemClickListener((ShowActivity)getActivity());
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
