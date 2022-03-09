package com.example.studentbusreservation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentbusreservation.Adapters.BookingsHistoryAdapter;
import com.example.studentbusreservation.MODELS.Bookings;
import com.example.studentbusreservation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewBookedList extends Fragment {

    BookingsHistoryAdapter adapter;
    ArrayList<Bookings> mList;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_booked_list, container, false);
        recyclerView= view.findViewById(R.id.recyclerview12);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        adapter = new BookingsHistoryAdapter(mList,getContext());
        recyclerView.setAdapter(adapter);

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Bookings");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Bookings services = dataSnapshot.getValue(Bookings.class);
                    mList.add(services);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}