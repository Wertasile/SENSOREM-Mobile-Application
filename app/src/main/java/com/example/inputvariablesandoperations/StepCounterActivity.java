package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {
    private TextView StepCounter;
    private SensorManager sensorManager;
    private Boolean isstepsensoravailable;
    private Sensor stepsensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        getWindow().setStatusBarColor(ContextCompat.getColor(StepCounterActivity.this,R.color.black));

        StepCounter = findViewById(R.id.step_counter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){

            stepsensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isstepsensoravailable = true;
        }else{
            StepCounter.setText("STEP COUNTER NOT AVAILABLE");
            isstepsensoravailable = false;
        }


    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == stepsensor ){
            StepCounter.setText(String.valueOf((int)sensorEvent.values[0]) );
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,stepsensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}