package edu.uniritter.strava20.services.position;

import android.content.Context;
import android.location.Location;
import java.time.LocalDateTime;

import edu.uniritter.strava20.services.sqlite.DBService;
import edu.uniritter.strava20.services.sqlite.FirebaseDB;

public class PositionService {
    private DBService db;

    public void saveData(Location location, String dbType, Context context){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        if (dbType.equals("Firebase")){
            FirebaseDB fb = new FirebaseDB();
            fb.SaveData(location);
        }

        else if (dbType.equals("SQLite")) {
            db = new DBService(context);
            db.createNewLocation(longitude, latitude, LocalDateTime.now());
        }
    }
}
