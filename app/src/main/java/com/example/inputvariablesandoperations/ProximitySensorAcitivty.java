package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class ProximitySensorAcitivty extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView proxresult,proxinfo;
    private Sensor proxsensor;
    private Boolean isProxsensoravailable;

    private float maxrange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor_acitivty);
        getWindow().setStatusBarColor(ContextCompat.getColor(ProximitySensorAcitivty.this,R.color.black));

        proxresult = findViewById(R.id.prox_data);
        proxinfo = findViewById(R.id.prox_info);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!=null){

            proxsensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isProxsensoravailable = true;
        }else{
            proxresult.setText("PROXIMITY SENSOR NOT AVAILABLE");
            isProxsensoravailable = false;
        }

        maxrange =  proxsensor.getMaximumRange();


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] < maxrange){
            proxinfo.setText("NEARBY");
            proxresult.setText("<" + maxrange + "cm");
        }else{
            proxinfo.setText("FAR");
            proxresult.setText(">" + maxrange + "cm");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,proxsensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}