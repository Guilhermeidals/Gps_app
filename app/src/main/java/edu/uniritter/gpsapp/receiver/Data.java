package edu.uniritter.gpsapp.receiver;

import android.icu.util.Calendar;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.model.ResourcePath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {
    private static MutableLiveData<List<Location>> locData = new MutableLiveData<>();

    public static MutableLiveData<List<Location>> getLocData() {
        if (locData.getValue() == null){
            locData.setValue(new ArrayList<>());
        }
        return locData;
    }

    public static void saveData(Location location, float distance) {
        int dist = (int) distance;
        Data.locData.getValue().add(location);
        locData.setValue(locData.getValue());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("Locations");
        GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        collectionReference.document(String.valueOf(Calendar.getInstance().getTime())).set(new Locations(geoPoint)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("firestore", "onSuccess: Foi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("firestore", "onSuccess: Não Foi");
            }
        });
    }
}

class Locations {
    public GeoPoint location;
    public Date date;

    public Locations(GeoPoint location){
        this.location = location;
        this.date = Calendar.getInstance().getTime();

    }
}
