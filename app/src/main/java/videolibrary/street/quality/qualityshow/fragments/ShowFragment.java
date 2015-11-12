package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.SearchActivity;
import videolibrary.street.quality.qualityshow.activities.ShowActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Airs;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.ui.adapters.RelatedAdapter;
import videolibrary.street.quality.qualityshow.ui.adapters.SearchAdapter;
import videolibrary.street.quality.qualityshow.ui.adapters.SeasonAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.async.RequestAsyncTask;
import videolibrary.street.quality.qualityshow.listeners.RequestListener;
import videolibrary.street.quality.qualityshow.utils.Requests;

/**
 * Created by Sacael on 05/11/2015.
 */
public class ShowFragment extends Fragment implements RequestListener,View.OnClickListener {

    ListView resultsView;
    RecyclerView relatedView;
    View rootView;
    Object show;
    String videoId;
    ArrayList<Saison> seasons;
    public void setShow(Object show) {
        this.show = show;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_show, container, false);
        resultsView = (ListView)rootView.findViewById(R.id.SeasonsView);
        relatedView = (RecyclerView)rootView.findViewById(R.id.related_shows);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(QualityShowApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        relatedView.setLayoutManager(linearLayoutManager);
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this);

        if (show instanceof Serie){
            Serie serie = (Serie) show;
            Collections.sort(((Serie) show).getSaisons(),new ComparateurSaison());
            fillView(serie);
            requestAsyncTask.execute(Requests.SERIES_RELATED,String.valueOf(((Serie) show).getIds().get("slug")));

        } else if(show instanceof Film){
            Film film = (Film)show;
            fillView(film);
            requestAsyncTask.execute(Requests.MOVIES_RELATED, String.valueOf(((Film) show).getIds().get("slug")));
        }


        try
        {   if(show instanceof Serie){
            videoId=extractYoutubeId(((Serie) show).getTrailer());
            }
            else{
            videoId=extractYoutubeId(((Film) show).getTrailer());
        }


            String img_url="http://img.youtube.com/vi/"+videoId+"/0.jpg"; // this is link which will give u thumnail image of that video

            // picasso jar file download image for u and set image in imagview

            Picasso.with(QualityShowApplication.getContext())
                    .load(img_url)
                    .placeholder(R.drawable.ic_action_add)
                    .into((ImageView) rootView.findViewById(R.id.img_thumnail));
            ((RelativeLayout)rootView.findViewById(R.id.webvideo_layout2)).setOnClickListener(this);

        }
        catch (MalformedURLException e)
        {
                rootView.findViewById(R.id.webvideo_layout2).setVisibility(View.GONE);
                rootView.findViewById(R.id.trailer).setVisibility(View.GONE);
        }

        return rootView;
    }


    private void fillView(Serie show){
        ((TextView)rootView.findViewById(R.id.synopsis)).setText(show.getOverview());
        String genres = "";

        if(show.getGenres() != null && show.getCategories() == null){
            ArrayList<Category> categories = show.getGenres();
            for(int i = 0; i < categories.size(); i++){
                if(i == categories.size() -1){
                    genres += categories.get(i) + ".";
                } else {
                    genres += categories.get(i) + ", ";
                }
            }
            ((TextView)rootView.findViewById(R.id.s_genres)).setText(genres);
        }else if(show.getCategories() != null){
            ArrayList<Category> categories = show.getGenres();
            for(int i = 0; i < categories.size(); i++){
                if(i == categories.size() -1){
                    String test = "";
                    test += categories.get(i);
                    try {
                        JSONObject object = new JSONObject(test);
                        genres += object.getString("name") + ". ";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String test = "";
                    test += categories.get(i);
                    try {
                        JSONObject object = new JSONObject(test);
                        genres += object.getString("name") + ", ";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
            ((TextView)rootView.findViewById(R.id.s_genres)).setText(genres);
        }


        HashMap<String, String> airsMap = show.getAirs();
        String aired = airsMap.get("day") + ", " + airsMap.get("time") + " - " + airsMap.get("timezone") + ".";

        ((TextView) rootView.findViewById(R.id.s_aired)).setText(aired);
        SeasonAdapter seasonAdapter = new SeasonAdapter((ShowActivity) getActivity(),show.getSaisons());
        resultsView.setAdapter(seasonAdapter);
        justifyListViewHeightBasedOnChildren(resultsView);
        resultsView.setOnItemClickListener((ShowActivity) getActivity());
    }


    private void fillView(Film show){
        ((TextView)rootView.findViewById(R.id.synopsis)).setText(show.getOverview());
        String genres = "";

        ArrayList<Category> categories = show.getGenres();
        if (categories != null && show.getCategories() == null) {
            for(int i = 0; i < categories.size(); i++){
                if(i == categories.size() -1){
                    genres += categories.get(i) + ".";
                } else {
                    genres += categories.get(i) + ", ";
                }
            }
            ((TextView)rootView.findViewById(R.id.s_genres)).setText(genres);
        }else if(show.getCategories() != null) {
            categories = show.getGenres();
            for (int i = 0; i < categories.size(); i++) {
                if (i == categories.size() - 1) {
                    String test = "";
                    test += categories.get(i);
                    try {
                        JSONObject object = new JSONObject(test);
                        genres += object.getString("name") + ". ";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String test = "";
                    test += categories.get(i);
                    try {
                        JSONObject object = new JSONObject(test);
                        genres += object.getString("name") + ", ";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
            ((TextView) rootView.findViewById(R.id.s_genres)).setText(genres);
        }
        ((TextView) rootView.findViewById(R.id.s_aired)).setText(String.valueOf(show.getYear()));
        ((TextView) rootView.findViewById(R.id.title_seasons)).setVisibility(View.GONE);
    }


    @Override
    public void onResponseReceived(List<Object> response) {
        if( response != null && response.size() > 0){
            RelatedAdapter relatedAdapter = new RelatedAdapter(response, (ShowActivity) getActivity());
            relatedView.setAdapter(relatedAdapter);
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
    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(QualityShowApplication.getContext(), "Install the youtube app to see the trailer.", Toast.LENGTH_LONG).show();

        }
    }
}
class ComparateurSaison implements Comparator<Saison> {

    @Override
    public int compare(Saison saison, Saison t1) {
        if (saison.getNumber().compareTo(t1.getNumber()) == -1) {
            return -1;
        } else if (saison.getNumber().compareTo(t1.getNumber()) == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}