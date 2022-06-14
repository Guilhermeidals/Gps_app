package edu.uniritter.gpsapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GPSActivity extends AppCompatActivity {


    LocationBroadcastReceiver receiver;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new LocationBroadcastReceiver();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startService();
            }
        } else {
            startService();
        }
    }


    void startService() {
        IntentFilter filter = new IntentFilter("Gps_Start");
        registerReceiver(receiver, filter);
        Intent intent = new Intent(GPSActivity.this, LocationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService();
                } else {
                    Toast.makeText(this, "Permissions necessary to continue", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public class LocationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Gps_Start")){
                double lat = intent.getDoubleExtra("Latitude", 0f);
                double lng = intent.getDoubleExtra("Longitude", 0f);
                Toast.makeText(GPSActivity.this, "Latitude is: " + lat + ", Longitude is: " + lng, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
