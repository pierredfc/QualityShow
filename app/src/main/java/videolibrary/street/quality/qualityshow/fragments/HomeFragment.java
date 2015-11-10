package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vlonjatg.progressactivity.ProgressActivity;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import videolibrary.street.quality.qualityshow.activities.MainActivity;
import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Airs;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SerieListener {

    public ProgressActivity rootView;
    private RecyclerView showsView;

    boolean userConnected;

    private ShowsAdapter showsAdapter;

    private List<Serie> userSerie;

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
        if(userConnected){
            QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
            rootView.showLoading();
        }
    }

    private void checkUserConnected(){
        if(QualityShowApplication.getUserHelper().getCurrentUser() == null){
            userConnected = false;
        } else {
            userConnected = true;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        QualityShowApplication.getUserHelper().getCurrentUser().setSeries(series);
        showsAdapter = new ShowsAdapter(series, null, (MainActivity) getActivity());
        showsView.setAdapter(showsAdapter);
        rootView.showContent();
        getNextAir(series);
    }

    private void getNextAir(ArrayList<Serie> series) {
        for (Serie serie : series) {
//            HashMap<String, String> airs = serie.getAirs();
//            Log.d("Calendar", serie.getTitle() + ": " + airs.get("day") + ", " + airs.get("time") + " - " + airs.get("timezone") + ".");
//
//            // get today and clear time of day
//            Calendar cal = Calendar.getInstance(Locale.FRANCE);
//            cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
//            cal.clear(Calendar.MINUTE);
//            cal.clear(Calendar.SECOND);
//            cal.clear(Calendar.MILLISECOND);
//
//            // get start of this week in milliseconds
//            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
//            long millis = cal.getTimeInMillis();
//            Log.d("Calendar", "Start of this week: " + cal.getTime() + " -> " + cal.getTimeInMillis());
//            long air = millis + getMillisOfDay(airs.get("day")) + getMillisOfTime(airs.get("time"));
//            Log.d("Calendar", "Airs: " + air);
//
//            if (air > System.currentTimeMillis()) {
//                Log.d("Calendar", "Show not yet aired !");
//            }

            for (Saison saison : serie.getSaisons()) {
                int aired_episodes = saison.getAired_episodes();
                List<Episode> episodes = saison.getEpisodes();
                for (int i = aired_episodes; i < episodes.size(); i++) {
                    Log.d("Calendar", serie.getTitle() + " " + episodes.get(i).getTitle() + " " + episodes.get(i).getFirst_aired());
                }
                HashMap<String, String> airs = serie.getAirs();
                Log.d("Calendar", serie.getTitle() + ": " + airs.get("day") + ", " + airs.get("time") + " - " + airs.get("timezone") + ".");

                // get today and clear time of day
                Calendar cal = Calendar.getInstance(Locale.FRANCE);
                cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
                cal.clear(Calendar.MINUTE);
                cal.clear(Calendar.SECOND);
                cal.clear(Calendar.MILLISECOND);

                // get start of this week in milliseconds
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                long millis = cal.getTimeInMillis();
                Log.d("Calendar", "Start of this week: " + cal.getTime() + " -> " + cal.getTimeInMillis());
                long air = millis + getMillisOfDay(airs.get("day")) + getMillisOfTime(airs.get("time"));
                Log.d("Calendar", "Airs: " + air);

                if (air > System.currentTimeMillis()) {
                    Log.d("Calendar", "Show not yet aired !");
                }
            }
        }
    }

    private static long getMillisOfDay(String day){
        final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < days.length; i++) {
            if (days[i].equals(day)) {
                return i * 24 * 60 * 60 * 1000;
            }
        }
        return 0;
    }

    private static long getMillisOfTime(String time) {
        if (time == null)
            return 0;
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * (60 + minutes) * 60 * 1000;
    }

    @Override
    public void onError(Throwable t) {

    }
}
