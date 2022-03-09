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

public class BookingsHistoryAdapter extends RecyclerView.Adapter<BookingsHistoryAdapter.viewholder> {
    private List<Bookings> Items;
    private Context context;

    public BookingsHistoryAdapter(List<Bookings> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_student_bookings, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.bname.setText(Items.get(position).getFullnames());
        holder.date.setText(Items.get(position).getDate());
        holder.viewTim.setText(Items.get(position).getTime());
        holder.destination.setText(Items.get(position).getDestination());

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private TextView bname,viewTim,date,destination;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.bdestination);
            date = itemView.findViewById(R.id.bdate);
            viewTim = itemView.findViewById(R.id.btime);
            bname = itemView.findViewById(R.id.bname);
        }
    }
}
