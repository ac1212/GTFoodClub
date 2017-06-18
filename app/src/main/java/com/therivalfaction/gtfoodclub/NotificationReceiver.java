package com.therivalfaction.gtfoodclub;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by User on 04-Apr-17.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AC","reached NotificationReceiver");
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = intent.getParcelableExtra(context.getResources().getString(R.string.intentkey_notification));
        int nid = intent.getIntExtra(context.getResources().getString(R.string.intentkey_notification_id), 0);
        nm.notify(nid,n);
    }
}
