package edu.uniritter.gpsapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBService {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBService(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void create(String sql) {
        db.execSQL(sql);
    }
}
