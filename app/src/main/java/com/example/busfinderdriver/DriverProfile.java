package com.example.busfinderdriver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DriverProfile extends AppCompatActivity{


    TextInputLayout fullname,email,password,phone;
    TextView fullnameLabel;
    DatabaseReference reference;
    FirebaseAuth auth;
    String _PASSWORD,_NAME,_PHONE,_EMAIL, userID;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        reference = FirebaseDatabase.getInstance().getReference("driver");

        fullname=findViewById(R.id.fullname_profile);
        email=findViewById(R.id.email_profile);
        password=findViewById(R.id.password_profile);
        phone=findViewById(R.id.phone_profile);
        fullnameLabel=findViewById(R.id.fullname_field);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //showAllDriverData();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userProfile = snapshot.getValue(UserHelperClass.class);
                String fullName = userProfile.name;
                String Email = userProfile.email;
                String Phone = userProfile.phone;
                String Password = userProfile.password;

                fullname.getEditText().setText(fullName);
                fullnameLabel.setText(fullName);
                email.getEditText().setText(Email);
                phone.getEditText().setText(Phone);
                password.getEditText().setText(Password);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DriverProfile.this,"Something wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DriverProfile.this.finish();
    }

    private void showAllDriverData() {

        Intent intent =getIntent();
         String _NAME = intent.getStringExtra("name");
        String _EMAIL = intent.getStringExtra("email");
        String _PHONE = intent.getStringExtra("phone");
        String _PASSWORD = intent.getStringExtra("password");


        fullname.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phone.getEditText().setText(_PHONE);
        password.getEditText().setText(_PASSWORD);

    }

    public void update(View view) {

        if(isNameChanged() || isPasswordChanged()){
            Toast.makeText(this, "Your Data Has Been Updated", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();

    }

    private boolean isPasswordChanged() {
        if(!_PASSWORD.equals(password.getEditText().getText().toString()))
        {
            reference.child(_NAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD=password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!_NAME.equals(fullname.getEditText().getText().toString())){
            reference.child(_NAME).child("name").setValue(fullname.getEditText().getText().toString());
            _NAME=fullname.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();

    }


    public void showlocation(View view) {
        Intent intent = new Intent(DriverProfile.this,DriverLocation.class);
        startActivity(intent);
        finish();
    }

    public void selectRoute(View view) {
        Intent intent = new Intent(DriverProfile.this,SelectedRoad.class);
        startActivity(intent);
        finish();
    }
}