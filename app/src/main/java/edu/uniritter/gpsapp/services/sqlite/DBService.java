package edu.uniritter.gpsapp.services.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBService {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBService(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void createNewLocation(double longitude, double latitude, LocalDateTime DateTime) {
        db.execSQL("INSERT INTO LocationData (longitude, latitude, date, isSended) VALUES (" +
                longitude + "," + latitude + "," + DateTime + "," + "false);");
    }

    public List<Location> getAllLocations(){
        Cursor cursor = db.rawQuery("SELECT * FROM LocationData", null);

        List<Location> locations = CursorToLocations(cursor);

        return locations;
    }

    public List<Location> getAllNotSendedLocations(){
        Cursor cursor = db.rawQuery("SELECT * FROM LocationData WHERE isSended = false", null);

        List<Location> locations = CursorToLocations(cursor);

        return locations;
    }

    public List<Location> CursorToLocations(Cursor cursor){
        List<Location> locations = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                Location loc = new Location("");
                loc.setLongitude(Double.parseDouble(cursor.getString(1)));
                loc.setLatitude(Double.parseDouble(cursor.getString(2)));

                locations.add(loc);

            } while (cursor.moveToNext());
        }

        return locations;
    }
}
