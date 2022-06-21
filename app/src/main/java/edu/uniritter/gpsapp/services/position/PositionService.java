package edu.uniritter.gpsapp.services.position;

import android.content.Context;
import android.location.Location;

import java.time.LocalDateTime;

import edu.uniritter.gpsapp.services.sqlite.DBService;

public class PositionService {
    private DBService db;

    public void saveData(Location location, String dbType, Context context){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        if (dbType.equals("Firebase")){
            //Send Location to Firebase
        }
        else if (dbType.equals("SQLite")){
            db = new DBService(context);
            db.createNewLocation(longitude, latitude, LocalDateTime.now());
        }
    }
}
