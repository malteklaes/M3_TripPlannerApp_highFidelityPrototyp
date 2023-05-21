package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    List<DataConnection> inputDataConnections;
    Context context;
    List<DataConnection> savedTrips=new ArrayList<>();   //trips which the user wants to bookmark

    /**
     * Constructor for creating ResultAdapter object
     *
     * @param inputDataConnections
     * @param context
     */
    public ResultAdapter(List<DataConnection> inputDataConnections, Context context) {
        this.inputDataConnections = inputDataConnections;
        this.context = context;
    }


    /**
     *sets up the inflator for the view
     *
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.result_element, viewGroup, false);  // elements of recyclerView are in style of result_element

        return new ViewHolder(view);
    }

    /**
     *sets information for result element
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //setting data of recyclerView element
        viewHolder.trip.setText(printTrip(inputDataConnections.get(position)));
        viewHolder.tripDate.setText(printTripDate(inputDataConnections.get(position)));
        viewHolder.tripTime.setText(printTripTime(inputDataConnections.get(position)));
        viewHolder.tripDuration.setText(printTripDuration(inputDataConnections.get(position)));
        viewHolder.tripPriceAndEmissions.setText(printTripPriceAndEmissions(inputDataConnections.get(position)));
        viewHolder.tripStops.setText(printTripStops(inputDataConnections.get(position)));

        //creating recyclerView for stops of the trip element (nested recyclerView)
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        viewHolder.stopsRecyclerView.setLayoutManager(layoutManager);

        StopAdapter adapter=new StopAdapter(inputDataConnections.get(position).getIntermediatStations(),context);
        viewHolder.stopsRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return inputDataConnections.size();
    }


    /**
     * inner class Viewholder for ResultAdapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trip;
        private TextView tripDate;
        private TextView tripTime;
        private TextView tripDuration;
        private TextView tripPriceAndEmissions;
        private TextView tripStops;
        private ImageButton bookmarkButton;
        private RecyclerView stopsRecyclerView;
        private Button purchaseButton;


        /**
         * binds data and view elements
         *
         * @param view
         */
        public ViewHolder(View view) {
            super(view);

            trip = (TextView) view.findViewById(R.id.trip);
            tripDate = (TextView) view.findViewById(R.id.tripDate);
            tripTime = (TextView) view.findViewById(R.id.tripTime);
            tripDuration = (TextView) view.findViewById(R.id.tripDuration);
            tripPriceAndEmissions = (TextView) view.findViewById(R.id.tripPriceAndEmissions);
            tripStops = (TextView) view.findViewById(R.id.tripStops);
            bookmarkButton=(ImageButton) view.findViewById(R.id.bookmarkButton);
            stopsRecyclerView=(RecyclerView) view.findViewById(R.id.stopsRecyclerView);
            purchaseButton=(Button) view.findViewById(R.id.purchaseButton);

            /**
             * sets up ClickListener for bookmark button
             */
            bookmarkButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * defines what happens when bookmark button is clicked
                 *
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    if(!savedTrips.contains(inputDataConnections.get(getAdapterPosition()))) {  //trip is not saved and user clicks bookmark-star
                        savedTrips.add(inputDataConnections.get(getAdapterPosition()));  //added to list of trips the user wants to save
                        bookmarkButton.setSelected(true);   //star is is now filled with color
                        bookmarksScreen5.addBookmark(inputDataConnections.get(getAdapterPosition()));   //add data in bookmarkScreen5 so it is presented there as well
                    }
                    else{
                        //if the user wants to "unbookmark" a trip he needs to confirm it
                        AlertDialog.Builder confirmationAlert=new AlertDialog.Builder(context);
                        confirmationAlert.setTitle("Warning");
                        confirmationAlert.setMessage("Are you sure that you want to remove the bookmark?");

                        confirmationAlert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {   //if the user confirms
                                savedTrips.remove(inputDataConnections.get(getAdapterPosition()));
                                bookmarkButton.setSelected(false);  //star is unfilled with color again
                                bookmarksScreen5.removeBookmark(inputDataConnections.get(getAdapterPosition()));
                            }
                        });
                        confirmationAlert.setNegativeButton("no", new DialogInterface.OnClickListener() {  // if he neglects nothing happens
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                        confirmationAlert.create().show();
                    }
                }
            });

            /**
             * sets up ClickListener for purchase button
             */
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * defines what happens when purchase button is clicked
                 *
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    Intent ticketVendor=new Intent(Intent.ACTION_VIEW,inputDataConnections.get(getAdapterPosition()).getType().getVendorURL());
                    context.startActivity(ticketVendor);
                }
            });
        }

    }


    /**
     * Method for getting String of from-to information of resultElement (place)
     *
     * @param connection
     * @return
     */
    private String printTrip(DataConnection connection) {
        return connection.getStartCity() + " -> " + connection.getDestinationCity() + "  (" + connection.getType().toString() + ")";
    }

    /**
     * Method for getting String of from-to information of resultElement (date)
     * @param connection
     * @return
     */
    private String printTripDate(DataConnection connection) {
        String ret = "";
        if(connection.getStartDate().getDay()<10)   //to make sure always two numbers are used for the date view
            ret+="0"+connection.getStartDate().getDay()+ ".";
        else
            ret+=connection.getStartDate().getDay()+ ".";

        if(connection.getStartDate().getMonth()<10)   //to make sure always two numbers are used for the moth view
            ret+="0"+connection.getStartDate().getMonth()+ ".";
        else
            ret+=connection.getStartDate().getMonth()+ ".";

        return ret+connection.getStartDate().getYear();
    }

    /**
     * Method for getting String of from-to information of resultElement (time)
     *
     * @param connection
     * @return
     */
    private String printTripTime(DataConnection connection) {
        String ret = "";

        //start time
        DataTime startTime = connection.getStartTime();
        if(startTime.getHour()<10)  //to make sure always two numbers are used for the hour view
            ret+="0"+startTime.getHour()+ ":";
        else
            ret+=startTime.getHour()+ ":";
        if(startTime.getMinute()<10)  //to make sure always two numbers are used for the minute view
            ret+="0"+startTime.getMinute()+ ": ";
        else
            ret+=startTime.getMinute()+ ": ";
        ret += connection.getStartCity()+" "+connection.getStartLocation() + "\n";

        //return time
        DataTime returnTime = connection.getReturnTimeWithDuration();
        if(returnTime.getHour()<10)  //to make sure always two numbers are used for the hour view
            ret+="0"+returnTime.getHour()+ ":";
        else
            ret+=returnTime.getHour()+ ":";
        if(returnTime.getMinute()<10)  //to make sure always two numbers are used for the minute view
            ret+="0"+returnTime.getMinute()+ ": ";
        else
            ret+=returnTime.getMinute()+ ": ";
        ret += connection.getDestinationCity()+" "+connection.getDestinationLocation();

        if(connection.isReturnOnNextDay())
            ret+=" (arrival is next day)";
        return ret;
    }

    /**
     * Method for getting String of duration of resultElement
     *
     * @param connection
     * @return
     */
    private String printTripDuration(DataConnection connection) {
        return "Duration: " + connection.getDuration().getHour() + "h " + connection.getDuration().getMinute()+"min";
    }

    /**
     * Method for getting String of price an emissions information of resultElement
     *
     * @param connection
     * @return
     */
    private String printTripPriceAndEmissions(DataConnection connection) {
        return connection.getPrize() + "€ | ~"+connection.getCO2Bilance()+"kg CO2";
    }


    /**
     * Method for getting String of stops information of resultElement
     *
     * @param connection
     * @return
     */
    private String printTripStops(DataConnection connection) {
        return "Stops: "+connection.getSwitchTransfer();
    }
}