package com.therivalfaction.gtfoodclub;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by User on 04-Apr-17.
 */

public class MyNotificationReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    public static String CANCEL_NOTIFICATION = "cancel_notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AC","reached MyNotificationReceiver");

        context.getSharedPreferences()

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int nid = intent.getIntExtra(NOTIFICATION_ID, 0);
        boolean isCancel = intent.getBooleanExtra(CANCEL_NOTIFICATION, false);
        if(isCancel)
        {
            nm.cancel(nid);
            Log.d("AC1","Time when notification was cancelled: "+ (SystemClock.elapsedRealtime()/1000));
        }
        else
        {
                Notification n = intent.getParcelableExtra(NOTIFICATION);
                nm.notify(nid, n);
                Log.d("AC1", "Time when notification was fired: " + (SystemClock.elapsedRealtime() / 1000));
        }

    }
}
