package com.example.studentbusreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentbusreservation.Admin.AdminMainActivity;
import com.example.studentbusreservation.MODELS.Assigned;
import com.example.studentbusreservation.MODELS.Bus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityAssignBus extends AppCompatActivity{

    private Toolbar toolbar;
    private Spinner spinnerFrom,spinnerTo,spinnerTime;

    String[] time={"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00"};

    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;
    private TextView busname,busregNo,location;
    private String name,reg,des,clock,id,seats, city,desID, desCity,from,to,destination;
    private Button btnAssignTimeToBus;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_bus);
        toolbar = findViewById(R.id.assign_toolbar);

        busregNo = findViewById(R.id.busregNo);
        busname = findViewById(R.id.busname);
        location=findViewById(R.id.city);
        btnAssignTimeToBus =findViewById(R.id.assign);

        toolbar.setTitle("Assign Bus");
        setSupportActionBar(toolbar);

        name = getIntent().getStringExtra("name");
        reg = getIntent().getStringExtra("reg");
        des =getIntent().getStringExtra("destination");
        id = getIntent().getStringExtra("id");
        seats = getIntent().getStringExtra("seats");
        city =getIntent().getStringExtra("city");
        progressDialog = new ProgressDialog(this);

        busname.setText(name);
        busregNo.setText(reg);
        location.setText(city);

        spinnerTime = findViewById(R.id.spinnerTime);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);

        ArrayAdapter timeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,time);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             clock =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destination");
        spinnerList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren())
                {
                    desCity = item.child("city").getValue().toString();
                    desID = item.child("destID").getValue().toString();
                    spinnerList.add(item.child("name").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spinnerFrom.setAdapter(adapter);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                from =parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), from, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerTo.setAdapter(adapter);
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                to =parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), from, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAssignTimeToBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              if(from.equals(to))
              {
                  Toast.makeText(getApplicationContext(), "Destinations must not be the same", Toast.LENGTH_SHORT).show();
              }
              else
              {
                  progressDialog.setTitle("Assign time to Bus");
                  progressDialog.setMessage("Please wait while checking assigning time to bus");
                  progressDialog.setCanceledOnTouchOutside(false);
                  progressDialog.show();

                  destination =from+" - "+to;
                  DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Assigned").child(id);
                  Assigned assigned = new Assigned(name,reg,clock,id,seats, city,destination,0,Integer.parseInt(seats));

                  ref.setValue(assigned).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){
                              Toast.makeText(getApplicationContext(), "Bus assigned successfully", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                              progressDialog.dismiss();
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

        });
    }


}