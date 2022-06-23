package edu.uniritter.strava20.adapters;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocationsViewModel extends ViewModel {
    public MutableLiveData<List<Location>> locations;

    public LocationsViewModel() {
        locations = new MutableLiveData<>();
        locations.setValue(new ArrayList<>());
    }

    public void addLocations(Location loc){
        List<Location> list = new ArrayList<>(locations.getValue());
        list.add(loc);
        locations.setValue(list);
    }
}
