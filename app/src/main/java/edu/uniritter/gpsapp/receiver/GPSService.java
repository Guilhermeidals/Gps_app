package edu.uniritter.gpsapp.receiver;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GPSService extends LifecycleService {
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest = new LocationRequest();
    Location lastLoc = null;
    private int updationTick = 30000;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Intent intent = new Intent("Gps_Start");
                intent.putExtra("Latitude", locationResult.getLastLocation().getLatitude());
                intent.putExtra("Longitude", locationResult.getLastLocation().getLongitude());
                sendBroadcast(intent);

                float distance = 0;
                Location loc = locationResult.getLastLocation();
                if(lastLoc != null) {
                    distance = loc.distanceTo(lastLoc);
                }else {
                    lastLoc = loc;
                }
                Data.saveData(loc,distance);
                Log.d("SaveData", "onLocationResult: teste");

                if (distance > loc.getAccuracy()) {
                    lastLoc = loc;
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }


    private void requestLocation() {

        locationRequest.setInterval(updationTick);
        locationRequest.setFastestInterval(updationTick);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    public int getUpdationTick() {
        return updationTick;
    }

    public void setUpdationTick(int tick){
        this.updationTick= tick;
    }
}
