package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class AllAvailableSensors extends AppCompatActivity {
    private TextView textView;private SensorManager sensorManager;private List<Sensor> sensors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_available_sensors);
        getWindow().setStatusBarColor(ContextCompat.getColor(AllAvailableSensors.this,R.color.black));
        textView=findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        displaysensors();
    }

    private void displaysensors() {
        for (Sensor i:sensors){
            textView.setText(textView.getText()+"\n"+i.getName());
        }
    }
}