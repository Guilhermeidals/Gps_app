package edu.uniritter.strava20.services.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        //change all isSended to Sending

        return locations;
    }

    public void UpdateDistance(double distance){

        double Distance = (GetDistance()) + distance;

        ContentValues cv = new ContentValues();
        cv.put("distance", Distance);

        db.update("AppData", cv, "id = ", new String[]{String.valueOf(1)});
    }

    public double GetDistance(){
        Cursor cursor = db.rawQuery("SELECT distance FROM AppData", null);

        return CursorToDouble(cursor);
    }

    public void UpdateTimeMoving(double TimeMoving){

        double timeMoving = (GetTimeMoving()) + TimeMoving;


        ContentValues cv = new ContentValues();
        cv.put("timeMoving", timeMoving);

        db.update("AppData", cv, "id = ", new String[]{String.valueOf(1)});
    }

    public double GetTimeMoving(){
        Cursor cursor = db.rawQuery("SELECT timeMoving FROM AppData", null);

        return CursorToDouble(cursor);
    }

    public void UpdateDowntime (double Downtime){

        double downtime = (GetDowntime()) + Downtime;

        ContentValues cv = new ContentValues();
        cv.put("downtime", downtime);

        db.update("AppData", cv, "id = ", new String[]{String.valueOf(1)});
    }

    public double GetDowntime (){
        Cursor cursor = db.rawQuery("SELECT downtime FROM AppData", null);

        return CursorToDouble(cursor);
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

    public double CursorToDouble(Cursor cursor){
        double valor = 0;

        if (cursor.moveToFirst()){
            do{

                valor = cursor.getDouble(1);

            } while (cursor.moveToNext());
        }

        return valor;
    }
}
