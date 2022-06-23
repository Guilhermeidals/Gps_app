package edu.uniritter.strava20.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GpsBroadcasReceiver extends BroadcastReceiver {
    private GpsService gpsService;
    private NotificationChannel gpsChannel;

    public GpsBroadcasReceiver() {
        gpsChannel = new NotificationChannel("Gps","Canal do Gps", NotificationManager.IMPORTANCE_LOW);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent posBoot = new Intent();
        Intent boot = new Intent();
        posBoot.setAction("edu.uniritter.strava20.Gps_Start");

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(gpsChannel);
            Intent gpsService = new Intent(context, GpsService.class);

            PendingIntent pendingIntent = PendingIntent.getForegroundService(context, 0,intent, PendingIntent.FLAG_IMMUTABLE);

            Notification notification = new Notification.Builder(context, "Gps")
                    .setContentTitle("Strava 2.0")
                    .setContentText("Clica aqui para abrir o app")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                    .build();

            notificationManager.notify(1337, notification);
        }
        if (intent.getAction().equals("edu.uniritter.strava20.Gps_Start")){
            Intent bootIntent = new Intent(context, GpsService.class);
            intent.putExtra("Boot", false);
            context.startForegroundService(bootIntent);
        }
    }
}
