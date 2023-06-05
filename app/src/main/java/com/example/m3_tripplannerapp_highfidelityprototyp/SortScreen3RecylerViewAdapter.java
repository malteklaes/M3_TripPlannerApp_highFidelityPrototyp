package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * this class manages both fragments to show results
 */
public class SortScreen3RecylerViewAdapter extends RecyclerView.Adapter<SortScreen3RecylerViewAdapter.MyViewHolder> {

    Context context;
    private List<DataConnection> connectionList;

    // to switch on a row item when its chosen
    private int chosenRowItem = -1;


    /**
     * sets context and connectionList
     * @param context
     * @param connectionList
     */
    public SortScreen3RecylerViewAdapter(Context context, List<DataConnection> connectionList){
        this.context = context;
        this.connectionList = connectionList;
    }

    /**
     * sets up the inflator for the view
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public SortScreen3RecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyler_view_row_fragments, parent, false);
        return new SortScreen3RecylerViewAdapter.MyViewHolder(view);
    }

    /**
     * manages to set information into each row element for showing the result
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SortScreen3RecylerViewAdapter.MyViewHolder holder, int position) {
        DataConnection data = this.connectionList.get(position);
        String prize = this.connectionList.get(position).getPrize()+"";
        String type = this.connectionList.get(position).getType()+"";
        holder.tvDirection.setText("from: " + this.connectionList.get(position).getStartCity() + " " + this.connectionList.get(position).getStartLocation() + "\nto: " + this.connectionList.get(position).getDestinationCity() + " " + this.connectionList.get(position).getDestinationLocation());
        holder.tvPrize.setText(prize + " €");
        holder.tvStartTime.setText("at: " + this.connectionList.get(position).getStartTime().toString() + ",\non: " + this.connectionList.get(position).getStartDate().toString());
        //holder.tvType.setText(type);
        Drawable drawable = null;
        if(type.equals("Bus")){
             drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bus);
        } else if(type.equals("Car_Sharing")){
             drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.car);
        } else if(type.equals("Train")){
             drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.train);
        } else if(type.equals("Ship")){
             drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ship);
        } else if(type.equals("Plane")){
             drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.plane);
        } else if(type.equals("Mix")){

        }else {
             drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.car);
            holder.transportTyp.setImageDrawable(drawable);
        }
        holder.transportTyp.setImageDrawable(drawable);
        Log.d("output", "onBindViewHolder: "+ type.getClass());

        // make each row element clickable
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // data will be processed, when row item will be clicked
                handleRowItemClick(data);
                // for changing color of row item when clicked on it
                chosenRowItem = position;
                notifyDataSetChanged();
            }
        });

        // for changing color of row item when clicked on it
        if(chosenRowItem==position){
            String chosenRowItemColor = "#ecfc68"; //#52307C";
            String chosenTextColor = "#3aab7e";
            String chosenTextPrizeColor = "#E6196A";
            int chosenRowItemCOLOR = Color.parseColor(chosenRowItemColor);
            Log.d("output", "choosen: "+ type);
            holder.cardView.setBackgroundColor(chosenRowItemCOLOR);
            holder.tvDirection.setTextColor(Color.parseColor(chosenTextColor));
            holder.tvDirection.setTypeface(null, Typeface.BOLD);
            holder.tvPrize.setTextColor(Color.parseColor(chosenTextPrizeColor));
            holder.tvPrize.setTypeface(null, Typeface.BOLD);
            holder.tvStartTime.setTextColor(Color.parseColor(chosenTextColor));
            holder.tvStartTime.setTypeface(null, Typeface.BOLD);
        } else {
            String darkBlueColor = "#111f28";
            String normalRowItemColor = darkBlueColor;
            int normalRowItemCOLOR = Color.parseColor(normalRowItemColor);
            holder.cardView.setBackgroundColor(normalRowItemCOLOR);
            holder.tvDirection.setTextColor(Color.WHITE);
            holder.tvDirection.setTypeface(null, Typeface.NORMAL);
            holder.tvPrize.setTextColor(Color.WHITE);
            holder.tvPrize.setTypeface(null, Typeface.NORMAL);
            holder.tvStartTime.setTextColor(Color.WHITE);
            holder.tvStartTime.setTypeface(null, Typeface.NORMAL);
        }

    }

    /**
     * delegates data to right fragment
     * @param data
     */
    private void handleRowItemClick(DataConnection data) {
        // execute action, when row element was clicked
        if(!data.isReturn()){
            ((sortScreen3) context).setResultFrag1Data(data);
        } else {
            ((sortScreen3) context).setResultFrag2Data(data);
        }
    }

    @Override
    public int getItemCount() {
        return this.connectionList.size();
    }

    /**
     * inner class to manages viewHolder for RecyclerView
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // to change row item color
        CardView cardView;
        ImageView imageView, transportTyp;
        TextView tvDirection, tvPrize, tvStartTime; // tvType;


        /**
         * main constructor to set all necessary object variables
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewRow);
            imageView = itemView.findViewById(R.id.imageViewRecylerFrag);
            tvDirection = itemView.findViewById(R.id.textViewDirection);
            tvPrize = itemView.findViewById(R.id.textViewPrize);
            tvStartTime = itemView.findViewById(R.id.textViewStartTime);
            //tvType = itemView.findViewById(R.id.textViewType);
            transportTyp = itemView.findViewById(R.id.transportType);

        }

    }
}
