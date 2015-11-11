package videolibrary.street.quality.qualityshow.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.activities.StartActivity;

/**
 * Created by Pierre on 11/11/2015.
 */
public class NotificationHelper {

    public static void displayNotification(String serie, boolean vibrate, boolean sound) {
        final Context context = QualityShowApplication.getContext();

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("The new episode of " + serie + " is out")
                .setAutoCancel(true);

        final Intent newIntent = new Intent(context, StartActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(StartActivity.class);
        stackBuilder.addNextIntent(newIntent);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
        builder.setContentIntent(resultPendingIntent);

        final Notification notification = builder.build();
        if(vibrate){
            notification.defaults = Notification.DEFAULT_VIBRATE;
        }

        if(sound){
            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(42, notification);
    }
}
