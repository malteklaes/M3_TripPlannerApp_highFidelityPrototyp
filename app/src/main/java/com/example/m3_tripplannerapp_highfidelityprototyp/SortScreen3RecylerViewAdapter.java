package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.content.Context;
import android.graphics.Color;
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

public class SortScreen3RecylerViewAdapter extends RecyclerView.Adapter<SortScreen3RecylerViewAdapter.MyViewHolder> {

    Context context;
    private List<DataConnection> connectionList;

    // to switch on a row item when its chosen
    private int chosenRowItem = -1;



    public SortScreen3RecylerViewAdapter(Context context, List<DataConnection> connectionList){
        this.context = context;
        this.connectionList = connectionList;
    }

    @NonNull
    @Override
    public SortScreen3RecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyler_view_row_fragments, parent, false);
        return new SortScreen3RecylerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortScreen3RecylerViewAdapter.MyViewHolder holder, int position) {
        DataConnection data = this.connectionList.get(position);
        String prize = this.connectionList.get(position).getPrize()+"";
        String type = this.connectionList.get(position).getType()+"";
        holder.tvDirection.setText("from: " + this.connectionList.get(position).getStartCity() + " " + this.connectionList.get(position).getStartLocation() + "\nto: " + this.connectionList.get(position).getDestinationCity() + " " + this.connectionList.get(position).getDestinationLocation());
        holder.tvPrize.setText(prize + " €");
        holder.tvStartTime.setText("at: " + this.connectionList.get(position).getStartTime().toString() + ",\non: " + this.connectionList.get(position).getStartDate().toString());
        holder.tvType.setText(type);

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
            String chosenRowItemColor = "#52307C";
            int chosenRowItemCOLOR = Color.parseColor(chosenRowItemColor);
            holder.cardView.setBackgroundColor(chosenRowItemCOLOR);
        } else {
            String normalRowItemColor = "#111f28";
            int normalRowItemCOLOR = Color.parseColor(normalRowItemColor);
            holder.cardView.setBackgroundColor(normalRowItemCOLOR);
        }

    }

    private void handleRowItemClick(DataConnection data) {
        // execute action, when row element was clicked
        if(!data.isReturn()){
            Log.d("transaction4" , "1 data.isReturn(): " + data.isReturn());
            ((sortScreen3) context).setResultFrag1Data(data);
        } else {
            Log.d("transaction4" , "2 data.isReturn(): " + data.isReturn());
            ((sortScreen3) context).setResultFrag2Data(data);
        }
    }

    @Override
    public int getItemCount() {
        return this.connectionList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // to change row item color
        CardView cardView;
        ImageView imageView;
        TextView tvDirection, tvPrize, tvStartTime, tvType;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewRow);
            imageView = itemView.findViewById(R.id.imageViewRecylerFrag);
            tvDirection = itemView.findViewById(R.id.textViewDirection);
            tvPrize = itemView.findViewById(R.id.textViewPrize);
            tvStartTime = itemView.findViewById(R.id.textViewStartTime);
            tvType = itemView.findViewById(R.id.textViewType);

        }

    }
}
