package videolibrary.street.quality.qualityshow.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.listeners.AlarmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.utils.AlarmPreferences;

/**
 * Created by Pierre on 11/11/2015.
 */
public class EpisodeNotificationService extends Service implements SerieListener {

    private AlarmPreferences alarmPreferences;
    private AlarmListener alarmListener;

    public EpisodeNotificationService(AlarmListener listener){
        alarmListener = listener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotificationService", "Start");

        if (QualityShowApplication.getUserHelper().getCurrentUser() != null) {
            QualityShowApplication.getUserHelper().series(QualityShowApplication.getUserHelper().getCurrentUser(), true, this);
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public void getSeries(ArrayList<Serie> series) {
        Log.d("NotificationService", "Stop");
        if(series != null){
            alarmListener.setSeries(series);
        }
        stopSelf();
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
