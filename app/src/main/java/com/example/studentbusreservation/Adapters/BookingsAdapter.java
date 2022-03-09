package com.example.studentbusreservation.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentbusreservation.MODELS.Bookings;
import com.example.studentbusreservation.R;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.viewholder> {
    private List<Bookings> Items;
    private Context context;

    public BookingsAdapter(List<Bookings> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlebookings, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.seat.setText(Items.get(position).getSeatNumber());
        holder.date.setText(Items.get(position).getDate());
        holder.viewTim.setText(Items.get(position).getTime());
        holder.destination.setText(Items.get(position).getDestination());

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private TextView seat,viewTim,date,destination;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.destination);
            date = itemView.findViewById(R.id.date);
            seat = itemView.findViewById(R.id.seat);
            viewTim = itemView.findViewById(R.id.viewTime);
        }
    }
}
