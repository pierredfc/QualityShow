package videolibrary.street.quality.qualityshow.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.strongloop.android.loopback.AccessToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Episode;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.UserHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.UserListener;
import videolibrary.street.quality.qualityshow.receivers.NewEpisodeReceiver;
import videolibrary.street.quality.qualityshow.utils.AlarmPreferences;
import videolibrary.street.quality.qualityshow.utils.CalendarUtils;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by Pierre on 11/11/2015.
 */
public class EpisodeNotificationService extends Service implements SerieListener, UserListener{

    private AlarmPreferences alarmPreferences;

    public EpisodeNotificationService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotificationService", "Start");
        UserHelper userHelper = QualityShowApplication.getUserHelper();

        alarmPreferences = new AlarmPreferences();

        if (userHelper.getCurrentUser() != null) {
            userHelper.series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
        } else {
            userHelper.retrieveRegisteredUser(this);
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        Log.d("NotificationService", "Stop");
        if(series != null){
            setSeries(series);
        }

        ArrayList<Integer> prefs = alarmPreferences.getAlarmPreferences();
        stopSelf();
    }


    public void setSeries(ArrayList<Serie> series) {
        SharedPreferences prefs = QualityShowApplication.getContext().getSharedPreferences(getString(R.string.notification_prefs), Context.MODE_PRIVATE);

        for (Serie serie : series) {
            for (Saison saison : serie.getSaisons()) {
                int aired_episodes = saison.getAired_episodes();
                List<Episode> episodes = saison.getEpisodes();
                for (int i = aired_episodes; i < episodes.size(); i++) {
                    String first_aired = episodes.get(i).getFirst_aired();
                    if(!(first_aired == null)){
                        if(CalendarUtils.getDayDiff(first_aired) >= 0){
                            String[] tokens = episodes.get(i).getFirst_aired().split("[-]");
                            int year = Integer.parseInt(tokens[0]);
                            int month = Integer.parseInt(tokens[1]);
                            int day = Integer.parseInt(tokens[2].split("[T]")[0]);
                            int id = episodes.get(i).getIds().get("trakt");
                            if(!alarmPreferences.isInAlarmPreferences(id)){
                                setAlarm(serie.getTitle(), id, year, month, day);
                                alarmPreferences.setAlarmPreferences(id);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setAlarm(String serieName, int id, int year, int month, int day){
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        int hour = 12;
        int minutes = 0;
        month -= 1;

        cal.set(year, month, day, hour, minutes);
        cal.add(Calendar.DATE, 1);
        Intent intent = new Intent(QualityShowApplication.getContext(), NewEpisodeReceiver.class);
        Bundle extras = new Bundle();
        extras.putString(Constants.NEW_EPISODE_RECEIVER, serieName);
        intent.putExtras(extras);

        PendingIntent operation = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), operation);
    }



    @Override
    public void userIsRetrieved(User user) {
        if(user != null){
            QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
        }
    }

    @Override
    public void getAllUsers(ArrayList<User> users) {

    }

    @Override
    public void isLogged(AccessToken accessToken, User user) {

    }

    @Override
    public void userIsUpdated(boolean isUpdated) {

    }

    @Override
    public void userIsDeleted(boolean isDeleted) {

    }

    @Override
    public void userIsCreated(boolean user) {

    }

    @Override
    public void userIsLogout() {

    }

    @Override
    public void userIsFind(User user) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }
}
