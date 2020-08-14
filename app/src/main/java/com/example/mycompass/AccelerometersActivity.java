package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccelerometersActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor sensorAcceleromter;
    private TextView xTextV, yTextV, zTextV, xRiktningTextV, yRiktningTextV;
    private Button backB;

    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometers);

        xTextV = findViewById(R.id.textView7);
        yTextV = findViewById(R.id.textView8);
        zTextV = findViewById(R.id.textView9);
        xRiktningTextV = findViewById(R.id.xRiktningTextV);
        yRiktningTextV = findViewById(R.id.yRiktningTextV);
        backB = findViewById(R.id.backAcc);

        backB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        //mp = MediaPlayer.create(this, R.raw.alarmsound);




        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);



    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        sensorManager.unregisterListener(this);
        super.onStop();
        startActivity(intent);
    }

    //private final SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float lastValues[] = new float[2];
            String [] direction = {"Riktning i x-led","riktning i y-led"};

            float xChange = lastValues[0] - event.values[0];
            float yChange = lastValues[1] - event.values[1];

            lastValues[0] = event.values[0];
            lastValues[1] = event.values[1];

            if (xChange > 2){
                direction[0] = "VÄNSTER";
                xRiktningTextV.setTextColor(Color.rgb(233,30,99));
                xRiktningTextV.setText("VÄNSTER");
            }
            else if (xChange < -2){
                direction[0] = "HÖGER";
                xRiktningTextV.setTextColor(Color.rgb(33,150,243));
                xRiktningTextV.setText("HÖGER");


            }

            if (yChange > 2){
                direction[1] = "NER";
                yRiktningTextV.setTextColor(Color.rgb(233,30,99));
                yRiktningTextV.setText("NER");


            }
            else if (yChange < -2){
                direction[1] = "UPP";
                yRiktningTextV.setTextColor(Color.rgb(33,150,243));
                yRiktningTextV.setText("UPP");



            }
            if(direction[0]==null){
                xRiktningTextV.setTextColor(Color.rgb(0,0,0));
                yRiktningTextV.setTextColor(Color.rgb(0,0,0));
                xRiktningTextV.setText("Riktning i x-led");
                xRiktningTextV.setText("Riktning i y-led");
            }


            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            x = (float) (Math.round(x*1000.0)/1000.0);
            y = (float) (Math.round(y*1000.0)/1000.0);
            z = (float) (Math.round(z*1000.0)/1000.0);

            xTextV.setText((new StringBuilder().append("X: ").append(x)).toString());
            yTextV.setText((new StringBuilder().append("Y: ").append(y).toString()));
            zTextV.setText((new StringBuilder().append("Z: ").append(z).toString()));
            //riktningTextV.setText(direction[0] + "\n" + direction[1]);

        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    //}




    }

