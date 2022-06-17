package edu.uniritter.gpsapp.receiver;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class GPSService extends LifecycleService {
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest = new LocationRequest();
    Location lastLoc = null;

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
                //Log.d("Teste", "Lat: "+ locationResult.getLastLocation().getLatitude() +
                    //    ", Long: " + locationResult.getLastLocation().getLongitude());
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


//        Task<Void> voidTask = fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback)

    }


    private void requestLocation() {

        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }
}
