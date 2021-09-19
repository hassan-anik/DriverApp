package com.example.busfinderdriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText Regemail,Regpass;
    Button forgot_btn,login_btn,sign_up;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Regemail= findViewById(R.id.email_reg);
        Regpass = findViewById(R.id.password_reg);
        forgot_btn =findViewById(R.id.forgot_btn);
        login_btn = findViewById(R.id.login_btn);
        sign_up = findViewById(R.id.sign_up);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),DriverProfile.class));
            finish();
        }          


    }

    public void LoginButton(View view) {
        userLogIn();
    }


    private void userLogIn() {
        String email = Regemail.getText().toString().trim();
        String password = Regpass.getText().toString().trim();
        if(email.isEmpty()){
            Regemail.setError("email required");
            Regemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Regemail.setError("Provide valid email");
            Regemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Regpass.setError("Password Required");
            Regpass.requestFocus();
            return;
        }

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(task.isSuccessful()){
                    Intent intent = new Intent(Login.this, DriverProfile.class);
                    startActivity(intent);
                    if(user.isEmailVerified()){
                        startActivity(new Intent(getApplicationContext(),DriverProfile.class));

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Check email !!", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(Login.this, "Error!!!!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void SignUp(View view) {
        Intent intent=new Intent(Login.this,Registration.class);
        startActivity(intent);
    }
}