package com.example.busfinderdriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectedRoad extends AppCompatActivity {
    Button mirpurToUttara, mirpurToBosundhara, mirpurToRampura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_road);
        mirpurToUttara = findViewById(R.id.mirpurToUttara);
        mirpurToRampura= findViewById(R.id.mirpurToRampura);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SelectedRoad.this,DriverProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void mirpurToUttara(View view) {
        Intent intent =  new Intent(SelectedRoad.this,mirpur2uttara.class);
        startActivity(intent);
        finish();
    }

    public void mirpurToBosundhara(View view) {
    }

    public void mirpurToRampura(View view) {
        Intent intent =  new Intent(SelectedRoad.this,mirpur2Rampura.class);
        startActivity(intent);
        finish();
    }
}