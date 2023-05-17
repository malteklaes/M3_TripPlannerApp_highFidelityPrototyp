package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder>{
    private List<DataConnection> stops;  //all stops of the trip
    Context context;

    public StopAdapter(List<DataConnection> stops, Context context) {
        this.stops = stops;
        this.context = context;
    }

    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stop_element, viewGroup, false);  // elements of recyclerView are in style of stop_element

        return new StopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StopAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.stopInformation.setText(printStopInformation(stops.get(position)));
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stopInformation;

        public ViewHolder(View view) {
            super(view);
            stopInformation = (TextView) view.findViewById(R.id.stopInformation);
        }
    }

    private String printStopInformation(DataConnection connection){
        String ret="";
        ret+=connection.getStartCity()+", "+connection.getDestinationCity()+": ";
        ret+=printStopTime(connection);
        ret+=" ("+connection.getType().toString()+")";
        return ret;
    }

    private String printStopTime(DataConnection connection){
        String ret="";
        DataTime startTime = connection.getStartTime();
        if(startTime.getHour()<10)
            ret+="0"+startTime.getHour()+ ":";
        else
            ret+=startTime.getHour()+ ":";
        if(startTime.getMinute()<10)
            ret+="0"+startTime.getMinute()+ "-";
        else
            ret+=startTime.getMinute()+ "-";

        DataTime returnTime = connection.getReturnTime();
        if(returnTime.getHour()<10)
            ret+="0"+returnTime.getHour()+ ":";
        else
            ret+=returnTime.getHour()+ ":";
        if(returnTime.getMinute()<10)
            ret+="0"+returnTime.getMinute();
        else
            ret+=returnTime.getMinute();
        return ret;
    }
}
