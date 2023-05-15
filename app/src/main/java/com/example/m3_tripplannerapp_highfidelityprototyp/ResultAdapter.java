package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    List<DataConnection> inputDataConnections;
    Context context;
    List<DataConnection> savedTrips=new ArrayList<>();

    public ResultAdapter(List<DataConnection> inputDataConnections, Context context) {
        this.inputDataConnections = inputDataConnections;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.result_element, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.trip.setText(printTrip(inputDataConnections.get(position)));
        viewHolder.tripDate.setText(printTripDate(inputDataConnections.get(position)));
        viewHolder.tripTime.setText(printTripTime(inputDataConnections.get(position)));
        viewHolder.tripDuration.setText(printTripDuration(inputDataConnections.get(position)));
        viewHolder.tripPriceAndEmissions.setText(printTripPriceAndEmissions(inputDataConnections.get(position)));
        viewHolder.tripStops.setText(printTripStops(inputDataConnections.get(position)));
    }

    @Override
    public int getItemCount() {
        return inputDataConnections.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trip;
        private TextView tripDate;
        private TextView tripTime;
        private TextView tripDuration;
        private TextView tripPriceAndEmissions;
        private TextView tripStops;
        private ImageButton bookmarkButton;


        public ViewHolder(View view) {
            super(view);

            trip = (TextView) view.findViewById(R.id.trip);
            tripDate = (TextView) view.findViewById(R.id.tripDate);
            tripTime = (TextView) view.findViewById(R.id.tripTime);
            tripDuration = (TextView) view.findViewById(R.id.tripDuration);
            tripPriceAndEmissions = (TextView) view.findViewById(R.id.tripPriceAndEmissions);
            tripStops = (TextView) view.findViewById(R.id.tripStops);
            bookmarkButton=(ImageButton) view.findViewById(R.id.bookmarkButton);

            bookmarkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!savedTrips.contains(inputDataConnections.get(getAdapterPosition()))) {
                        savedTrips.add(inputDataConnections.get(getAdapterPosition()));
                        bookmarkButton.setSelected(true);
                        //System.out.println(savedTrips.size());
                    }
                    else{
                        savedTrips.remove(inputDataConnections.get(getAdapterPosition()));
                        bookmarkButton.setSelected(false);
                        //System.out.println(savedTrips.size());
                    }
                }
            });
        }

    }


    private String printTrip(DataConnection connection) {
        return connection.getStartCity() + " -> " + connection.getDestinationCity() + "  (" + connection.getType().toString() + ")";
    }

    private String printTripDate(DataConnection connection) {
        String ret = "";
        if(connection.getStartDate().getDay()<10)
            ret+="0"+connection.getStartDate().getDay()+ ".";
        else
            ret+=connection.getStartDate().getDay()+ ".";

        if(connection.getStartDate().getMonth()<10)
            ret+="0"+connection.getStartDate().getMonth()+ ".";
        else
            ret+=connection.getStartDate().getMonth()+ ".";

        return ret+connection.getStartDate().getYear();
    }

    private String printTripTime(DataConnection connection) {
        String ret = "";
        DataTime startTime = connection.getStartTime();
        if(startTime.getHour()<10)
            ret+="0"+startTime.getHour()+ ":";
        else
            ret+=startTime.getHour()+ ":";
        if(startTime.getMinute()<10)
            ret+="0"+startTime.getMinute()+ ": ";
        else
            ret+=startTime.getMinute()+ ": ";
        ret += connection.getStartLocation() + "\n";

        DataTime returnTime = connection.getReturnTime();
        if(returnTime.getHour()<10)
            ret+="0"+returnTime.getHour()+ ":";
        else
            ret+=returnTime.getHour()+ ":";
        if(returnTime.getMinute()<10)
            ret+="0"+returnTime.getMinute()+ ": ";
        else
            ret+=returnTime.getMinute()+ ": ";
        ret += connection.getDestinationLocation();
        return ret;
    }

    private String printTripDuration(DataConnection connection) {
        return "Duration: " + connection.getDuration().getHour() + "h " + connection.getDuration().getMinute()+"min";
    }

    private String printTripPriceAndEmissions(DataConnection connection) {
        return connection.getPrize() + "€ | ~"+connection.getCO2Bilance()+"kg CO2";
    }

    private String printTripStops(DataConnection connection) {
        return "Stops: "+connection.getSwitchTransfer();
    }
}