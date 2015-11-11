package videolibrary.street.quality.qualityshow.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.helpers.NotificationHelper;
import videolibrary.street.quality.qualityshow.utils.Constants;

/**
 * Created by Pierre on 11/11/2015.
 */
public class NewEpisodeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String serie = intent.getExtras().getString(Constants.NEW_EPISODE_RECEIVER);
        Log.d("onReceive, serie", serie);

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.notification_prefs), Context.MODE_PRIVATE);
        boolean sound = prefs.getBoolean("sound", false);
        boolean vibrate = prefs.getBoolean("vibrate", false);

        NotificationHelper.displayNotification(serie, vibrate, sound);
    }
}
