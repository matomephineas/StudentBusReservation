package com.example.studentbusreservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentbusreservation.Admin.AdminMainActivity;
import com.example.studentbusreservation.MODELS.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class LoginRegisterPage extends AppCompatActivity {

    private RelativeLayout login_relative,register_relative;
    private EditText regfirstname,regemail,regphone,regpassword,regconfirmpassword,username,logpassword,regIdenty;
    private String userID,fullnames,selectedCampusName,cityName,email,password,phone,confirmpassword,identity;
    private Button login;
    MaterialSpinner campus,citySpinner;
    String[] city={"Polokwane","Pretoria"};
    FirebaseAuth mAuth;
    FirebaseUser user;

    private  ProgressDialog progressDialog;
    ArrayList<String> campusList;
    ArrayAdapter<String> adapter;
    ArrayList<String> cityList;
    ArrayAdapter<String> adapter2;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_page);
        register_relative = findViewById(R.id.register_relative);
        login_relative = findViewById(R.id.login_relative);
        regfirstname =findViewById(R.id.regfirstname);
        regIdenty = findViewById(R.id.regIdenty);
        citySpinner =findViewById(R.id.city);

        regemail =findViewById(R.id.regemail);
        regphone =findViewById(R.id.regphone);
        regpassword =findViewById(R.id.regpassword);
        regconfirmpassword =findViewById(R.id.regconfirmpassword);
        campus = findViewById(R.id.campus);
        //login
        username =findViewById(R.id.username);
        logpassword =findViewById(R.id.password);
        login =findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginUser();
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
                    campusList.add(item.child("name").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        campus.setAdapter(adapter);

        cityList = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, city);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(adapter2);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                cityName = adapterView.getItemAtPosition(position).toString();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void btnLoginUser()
    {
        String Username =username.getText().toString().toString().trim();
        String Password = logpassword.getText().toString().toString().trim();

        if(Username.isEmpty() || Password.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "fields must not be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setTitle("Login User");
            progressDialog.setMessage("Please wait while checking your credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

           mAuth.signInWithEmailAndPassword(Username,Password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task)
                       {
                          if(task.isSuccessful())
                          {

                              String usertype =user.getUid();
                              checkUserType(usertype);


                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(), "error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                          }

                       }
                   });


        }

    }

    private void checkUserType(String usertype)
    {
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(usertype);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Users userProfile = snapshot.getValue(Users.class);
                    //assert userProfile != null;
                    int userType = (userProfile.getUsertype());

                    switch (userType) {
                        case 1:
                            startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                            progressDialog.dismiss();
                            break;
                        case 2:
                            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                            progressDialog.dismiss();
                            break;

                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "User: "+ email +" does not exits", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void btnRegisterUser(View view)
    {
        fullnames = regfirstname.getText().toString().toString().trim();
        identity = regIdenty.getText().toString().toString().trim();
        email = regemail.getText().toString().toString().trim();;
        password = regpassword.getText().toString().toString().trim();;
        phone = regphone.getText().toString().toString().trim();;
        confirmpassword =regconfirmpassword.getText().toString().trim();

        if(fullnames.isEmpty()|| email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || identity.isEmpty()){
            Toast.makeText(getApplicationContext(), "filed must not be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(password.equals(confirmpassword))
            {
                progressDialog.setTitle("Register User");
                progressDialog.setMessage("Please wait while checking your credentials");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

               mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if(task.isSuccessful())
                       {
                           DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                           String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                           Users users = new Users(id,fullnames,email,phone,password,selectedCampusName,cityName.toUpperCase(),identity,1);

                           reference.child(id).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task)
                               {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                                    login_relative.setVisibility(View.VISIBLE);
                                    register_relative.setVisibility(View.GONE);

                                    progressDialog.dismiss();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "An error occured "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                               }
                           });

                       }
                       else
                       {
                           Toast.makeText(getApplicationContext(), "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           progressDialog.dismiss();
                       }
                   }
               });
            }
            else
            {
                Toast.makeText(getApplicationContext(), "passwords does not match", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void sendToLogin(View view)
    {
        register_relative.setVisibility(View.INVISIBLE);
        login_relative.setVisibility(View.VISIBLE);
    }
    public void sendUserToRegisterPage(View view)
    {
        register_relative.setVisibility(View.VISIBLE);
        login_relative.setVisibility(View.INVISIBLE);
    }

    public void sendToAdmin(View view)
    {
        //startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
    }
}