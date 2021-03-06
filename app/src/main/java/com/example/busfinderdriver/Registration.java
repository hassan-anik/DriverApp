package com.example.busfinderdriver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity{

    FirebaseAuth fAuth;
    EditText regName, regEmail, regPhone, regPass;
    Button goto_login, regBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        regName = findViewById(R.id.fullname_reg);
        regEmail = findViewById(R.id.email_reg);
        regPhone = findViewById(R.id.phone_reg);
        regPass = findViewById(R.id.password_reg);
        regBtn = findViewById(R.id.signup_btn);
        goto_login = findViewById(R.id.gotologin_btn);
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        if(fAuth.getCurrentUser() != null){
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
        }

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("driver");

                final String email = regEmail.getText().toString().trim();
                final String password = regPass.getText().toString().trim();
                final String phone = regPhone.getText().toString().trim();
                final String fullName = regName.getText().toString().trim();
                if(fullName.isEmpty()){
                    regName.setError("FullName Required");
                    regName.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    regEmail.setError("Email Required");
                    regEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    regEmail.setError("Provide valid email address");
                    regEmail.requestFocus();
                }
                if(password.isEmpty()){
                    regPass.setError("Password Required");
                    regPass.requestFocus();
                    return;
                }
                if(password.length() < 6){
                    regPass.setError("Password length min 6 character");
                    regPass.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    regPhone.setError("Phone No Required");
                    regPhone.requestFocus();
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserHelperClass userHelperClass = new UserHelperClass(fullName,email,phone,password);
                            databaseReference.child(fAuth.getCurrentUser().getUid()).setValue(userHelperClass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registration.this, "record saved", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(Registration.this, "Record not saved", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            Toast.makeText(Registration.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        else{
                            Toast.makeText(Registration.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public void goto_login(View view) {
        Intent intent = new Intent(Registration.this, Login.class);
        startActivity(intent);
    }
}