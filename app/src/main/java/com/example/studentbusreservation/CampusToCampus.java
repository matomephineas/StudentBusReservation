package com.example.studentbusreservation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CampusToCampus extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatePicker simpleDatePicker;
    private int dayOfMonth;
    private String date,destination,bookingTime,fromDestination,toDestination, desCity1,desCity2,desID2,desID;

    private TextView viewDate,selected_date_tv;
    private Spinner spinnerFrom,spinnerTo,spinnerTime;
    private Button btn_book_seat;
    ArrayList<String> campusList;
    ArrayAdapter<String> adapter;

    ArrayList<String> resList;
    ArrayAdapter<String> resadapter;

    String[] time={"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00"};

    private FirebaseAuth mAuth;
    private String currentUser;
    private Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_to_campus);
        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        selected_date_tv = findViewById(R.id.selected_date_tv);
        btn_book_seat = findViewById(R.id.book_seat);
        spinnerFrom =findViewById(R.id.spinnerFrom);
        spinnerTo =findViewById(R.id.spinnerTo);
        spinnerTime =findViewById(R.id.spinnerTime);
        toolbar = findViewById(R.id.campus_toolbar);
        toolbar.setTitle("Campus to Campus");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.campus_drawer_layout);
        NavigationView navigationView = findViewById(R.id.campus_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // get the values for day of month , month and year from a date picker

        resList = new ArrayList<>();
        resadapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, resList);
        resadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Query reference = FirebaseDatabase.getInstance().getReference("Destination").orderByChild("type").equalTo("Campus");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren())
                {
                    desCity1 = item.child("city").getValue().toString();
                    desID = item.child("destID").getValue().toString();
                    resList.add(item.child("name").getValue().toString());
                }
                resadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        campusList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, campusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Query query = FirebaseDatabase.getInstance().getReference("Destination").orderByChild("type").equalTo("Campus");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren())
                {
                    desCity2 = item.child("city").getValue().toString();
                    desID2 = item.child("destID").getValue().toString();
                    campusList.add(item.child("name").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerFrom.setAdapter(resadapter);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                fromDestination =parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), from, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Please select campus", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerTo.setAdapter(adapter);
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                toDestination =parent.getItemAtPosition(position).toString();
                // Toast.makeText(getApplicationContext(), from, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Please select campus", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter timeAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,time);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookingTime =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please select time", Toast.LENGTH_SHORT).show();

            }
        });
        simpleDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day)
            {
                DecimalFormat format = new DecimalFormat("00");
                format.setRoundingMode(RoundingMode.DOWN);

                day =simpleDatePicker.getDayOfMonth();
                month =simpleDatePicker.getMonth();
                year = simpleDatePicker.getYear();
                // display the values by using a toast
                date =  year + "/" + format.format(Double.valueOf(month)+1) + "/"+ format.format(Double.valueOf(day));
                selected_date_tv.setText(date);
                DateFormat formatter = new SimpleDateFormat("dd");
                Calendar c = Calendar.getInstance();

                String today = formatter.format(c.getTime());
                dayOfMonth = Integer.parseInt(today);

                if(day==dayOfMonth )
                    Toast.makeText(getApplicationContext(), "You cannot book for today, please select tomorrows date", Toast.LENGTH_SHORT).show();
                else if(day <dayOfMonth)
                    Toast.makeText(getApplicationContext(), "This day "+ day + " has already passed", Toast.LENGTH_SHORT).show();
                else
                {
                    btn_book_seat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            confirmBookings(fromDestination,toDestination,date,bookingTime,selected_date_tv);

                        }
                    });
                }

            }
        });

    }

    private void confirmBookings(String campus1, String campus2, String date, String bookingTime, TextView selected_date_tv)
    {
        destination = campus1 +" - "+campus2;
        if(campus1.equals(campus2))
        {
            Toast.makeText(getApplicationContext(), "Destinations must not be equal", Toast.LENGTH_SHORT).show();
        }
        else if(selected_date_tv.getText().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please select date", Toast.LENGTH_SHORT).show();
        }
        else if(campus1.equals("Campus"))
        {
            Toast.makeText(getApplicationContext(), "Please select the location you are from", Toast.LENGTH_SHORT).show();
        }
        else if(campus2.equals("Campus"))
        {
            Toast.makeText(getApplicationContext(), "Please select the location you are going", Toast.LENGTH_SHORT).show();
        }
        else if(!desCity1.equals(desCity2))
        {
            Toast.makeText(getApplicationContext(), "Both campus locations/city must be the same", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), ConfirmBookings.class);
            intent.putExtra("date",date);
            intent.putExtra("destination",destination);
            intent.putExtra("time",bookingTime);
            intent.putExtra("campus2",campus2);
            intent.putExtra("campus1",campus1);
            intent.putExtra("desCity1", desCity1);
            intent.putExtra("desCity2", desCity2);
            startActivity(intent);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
//                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//                intent.putExtra("type", "Customers");
//                startActivity(intent);
//                finish();
//                return true;
            case R.id.nav_view_bookings:
                Intent intent1 = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent1);
                finish();
                return true;
            default:
                break;
        }
        return false;
    }
}