package edu.uniritter.strava20.receiver;

import android.icu.util.Calendar;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class Locations {
    public GeoPoint location;
    public Date date;

    public Locations(GeoPoint location){
        this.location = location;
        this.date = Calendar.getInstance().getTime();

    }
}
