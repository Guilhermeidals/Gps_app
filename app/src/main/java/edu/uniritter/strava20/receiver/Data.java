package edu.uniritter.strava20.receiver;

import android.icu.util.Calendar;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uniritter.strava20.services.position.PositionService;

public class Data {
    public static int distance = 0;
    private static MutableLiveData<List<Location>> locData = new MutableLiveData<>();

    public static MutableLiveData<List<Location>> getLocData() {
        if (locData.getValue() == null){
            locData.setValue(new ArrayList<>());
        }
        return locData;
    }

    public static void saveData(Location location, float dist) {
        distance += (int) dist;
        Data.locData.getValue().add(location);
        locData.setValue(locData.getValue());
    }
}


