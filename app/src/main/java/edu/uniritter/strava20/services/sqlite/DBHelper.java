package edu.uniritter.strava20.services.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String BD_NAME = "BDTelemetria";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, BD_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE LocationData(id INTEGER PRIMARY KEY, longitude REAL, latitude REAL, date DATETIME, isSended BOOL);";
        db.execSQL(sql);
        String sql2 = "CREATE TABLE AppData(id INTEGER PRIMARY KEY, distance REAL, timeMoving REAL, downtime REAL);";
        db.execSQL(sql2);

        db.execSQL("INSERT INTO AppData (distance, timeMoving, downtime) VALUES (0, 0, 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS LocationData;");
        onCreate(db);
    }
}