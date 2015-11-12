package videolibrary.street.quality.qualityshow.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import videolibrary.street.quality.qualityshow.ui.adapters.CalendarEpisodeAdapter;
import videolibrary.street.quality.qualityshow.ui.adapters.ShowsAdapter;
import videolibrary.street.quality.qualityshow.ui.holders.CalendarChild;
import videolibrary.street.quality.qualityshow.ui.holders.CalendarParent;


public class HomeFragment extends Fragment implements View.OnClickListener, SerieListener {

    public View rootView;
    private RecyclerView resultsView;

    boolean userConnected;

    private ShowsAdapter showsAdapter;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        resultsView = (RecyclerView) rootView.findViewById(R.id.calendar_episode);
        resultsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        checkUserConnected();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.agenda);

        if (userConnected) {
            user = QualityShowApplication.getUserHelper().getCurrentUser();
            if (user.series != null) {
                showNextAir(user.series);
            } else {
                QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
            }
        }

        return rootView;
    }

    private void checkUserConnected() {
        if (QualityShowApplication.getUserHelper().getCurrentUser() == null) {
            userConnected = false;
        } else {
            userConnected = true;
        }
    }

    private void showNextAir(ArrayList<Serie> series) {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (Serie serie : series) {
            if (serie.getStatus().equals("ended") || serie.getStatus().equals("canceled") || serie.getSaisons() == null)
                continue;
            for (Saison saison : serie.getSaisons()) {
                if (saison.getEpisodes() == null)
                    continue;
                int aired_episodes = saison.getAired_episodes();
                List<Episode> episodes = saison.getEpisodes();
                for (int i = aired_episodes; i < episodes.size(); i++) {
                    if (episodes.get(i).getFirst_aired() == null)
                        continue;
                    int daysLeft = getDaysLeft(episodes.get(i).getFirst_aired());
                    if (daysLeft < 0)
                        continue;
                    Object p = saison.getPoster().get("thumb");
                    String image = (String) p;
                    CalendarChild calendarChild = new CalendarChild(serie.getTitle(), saison.getNumber(), episodes.get(i), daysLeft, image);
                    Log.d("Calendar", serie.getTitle() + " " + episodes.get(i).getTitle() + " " + episodes.get(i).getFirst_aired() + " in " + calendarChild.getDaysLeft() + " days");
                    insertIntoParent(parentObjects, calendarChild);
                }
            }
        }
        if (getActivity() != null) {
            Collections.sort(parentObjects, new CalendarComparator());
            CalendarEpisodeAdapter calendarEpisodeAdapter = new CalendarEpisodeAdapter(getActivity(), parentObjects, this);
            calendarEpisodeAdapter.setParentAndIconExpandOnClick(true);
            resultsView.setAdapter(calendarEpisodeAdapter);
        }
    }

    public static void insertIntoParent(ArrayList<ParentObject> parentObjects, CalendarChild calendarChild) {
        for (ParentObject parentObject : parentObjects) {
            if (((CalendarParent) parentObject).getDaysLeft() == calendarChild.getDaysLeft()) {
                parentObject.getChildObjectList().add(calendarChild);
                return;
            }
        }
        String daysLeftStr;
        switch (calendarChild.getDaysLeft()) {
            case 0:
                daysLeftStr = "Today";
                break;
            case 1:
                daysLeftStr = "Tomorrow";
                break;
            default:
                daysLeftStr = "In " + calendarChild.getDaysLeft() + " days";

        }
        CalendarParent calendarParent = new CalendarParent(new ArrayList<>(), calendarChild.getDaysLeft(), daysLeftStr);
        calendarParent.getChildObjectList().add(calendarChild);
        parentObjects.add(calendarParent);
    }

    public static int getDaysLeft(String strThatDay) {
        strThatDay = strThatDay.replace("Z", "+01:00");
        Calendar thatDay = Calendar.getInstance(TimeZone.getDefault(), Locale.FRANCE);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.FRANCE);
        try {
            Date date = dateformat.parse(strThatDay);
            thatDay.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        showNextAir(series);
    }

    @Override
    public void onError(Throwable t) {

    }

    class CalendarComparator implements Comparator<ParentObject> {
        @Override
        public int compare(ParentObject lhs, ParentObject rhs) {
            CalendarParent cp1 = (CalendarParent) lhs;
            CalendarParent cp2 = (CalendarParent) rhs;
            if (cp1.getDaysLeft().compareTo(cp2.getDaysLeft()) == -1) {
                return -1;
            } else if (cp1.getDaysLeft().compareTo(cp2.getDaysLeft()) == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
