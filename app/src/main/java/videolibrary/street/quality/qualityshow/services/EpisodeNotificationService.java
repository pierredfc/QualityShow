package videolibrary.street.quality.qualityshow.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Pierre on 11/11/2015.
 */
public class EpisodeNotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Start");


        return Service.START_NOT_STICKY;
    }
}
