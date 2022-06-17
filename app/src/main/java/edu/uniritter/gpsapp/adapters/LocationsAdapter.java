package edu.uniritter.gpsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.uniritter.gpsapp.R;
import edu.uniritter.gpsapp.receiver.Data;

public class LocationsAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.TVLocation))
            .setText("Location Latitude: "+ (Data.getLocData().getValue().get(position).getLatitude()
                    + ". Longitude: " + (Data.getLocData().getValue().get(position).getLongitude())));
    }

    @Override
    public int getItemCount() {
        return Data.getLocData().getValue().size();
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
