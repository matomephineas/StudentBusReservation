package com.example.studentbusreservation.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentbusreservation.Admin.AdminMainActivity;
import com.example.studentbusreservation.MODELS.DestinationDetails;
import com.example.studentbusreservation.R;
import com.example.studentbusreservation.ResToCampus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.ganfra.materialspinner.MaterialSpinner;


public class FragmentAddDestination extends AppCompatDialogFragment {
    private Button submitDestinationDetails;
    private EditText destinationCity,destinationName;
    private String name,city,type;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("Bus");
    private MaterialSpinner spinner;
    String[] typeArray={"Campus","Res"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_destination, container, false);

        submitDestinationDetails = view.findViewById(R.id.submitDestinationDetails);
        destinationCity = view.findViewById(R.id.destinationCity);
        destinationName = view.findViewById(R.id.destinationName);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,typeArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type =parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please select pickup location", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), ResToCampus.class));
            }
        });

        progressDialog = new ProgressDialog(getContext());
        submitDestinationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDestDetails();
            }
        });
        return view;
    }

    private boolean validateCity()
    {
        city =destinationCity.getText().toString().trim();
        if(city.isEmpty())
        {
            destinationCity.setError("Field must not be empty");
            destinationCity.requestFocus();
            return false;
        }
        else
        {
            destinationCity.setError(null);
            destinationCity.requestFocus();
            return true;
        }
    }
    private boolean validateName()
    {
        name =destinationName.getText().toString().trim();
        if(name.isEmpty())
        {
            destinationName.setError("Field must not be empty");
            destinationName.requestFocus();

            return false;
        }
        else
        {
            destinationName.setError(null);

            destinationName.requestFocus();
            return true;
        }
    }

    private void submitDestDetails()
    {
      if(!validateCity() | !validateName())
      {
          return;
      }
      else
      {
          progressDialog.setTitle("Assign Destination");
          progressDialog.setMessage("Please wait while checking credentials");
          progressDialog.setCanceledOnTouchOutside(false);
          progressDialog.show();

          DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destination").push();
          String destID= reference.getKey();

          DestinationDetails details =new DestinationDetails(destID,name,city.toUpperCase(),type);

          reference.setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful())
                  {
                      Toast.makeText(getContext(),type+ " added successfully", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(getContext(), ViewDestinations.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);
                      progressDialog.dismiss();
                  }
                  else
                  {
                      Toast.makeText(getContext(), "error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      progressDialog.dismiss();
                  }
              }
          });

      }

    }
}