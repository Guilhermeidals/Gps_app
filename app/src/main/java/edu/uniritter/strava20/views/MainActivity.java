package edu.uniritter.strava20.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import edu.uniritter.strava20.R;
import edu.uniritter.strava20.adapters.LocationsAdapter;
import edu.uniritter.strava20.adapters.LocationsViewModel;
import edu.uniritter.strava20.receiver.Data;
import edu.uniritter.strava20.receiver.GpsBroadcastReceiver;
import edu.uniritter.strava20.receiver.Locations;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    BroadcastReceiver broadcastReceiver;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    private Button DataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataButton = findViewById(R.id.DataButton);
        requestPermissions();

        broadcastReceiver = new GpsBroadcastReceiver();
        IntentFilter startFilter = new IntentFilter("edu.uniritter.strava20.Gps_Start");
        IntentFilter bootFilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
        registerReceiver(broadcastReceiver, startFilter);
        registerReceiver(broadcastReceiver, bootFilter);

        SharedPreferences preferences = getSharedPreferences("USER_LOGGED", MODE_PRIVATE);
        String USER = preferences.getString("USER_LOGGED", null);

        if (USER.equals("admin")) {
            DataButton.setVisibility(View.VISIBLE);
        }else if (USER.equals("operator")){
            DataButton.setVisibility(View.INVISIBLE);
        }

        Intent startIntent = new Intent();
        startIntent.setAction("edu.uniritter.strava20.Gps_Start");
        getApplicationContext().sendBroadcast(startIntent);

        RecyclerView rvLocation =  findViewById(R.id.rvLocations);
        LocationsAdapter locAdapter =  new LocationsAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rvLocation.setLayoutManager(llm);
        rvLocation.setAdapter(locAdapter);


        LocationsViewModel viewmodel = new ViewModelProvider(this).get(LocationsViewModel.class);
        Data.getLocData().observe(this,
                new Observer<List<Location>>() {
                    @Override
                    public void onChanged(List<Location> locations) {
                        locAdapter.refresh();
                    }
                }
        );

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    public void requestPermissions() {
        ActivityResultLauncher<String[]> permissionRequest = registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            Boolean backgroundLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted && backgroundLocationGranted) {
                                Toast.makeText(this, "Localização Tempo todo", Toast.LENGTH_SHORT).show();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                Toast.makeText(this, "Somente durante o uso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Localização não autorizada", Toast.LENGTH_SHORT).show();

                            }
                        });

        permissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void GoToConfig(View view){
        startActivity(new Intent(MainActivity.this, ConfigActivity.class));
    }

    public void GoToData(View view){
        startActivity(new Intent(MainActivity.this, AppDataActivity.class));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setBuildingsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setIndoorEnabled(true);
        googleMap.setMyLocationEnabled(true);
        Data.getLocData().observe(this,
                new Observer<List<Location>>() {
                    @Override
                    public void onChanged(List<Location> locations) {
                        if (locations.size() > 0) {
                            onNewPoint(locations);
                        }
                    }
                });
    }

    private void onNewPoint(List<Location> locs) {
        googleMap.clear();
        LatLng lastPonto = null;
        PolylineOptions plo = new PolylineOptions();
        for (Location loc : locs) {
            LatLng ponto = new LatLng(loc.getLatitude(), loc.getLongitude());
            googleMap.addCircle(new CircleOptions()
                    .center(ponto)
                    .radius(loc.getAccuracy())
                    .strokeColor(Color.RED)
                    .strokeWidth(1));
            int veloc = (int) (loc.getSpeed()*10);
            googleMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dot))
                    .position(ponto));
//                    .title("+/- "+loc.getAccuracy()+"m  "+(veloc/10.0)+"m/s"));
            plo.add(ponto);

            lastPonto = ponto;
        }
        googleMap.addPolyline(plo);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lastPonto));
    }
}
