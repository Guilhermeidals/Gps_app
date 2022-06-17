package edu.uniritter.gpsapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uniritter.gpsapp.R;
import edu.uniritter.gpsapp.adapters.LocationsAdapter;
import edu.uniritter.gpsapp.receiver.Data;
import edu.uniritter.gpsapp.receiver.GPSBroadcastReceiver;
import edu.uniritter.gpsapp.receiver.GPSService;

public class GpsActivity extends AppCompatActivity {
    GPSBroadcastReceiver receiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        receiver = new GPSBroadcastReceiver();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startService();
        }

        RecyclerView rv = findViewById(R.id.RvLocations);
        LocationsAdapter adapter = new LocationsAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


        Data.getLocData().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                adapter.refresh();
            }
        });
    }


    void startService() {
        IntentFilter filter = new IntentFilter("Gps_Start");
        registerReceiver(receiver, filter);
        Intent intent = new Intent(GpsActivity.this, GPSService.class);
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
}
