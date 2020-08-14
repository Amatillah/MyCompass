package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener{
    private ImageView compassImg;
    private TextView compassTextV;
    private double rotationValue;
    private Button compassButton;
    private ConstraintLayout compassLayout;
    private Button backB;

    private SensorManager sensorManager;
    private Sensor sensorAcceleromter;
    private Sensor sensorMagneticField;


    private float [] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];

    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    private Vibrator vibrator;
    private long[] vibratorPattern;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        compassLayout = findViewById(R.id.compassLayout);


        compassTextV = findViewById(R.id.compassValue);
        compassImg = findViewById(R.id.compImg);
        compassButton = findViewById(R.id.backAcc);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        backB = findViewById(R.id.backAcc);

        backB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        sensorAcceleromter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this,sensorAcceleromter,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorMagneticField,SensorManager.SENSOR_DELAY_NORMAL);

        sensorAcceleromter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibratorPattern = new long[]{0, 50}; //sleep 0 ms, vibrate 50 ms
        rotationValue = 180;




    }

    public void ResetButton(View view){
        compassImg.setRotation(180);
    }

    public void setRotation(float rotate){
        compassImg.setRotation(rotate);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        sensorManager.unregisterListener(this);
        rotationValue = 180;
        super.onStop();
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;

        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(floatRotationMatrix, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(floatRotationMatrix, floatOrientation);
            rotationValue = (Math.toDegrees(SensorManager.getOrientation(floatRotationMatrix, floatOrientation)[0]) + 360) % 360;
        }
        if( (15 >= rotationValue) && (rotationValue >=0) ||((345 <= rotationValue) && (360 >= rotationValue))){
            vibrator.vibrate(vibratorPattern,-1);
            compassLayout.setBackgroundColor(Color.rgb(228, 222,162));


        }
        else
            compassLayout.setBackgroundColor(Color.rgb(255, 255,255));

        rotationValue = Math.round(rotationValue);
        compassImg.setRotation((float) -rotationValue);
        compassTextV.setText(new StringBuilder().append(rotationValue).append(" degrees").toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
