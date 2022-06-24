package edu.uniritter.strava20.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import edu.uniritter.strava20.R;
import edu.uniritter.strava20.adapters.LocationsAdapter;
import edu.uniritter.strava20.adapters.LocationsViewModel;
import edu.uniritter.strava20.receiver.Data;
import edu.uniritter.strava20.receiver.GpsBroadcasReceiver;
import edu.uniritter.strava20.receiver.GpsService;
import edu.uniritter.strava20.services.config.ConfigService;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;

    private Button DataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataButton = findViewById(R.id.DataButton);
        requestPermissions();

        broadcastReceiver = new GpsBroadcasReceiver();
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
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Botao não faz nada ainda", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ConfigActivity.class));
//                Intent startIntent = new Intent();
//                startIntent.setAction("edu.uniritter.strava20.Gps_Start");
//                getApplicationContext().sendBroadcast(startIntent);
            }
        });

        Intent startIntent = new Intent();
        startIntent.setAction("edu.uniritter.strava20.Gps_Start");
        getApplicationContext().sendBroadcast(startIntent);

        RecyclerView rvLocation =  findViewById(R.id.rvLocations);
        LocationsAdapter locAdapter =  new LocationsAdapter();
        rvLocation.setLayoutManager( new LinearLayoutManager(this));
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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
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
}