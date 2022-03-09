package com.example.studentbusreservation.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentbusreservation.ActivityAssignBus;
import com.example.studentbusreservation.MODELS.Bus;
import com.example.studentbusreservation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewListOfBuses extends Fragment {

    BusAdapter adapter;
    ArrayList<Bus> mList;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_view_buses, container, false);

        recyclerView= view.findViewById(R.id.view_buses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        adapter = new BusAdapter(mList,getContext());
        recyclerView.setAdapter(adapter);

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Bus");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot item: snapshot.getChildren())
                {
                    Bus bus = item.getValue(Bus.class);
                    mList.add(bus);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       return view;
    }

    public class BusAdapter extends RecyclerView.Adapter<BusAdapter.viewHolder>
    {
        private List<Bus> Items;
        private Context context;

        public BusAdapter(List<Bus> items, Context context) {
            Items = items;
            this.context = context;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bus,parent,false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.name.setText(Items.get(position).getName());
            holder.seats.setText(String.valueOf(Items.get(position).getSeats()));
            holder.reg_number.setText(Items.get(position).getReg_number());
            holder.city.setText(Items.get(position).getCity());
            holder.btnSelectBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), ActivityAssignBus.class);
                    intent.putExtra("name",Items.get(position).getName());
                    intent.putExtra("reg",Items.get(position).getReg_number());
                    intent.putExtra("id",Items.get(position).getBusID());
                    intent.putExtra("seats",Items.get(position).getSeats());
                    intent.putExtra("city",Items.get(position).getCity());

                    startActivity(intent);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bus").child(Items.get(position).getBusID());
                    reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context, "Bus successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(context, "an error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bus").child(Items.get(position).getBusID());
                    HashMap<String,Object> map = new HashMap<>();

                    map.put("name",holder.name.getText().toString().trim());
                    map.put("city",holder.city.getText().toString().trim());
                    map.put("seats",holder.seats.getText().toString().trim());
                    map.put("reg_number",holder.reg_number.getText().toString().trim());

                    reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context, "Bus successfully updated", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(context, "failed to update", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }

        @Override
        public int getItemCount() {
            return Items.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder
        {
            private TextView name,reg_number,seats,city,btnSelectBus;
            private ImageView edit,delete;
            private RelativeLayout relativeLayout;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                city = itemView.findViewById(R.id.city);
                name=itemView.findViewById(R.id.name);
                reg_number=itemView.findViewById(R.id.reg_number);
                seats=itemView.findViewById(R.id.seats);
                delete=itemView.findViewById(R.id.delete);
                edit=itemView.findViewById(R.id.edit);
                btnSelectBus = itemView.findViewById(R.id.btnSelectBus);

            }
        }
    }
}