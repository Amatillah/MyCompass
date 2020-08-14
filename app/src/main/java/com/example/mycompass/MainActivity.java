package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String ROTATION_VALUE = "";
    private Button buttonAcc;
    private Button buttonComp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAcc = findViewById(R.id.buttonAcc);
        buttonComp = findViewById(R.id.backAcc);




        buttonAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openAcceleromtersActivity();
            }
        });
        buttonComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCompassActivity();

            }
        });


    }


    public void openCompassActivity() {
        Intent intent = new Intent(this, CompassActivity.class);
        //intent.putExtra(ROTATION_VALUE, (int)rotationValue);
        startActivity(intent);
    }

    public void openAcceleromtersActivity() {
        Intent intent = new Intent(this, AccelerometersActivity.class);
        startActivity(intent);
    }


}



