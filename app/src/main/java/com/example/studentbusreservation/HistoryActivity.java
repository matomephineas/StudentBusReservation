package com.example.studentbusreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentbusreservation.Adapters.BookingsAdapter;
import com.example.studentbusreservation.MODELS.Bookings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    BookingsAdapter adapter;
    ArrayList<Bookings> mList;
    FirebaseAuth mAuth;
    String currentUser;
    private TextView destination,date,tvTime,seat;
    private Button cancelBookings;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerview1);

        date = findViewById(R.id.date);
        tvTime = findViewById(R.id.viewTime);
        seat =findViewById(R.id.seat);
        destination =findViewById(R.id.destination);
        cancelBookings =findViewById(R.id.cancelBookings);
        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid().toString();
        getValue(currentUser);

        cancelBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Cancel Booking");
                dialog.setMessage("Please wait while cancelling your bookings");
                dialog.show();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings");
                reference.child(currentUser).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Successfully cancelled", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ResToCampus.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void getValue(String currentUser)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings");
        reference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    seat.setText(snapshot.child("seatNumber").getValue().toString());
                    date.setText(snapshot.child("date").getValue().toString());
                    destination.setText(snapshot.child("destination").getValue().toString());
                    tvTime.setText(snapshot.child("time").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}