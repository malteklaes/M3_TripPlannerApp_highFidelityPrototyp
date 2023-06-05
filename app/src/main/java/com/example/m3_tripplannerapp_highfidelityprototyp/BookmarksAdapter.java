package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder>{

    List<DataConnection> bookmarks;
    Context context;

    /**
     * Constructor for creating BookmarksAdapter object
     *
     * @param inputDataConnections
     * @param context
     */
    public BookmarksAdapter(List<DataConnection> inputDataConnections, Context context) {
        this.bookmarks = inputDataConnections;
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
    public BookmarksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.result_element, viewGroup, false);  // elements of recyclerView are in style of result_element

        return new BookmarksAdapter.ViewHolder(view);
    }

    /**
     * sets information for bookmark element
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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


    /**
     * inner class Viewholder for BookmarkAdapter
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
                    //if the user wants to "unbookmark" a trip he needs to confirm it
                    View confirmationAlertView= LayoutInflater.from(context).inflate(R.layout.save_bookmark_alert,null);
                    AlertDialog.Builder confirmationAlert=new AlertDialog.Builder(context);
                    confirmationAlert.setView(confirmationAlertView);

                    Button buttonYes=(Button) confirmationAlertView.findViewById(R.id.deleteYes);
                    Button buttonNo=(Button) confirmationAlertView.findViewById(R.id.deleteNo);

                    AlertDialog confirmAlertDialog = confirmationAlert.create();
                    confirmAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    confirmAlertDialog.show();
                    buttonYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // if the user confirms the chosen bookmark is removed from the view
                            ((bookmarksScreen5) context).removeBookmark(bookmarks.get(getAdapterPosition()));
                            ((bookmarksScreen5) context).createRecyclerView();  //to update the recyclerView it is created again
                            confirmAlertDialog.dismiss();
                            Toast toast = Toast.makeText(context, "Bookmark has been removed", Toast.LENGTH_SHORT);
                            View toastView = toast.getView();
                            GradientDrawable drawable = new GradientDrawable();
                            drawable.setShape(GradientDrawable.RECTANGLE);
                            drawable.setColor(Color.parseColor("#80FF0000"));
                            drawable.setCornerRadius(80); // Adjust the corner radius as needed
                            toastView.setBackground(drawable);
                            toast.show();
                        }
                    });
                    buttonNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmAlertDialog.dismiss();
                        }
                    });
                }
            });

            /**
             * sets up ClickListener for purchase button
             */
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ticketVendor=new Intent(Intent.ACTION_VIEW,bookmarks.get(getAdapterPosition()).getType().getVendorURL());
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
     *
     * @param connection
     * @return
     */
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
     * Method for getting String of from-to information of resultElement (duration)
     *
     * @param connection
     * @return
     */
    private String printTripDuration(DataConnection connection) {
        return "Duration: " + connection.getDuration().getHour() + "h " + connection.getDuration().getMinute()+"min";
    }

    /**
     * Method for getting String of from-to information of resultElement (price and emissions)
     *
     * @param connection
     * @return
     */
    private String printTripPriceAndEmissions(DataConnection connection) {
        return connection.getPrize() + "€ | ~"+connection.getCO2Bilance()+"kg CO2";
    }

    /**
     * Method for getting String of from-to information of resultElement (stops)
     *
     * @param connection
     * @return
     */
    private String printTripStops(DataConnection connection) {
        return "Stops: "+connection.getSwitchTransfer();
    }
}
