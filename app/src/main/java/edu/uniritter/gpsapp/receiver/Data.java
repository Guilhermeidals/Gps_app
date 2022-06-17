package edu.uniritter.gpsapp.receiver;

import android.icu.util.Calendar;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

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
    }
}
