package com.example.studentbusreservation.Admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.studentbusreservation.fragments.FragmentAddBus;
import com.example.studentbusreservation.fragments.FragmentAddDestination;
import com.example.studentbusreservation.R;
import com.example.studentbusreservation.fragments.ViewAssignedBuses;
import com.example.studentbusreservation.fragments.ViewBookedList;
import com.example.studentbusreservation.fragments.ViewListOfBuses;
import com.example.studentbusreservation.fragments.ViewDestinations;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    BottomNavigationView navigation;
    FrameLayout main_container;
    Fragment active;
    TextView tvTotalStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        toolbar = findViewById(R.id.admin_toolbar);

        navigation = findViewById(R.id.admin_bottom_navigation);
        main_container = findViewById(R.id.admin_main_container);
        tvTotalStudents = findViewById(R.id.tvTotalStudents);
        toolbar.setTitle("DASHBOARD");
        setSupportActionBar(toolbar);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int bookings = (int) snapshot.getChildrenCount();
                tvTotalStudents.setText(String.valueOf(bookings));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Fragment fragment1 = new ViewListOfBuses();
        final Fragment fragment2 = new ViewAssignedBuses();
        final Fragment fragment3 = new ViewDestinations();
        final Fragment fragment4 = new ViewBookedList();

        final FragmentManager fm = getSupportFragmentManager();
        active = fragment1;
        fm.beginTransaction().add(R.id.admin_main_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.admin_main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.admin_main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.admin_main_container, fragment1, "1").commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_bus:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.admin_nav_view_assigned:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.admin_nav_view_destinations:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                    case R.id.admin_nav_view_bookings:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_bus:
                FragmentAddBus fragmentAddBus = new FragmentAddBus();
                fragmentAddBus.show(getSupportFragmentManager(), "popup dialog");
                return true;

            case R.id.action_add_school:
                FragmentAddDestination fragmentAddDestination = new FragmentAddDestination();
                fragmentAddDestination.show(getSupportFragmentManager(), "popup dialog");
                return true;

        }
        return super.onOptionsItemSelected(item);

    }
}