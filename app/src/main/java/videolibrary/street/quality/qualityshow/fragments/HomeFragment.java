package videolibrary.street.quality.qualityshow.fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vlonjatg.progressactivity.ProgressActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;


public class HomeFragment extends Fragment implements SerieListener {

    public ProgressActivity rootView;
    private RecyclerView showsView;

    boolean userConnected;

    private ShowsAdapter showsAdapter;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ProgressActivity) inflater.inflate(R.layout.fragment_home, container, false);
        showsView = (RecyclerView) rootView.findViewById(R.id.show_listView);
        this.showsView.setHasFixedSize(true);
        this.showsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        checkUserConnected();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.agenda);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (userConnected) {
            user = QualityShowApplication.getUserHelper().getCurrentUser();
            if(user.series != null){
                this.showSeries(user.getSeries());
            }else{
                QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
                rootView.showLoading();
            }
        }
    }

    private void checkUserConnected() {
        if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
            userConnected = false;
        } else {
            userConnected = true;
        }
    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        //QualityShowApplication.getUserHelper().getCurrentUser().setSeries(series);
        user.setSeries(series);
        this.showSeries(user.getSeries());
    }

    public void showSeries(ArrayList<Serie> series){
        showsAdapter = new ShowsAdapter(series, null, (MainActivity) getActivity());
        showsView.setAdapter(showsAdapter);
        rootView.showContent();
        getNextAir(series);
    }

    private void getNextAir(ArrayList<Serie> series) {
        for (Serie serie : series) {
            if(serie.getSaisons() != null){
                for (Saison saison : serie.getSaisons()) {
                    if(saison.getEpisodes() != null){
                        int aired_episodes = saison.getAired_episodes();
                        List<Episode> episodes = saison.getEpisodes();
                        for (int i = aired_episodes; i < episodes.size(); i++) {
                            if (episodes.get(i).getFirst_aired() != null) {
                                Log.d("Calendar", serie.getTitle() + " " + episodes.get(i).getTitle() + " " + episodes.get(i).getFirst_aired() + " in " + getDayDiff(episodes.get(i).getFirst_aired()) + " days");
                            }
                        }
                    }
                }
            }
        }
    }

    public static int getDayDiff(String strThatDay) {
        strThatDay = strThatDay.replace("Z", "+01:00");
        Calendar thatDay = Calendar.getInstance(TimeZone.getDefault(), Locale.FRANCE);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.FRANCE);
        try {
            Date date = dateformat.parse(strThatDay);
            thatDay.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.d("DayDiff", thatDay.toString());

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void onError(Throwable t) {

    }
}
