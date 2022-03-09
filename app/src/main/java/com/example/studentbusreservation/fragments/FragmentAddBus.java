package com.example.studentbusreservation.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentbusreservation.MODELS.Bus;
import com.example.studentbusreservation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentAddBus extends AppCompatDialogFragment {

    private EditText numSeats,busName,regNumber,Province;
    private String busID,seats,name,reg_number,from,to,province;
    private Button submitBusDetails;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("Bus");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_add_bus, container, false);

       numSeats = view.findViewById(R.id.numSeats);
       busName = view.findViewById(R.id.busName);
       regNumber = view.findViewById(R.id.regNumber);
        submitBusDetails = view.findViewById(R.id.submitBusDetails);
        Province = view.findViewById(R.id.province);
       progressDialog = new ProgressDialog(getContext());

        submitBusDetails.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               registerBusDetails();
           }
       });
       return view;
    }
    private boolean validateProvince()
    {
        province =Province.getText().toString().trim();
        if(seats.isEmpty())
        {
            Province.setError("Field must not be empty");
            Province.requestFocus();
            return false;
        }
        else
        {
            Province.setError(null);
            Province.requestFocus();
            return true;
        }
    }
    private boolean validateSeats()
    {
        seats =numSeats.getText().toString().trim();
        if(seats.isEmpty())
        {
            numSeats.setError("Field must not be empty");
            numSeats.requestFocus();
            return false;
        }
        else
        {
            numSeats.setError(null);
            numSeats.requestFocus();
            return true;
        }
    }
    private boolean validateRegNumber()
    {
        reg_number =regNumber.getText().toString().trim();
        if(reg_number.isEmpty())
        {
            regNumber.setError("Field must not be empty");
            regNumber.requestFocus();

            return false;
        }
        else
        {
            regNumber.setError(null);

            regNumber.requestFocus();
            return true;
        }
    }
    private boolean validateName()
    {
        name =busName.getText().toString().trim();
        if(name.isEmpty())
        {
            busName.setError("Field must not be empty");
            busName.requestFocus();

            return false;
        }
        else
        {
            busName.setError(null);

            busName.requestFocus();
            return true;
        }
    }
    private void registerBusDetails()
    {
      if(!validateName() | !validateSeats() | !validateRegNumber() |!validateProvince()){
          return;
      }
      else
      {
          ProgressDialog progressDialog = new ProgressDialog(getContext());
          progressDialog.setTitle("Register Bus");
          progressDialog.setMessage("Please wait while checking bus credentials");
          progressDialog.setCanceledOnTouchOutside(false);
          progressDialog.show();

          DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bus").push();


          String destination = from + " - "+to;
          busID = ref.getKey();
          Bus bus = new Bus(busID,seats,name,reg_number,province.toUpperCase());

          ref.setValue(bus).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful())
                  {
                      Toast.makeText(getContext(), "Bus added successfully", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(getContext(),ViewListOfBuses.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);
                      progressDialog.dismiss();
                  }
                  else
                  {
                      Toast.makeText(getContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      progressDialog.dismiss();
                  }
              }
          });

      }
    }
}