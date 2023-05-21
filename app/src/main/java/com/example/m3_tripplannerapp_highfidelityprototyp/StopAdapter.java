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

    /**
     * Constructor for creating ResultAdapter object
     *
     * @param stops
     * @param context
     */
    public StopAdapter(List<DataConnection> stops, Context context) {
        this.stops = stops;
        this.context = context;
    }

    /**
     * sets up the inflator for the view
     *
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @Override
    public StopAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stop_element, viewGroup, false);  // elements of recyclerView are in style of stop_element

        return new StopAdapter.ViewHolder(view);
    }

    /**
     * sets information for stop element
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(StopAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.stopInformation.setText(printStopInformation(stops.get(position)));
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    /**
     * inner class Viewholder for StopAdapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stopInformation;

        public ViewHolder(View view) {
            super(view);
            stopInformation = (TextView) view.findViewById(R.id.stopInformation);
        }
    }

    /**
     * Method for getting String of from-to information of stopElement (place)
     *
     * @param connection
     * @return
     */
    private String printStopInformation(DataConnection connection){
        String ret="";
        ret+=connection.getStartCity()+", "+connection.getDestinationCity()+": ";
        ret+=printStopTime(connection);
        ret+=" ("+connection.getType().toString()+")";
        return ret;
    }

    /**
     * Method for getting String of from-to information of resultElement (time)
     *
     * @param connection
     * @return
     */
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
