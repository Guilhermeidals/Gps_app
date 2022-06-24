package edu.uniritter.strava20.receiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import edu.uniritter.strava20.services.config.ConfigService;
import edu.uniritter.strava20.services.position.PositionService;
import edu.uniritter.strava20.services.sqlite.DBService;
import edu.uniritter.strava20.views.MainActivity;

public class GpsService extends LifecycleService {

    private LocationManager locationManager;
    private static FusedLocationProviderClient locationProviderClient;
    private static LocationCallback locCallback;
    private static Location lastLocation = null;
    private NotificationChannel notificationChannel;
    static LocationRequest locationRequest;

    @SuppressLint("MissingSuperCall")
    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (ActivityCompat.checkSelfPermission(this.getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this.getBaseContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
        }

        notificationChannel = new NotificationChannel(
                "Gps",
                "Channel do Gps",
                NotificationManager.IMPORTANCE_LOW
        );
        notificationChannel.setDescription("Este é o canal de notificação");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);

        Notification notification =
                new Notification.Builder(this, "Gps")
                        .setContentTitle("Teste1")
                        .setContentText("Teste123")
                        .build();

        startForeground(1337, notification);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        newLocationRequest(ConfigService.GetInterval(this)*1000);
        if (intent == null){
            return START_NOT_STICKY;
        }
        return START_NOT_STICKY;
    }

    public static void newLocationRequest(int interval) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(interval)
                .setInterval(interval)
                .setSmallestDisplacement(0);

        requestLocations(locationRequest);
    }

    private static void requestLocations(LocationRequest locationRequest) {
        @SuppressLint("MissingPermission")
        Task<Void> voidTask = locationProviderClient.requestLocationUpdates(locationRequest, locCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                float distance = 0;
                Location location = locationResult.getLastLocation();
                Log.d("tag", "onLocationResult: loc"+location);
                teste(distance, location);
            }
        }, null);
    }

    private static void teste(float distance, Location location) {
        if(lastLocation != null) {
            distance = location.distanceTo(lastLocation);
        }else{
            lastLocation = location;
            Data.saveData(location, distance);
        }
        if (ConfigService.GetSaveStop(locationProviderClient.getApplicationContext()) == true){
                lastLocation = location;
                Data.saveData(location, distance);
        }else if (ConfigService.GetSaveStop(locationProviderClient.getApplicationContext()) == false){
            if (distance > location.getAccuracy()) {
                lastLocation = location;
                Data.saveData(location, distance);
            }
        }
    }

    public static void stopLocationRequest() {
        locationProviderClient.removeLocationUpdates(locCallback);
    }
};

