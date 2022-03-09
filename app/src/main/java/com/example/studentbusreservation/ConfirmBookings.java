package com.example.studentbusreservation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentbusreservation.MODELS.Assigned;
import com.example.studentbusreservation.MODELS.Bookings;
import com.example.studentbusreservation.MODELS.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfirmBookings extends AppCompatActivity {

    private TextView view,tvdestination,tvTotal,time, city, regCampus;

    String date,destination,bookingTime,campusName1,campusName2,desCity1,desCity2,myCity,myCampus;
    private int count =1,seats;
    private FirebaseAuth mAuth;
    private String userID;
    private Button btnConfirmBookings;
    private LinearLayout seat1 ,seat2,seat3,seat4,seat5;
    int seat=0;
    private  ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private StudentBusAdapter adapter;
    ArrayList<Assigned> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_bookings);
        recyclerView = findViewById(R.id.list_of_assigned_buses);

        tvdestination = findViewById(R.id.tvdestination);
        city = findViewById(R.id.city);
        regCampus = findViewById(R.id.regCampus);
        tvTotal = findViewById(R.id.tvTotal);
        view = findViewById(R.id.date);
        time = findViewById(R.id.time);
        seat1 = findViewById(R.id.seat1);
        seat2 = findViewById(R.id.seat2);
        seat3 = findViewById(R.id.seat3);
        seat4 = findViewById(R.id.seat4);
        seat5 = findViewById(R.id.seat5);


        date = getIntent().getStringExtra("date");
        destination = getIntent().getStringExtra("destination");
        bookingTime = getIntent().getStringExtra("time");
        campusName2 = getIntent().getStringExtra("campus2");
        campusName1 = getIntent().getStringExtra("campus1");
        desCity1 = getIntent().getStringExtra("desCity1");
        desCity2 = getIntent().getStringExtra("desCity2");

        progressDialog = new ProgressDialog(this);
        view.setText(date);
        tvdestination.setText(destination);
        time.setText(bookingTime);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
       // Toast.makeText(getApplicationContext(), currentUser, Toast.LENGTH_SHORT).show();

        seat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 seat =1;
                 seat1.setBackgroundColor(Color.RED);
                seat2.setBackgroundColor(Color.TRANSPARENT);
                seat3.setBackgroundColor(Color.TRANSPARENT);
                seat4.setBackgroundColor(Color.TRANSPARENT);
                seat5.setBackgroundColor(Color.TRANSPARENT);
                Toast.makeText(getApplicationContext(), "You selected "+seat, Toast.LENGTH_SHORT).show();
            }
        });
        seat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 seat =2;
                seat2.setBackgroundColor(Color.BLUE);
                seat1.setBackgroundColor(Color.TRANSPARENT);
                seat3.setBackgroundColor(Color.TRANSPARENT);
                seat4.setBackgroundColor(Color.TRANSPARENT);
                seat5.setBackgroundColor(Color.TRANSPARENT);
                Toast.makeText(getApplicationContext(), "You selected "+seat, Toast.LENGTH_SHORT).show();
            }
        });
        seat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               seat =3;
                seat3.setBackgroundColor(Color.GREEN);
                seat1.setBackgroundColor(Color.TRANSPARENT);
                seat2.setBackgroundColor(Color.TRANSPARENT);
                seat4.setBackgroundColor(Color.TRANSPARENT);
                seat5.setBackgroundColor(Color.TRANSPARENT);
                Toast.makeText(getApplicationContext(), "You selected "+seat, Toast.LENGTH_SHORT).show();
            }
        });
        seat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seat =4;
                seat4.setBackgroundColor(Color.YELLOW);
                seat1.setBackgroundColor(Color.TRANSPARENT);
                seat3.setBackgroundColor(Color.TRANSPARENT);
                seat2.setBackgroundColor(Color.TRANSPARENT);
                seat5.setBackgroundColor(Color.TRANSPARENT);
                Toast.makeText(getApplicationContext(), "You selected "+seat, Toast.LENGTH_SHORT).show();
            }
        });
        seat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seat =5;
                seat5.setBackgroundColor(Color.GRAY);
                seat1.setBackgroundColor(Color.TRANSPARENT);
                seat3.setBackgroundColor(Color.TRANSPARENT);
                seat4.setBackgroundColor(Color.TRANSPARENT);
                seat2.setBackgroundColor(Color.TRANSPARENT);
                Toast.makeText(getApplicationContext(), "You selected "+seat, Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int bookings = (int) snapshot.getChildrenCount();
                tvTotal.setText(String.valueOf(bookings));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mList = new ArrayList<>();
        adapter = new StudentBusAdapter(mList,getApplicationContext());
        recyclerView.setAdapter(adapter);

        DatabaseReference assigned = FirebaseDatabase.getInstance().getReference("Assigned");

        assigned.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Assigned services = dataSnapshot.getValue(Assigned.class);
                    mList.add(services);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference("Users");
        currentUser.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    myCity = snapshot.child("city").getValue().toString();
                    myCampus = snapshot.child("selectedCampusName").getValue().toString();
                    city.setText(myCity);
                    regCampus.setText(myCampus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class StudentBusAdapter extends RecyclerView.Adapter<StudentBusAdapter.viewholder>
    {
        private List<Assigned> Items;
        private Context context;

        public StudentBusAdapter(List<Assigned> items, Context context) {
            Items = items;
            this.context = context;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_single_bus, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position)
        {
            holder.bus_name.setText(Items.get(position).getName());
            holder.bus_seats.setText(String.valueOf(Items.get(position).getSeats()));
            holder.bus_reg_number.setText(Items.get(position).getReg());
            holder.bus_sets_booked.setText(String.valueOf(Items.get(position).getCount()));
            holder.destin.setText(Items.get(position).getDestination());
            holder.bus_time.setText(Items.get(position).getClock());
            holder.location.setText(Items.get(position).getProvince());
            holder.seats_vailable.setText(String.valueOf(Items.get(position).getAvailableSeats()));

            holder.select_bus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference bus = FirebaseDatabase.getInstance().getReference("Assigned").child(Items.get(position).getId());
                    bus.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.exists())
                            {
                                String s =String.valueOf(snapshot.child("seats").getValue());
                                String busCount =String.valueOf(snapshot.child("count").getValue());
                                String availSeats =String.valueOf(snapshot.child("availableSeats").getValue());
                                String busDestination =String.valueOf(snapshot.child("destination").getValue());
                                int availableSeats = Integer.parseInt(s);
                                int cnt = Integer.parseInt(busCount);

                                if(count > availableSeats)
                                {
                                    Toast.makeText(getApplicationContext(), "Bus fully booked", Toast.LENGTH_SHORT).show();
                                }
                                else if(!myCity.equals(snapshot.child("province")))
                                {
                                    Toast.makeText(getApplicationContext(), "You cannot select a bus that is in: "+snapshot.child("province").getValue().toString() +" while you are in: "+myCity, Toast.LENGTH_SHORT).show();
                                }
                                else if(!destination.equals(busDestination))
                                {
                                    Toast.makeText(getApplicationContext(), "Bus does not go to where you are going", Toast.LENGTH_SHORT).show();
                                }
                                else if(!bookingTime.equals(snapshot.child("clock").getValue()))
                                {
                                    Toast.makeText(getApplicationContext(), "Wrong chosen time,bus travels at " +snapshot.child("clock").getValue() , Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings").child(userID);
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists())
                                            {
                                                Toast.makeText(getApplicationContext(), "You have already booked", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(seat ==0)
                                            {
                                                Toast.makeText(getApplicationContext(), "Please select a seat", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                                                refUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists())
                                                        {
                                                            Users users = snapshot.getValue(Users.class);
                                                            Bookings bookings = new Bookings(
                                                                    userID,users.getFullnames(),String.valueOf(users.getIdentity()),
                                                                    destination,bookingTime,date,String.valueOf(seat),String.valueOf(count),
                                                                    Items.get(position).getId());

                                                            reference.setValue(bookings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful())
                                                                    {
                                                                        HashMap<String,Object> map = new HashMap<>();
                                                                        map.put("count", cnt + count);
                                                                        map.put("bookedSeats",count);
                                                                        map.put("availableSeats", availableSeats - count);
                                                                        bus.updateChildren(map);
                                                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                                                        count++;

                                                                    }
                                                                    else
                                                                    {
                                                                        Toast.makeText(getApplicationContext(), "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        progressDialog.dismiss();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
        }

        @Override
        public int getItemCount() {
            return Items.size();
        }

        public class viewholder extends RecyclerView.ViewHolder
        {
            private TextView bus_name, bus_reg_number, bus_seats,bus_time, bus_sets_booked,destin,seats_vailable,location;
            private Button select_bus;
            public viewholder(@NonNull View itemView)
            {
                super(itemView);
                location = itemView.findViewById(R.id.location);
                seats_vailable = itemView.findViewById(R.id.seats_vailable);
                bus_sets_booked = itemView.findViewById(R.id.bus_sets_booked);
                bus_name =itemView.findViewById(R.id.bus_name);
                bus_reg_number =itemView.findViewById(R.id.bus_reg_number);
                bus_seats =itemView.findViewById(R.id.bus_seats);
                bus_time =itemView.findViewById(R.id.bus_time);
                destin =itemView.findViewById(R.id.destin);
                select_bus = itemView.findViewById(R.id.select_bus);

            }

        }
    }

}