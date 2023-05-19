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

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder>{

    List<DataConnection> bookmarks;
    Context context;

    public BookmarksAdapter(List<DataConnection> inputDataConnections, Context context) {
        this.bookmarks = inputDataConnections;
        this.context = context;
    }


    @Override
    public BookmarksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.result_element, viewGroup, false);  // elements of recyclerView are in style of result_element

        return new BookmarksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarksAdapter.ViewHolder viewHolder, final int position) {
        //setting data of recyclerView element
        viewHolder.trip.setText(printTrip(bookmarks.get(position)));
        viewHolder.tripDate.setText(printTripDate(bookmarks.get(position)));
        viewHolder.tripTime.setText(printTripTime(bookmarks.get(position)));
        viewHolder.tripDuration.setText(printTripDuration(bookmarks.get(position)));
        viewHolder.tripPriceAndEmissions.setText(printTripPriceAndEmissions(bookmarks.get(position)));
        viewHolder.tripStops.setText(printTripStops(bookmarks.get(position)));
        viewHolder.bookmarkButton.setSelected(true);  //in bookmarkView the bookmark-star is always filled with color

        //creating recyclerView for stops of the trip element (nested recyclerView)
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        viewHolder.stopsRecyclerView.setLayoutManager(layoutManager);

        StopAdapter adapter=new StopAdapter(bookmarks.get(position).getIntermediatStations(),context);
        viewHolder.stopsRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


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

            bookmarkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if the user wants to "unbookmark" a trip he needs to confirm it
                    AlertDialog.Builder confirmationAlert=new AlertDialog.Builder(context);
                    confirmationAlert.setTitle("Warning");
                    confirmationAlert.setMessage("Are you sure that you want to remove the bookmark?");

                    confirmationAlert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // if the user confirms the chosen bookmark is removed from the view
                            ((bookmarksScreen5) context).removeBookmark(bookmarks.get(getAdapterPosition()));
                            ((bookmarksScreen5) context).createRecyclerView();  //to update the recyclerView it is created again
                        }
                    });
                    confirmationAlert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    confirmationAlert.create().show();
                }
            });

            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ticketVendor=new Intent(Intent.ACTION_VIEW,bookmarks.get(getAdapterPosition()).getType().getVendorURL());
                    context.startActivity(ticketVendor);
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
