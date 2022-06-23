package edu.uniritter.strava20.services.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import edu.uniritter.strava20.receiver.Locations;

public class FirebaseDB {
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public CollectionReference collectionReference = firebaseFirestore.collection("Locations");
    private SQLiteDatabase db;
    DBService dbService;

    public void SaveData(Location location) {
        GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        collectionReference.document(String.valueOf(Calendar.getInstance().getTime())).set(new Locations(geoPoint)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("firestore", "onSuccess: Enviado");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("firestore", "onFailure: Não Enviado");
            }
        });
    }

    public void updateFirebaseSQL() {
        for (Location location : dbService.getAllNotSendedLocations()) {
            GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            collectionReference.document(String.valueOf(Calendar.getInstance().getTime())).set(new Locations(geoPoint)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("firestore", "onSuccess: Enviado");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("firestore", "onFailure: Não Enviado");
                }
            });
        }
        //Change SQL TO SENDED;
    }
}