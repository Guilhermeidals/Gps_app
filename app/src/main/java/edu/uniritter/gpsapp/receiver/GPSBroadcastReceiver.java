package edu.uniritter.gpsapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GPSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("Gps_Start")){
            double lat = intent.getDoubleExtra("Latitude", 0f);
            double lng = intent.getDoubleExtra("Longitude", 0f);
            Log.d("Location", "lat: "+lat + ", lng: "+lng);
        }
    }
}