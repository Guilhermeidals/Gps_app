package edu.uniritter.gpsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service {
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;


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
                Log.d("Teste", "Lat: "+ locationResult.getLastLocation().getLatitude() +
                        ", Long: " + locationResult.getLastLocation().getLongitude());
                Intent intent = new Intent("Gps_Start");
                intent.putExtra("Latitude", locationResult.getLastLocation().getLatitude());
                intent.putExtra("Longitude", locationResult.getLastLocation().getLongitude());
                sendBroadcast(intent);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }
}
