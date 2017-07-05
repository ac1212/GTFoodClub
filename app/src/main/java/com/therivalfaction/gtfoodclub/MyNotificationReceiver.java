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

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AC","reached MyNotificationReceiver");
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = intent.getParcelableExtra(NOTIFICATION);
        int nid = intent.getIntExtra(NOTIFICATION_ID, 0);
        nm.notify(nid,n);
        Log.d("AC1","Time when notification was fired: "+ (SystemClock.elapsedRealtime()/1000));
    }
}
