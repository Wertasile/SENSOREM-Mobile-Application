package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MotionActivity extends AppCompatActivity {
    private Spinner spinner;
    private List<String> items; private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);
        getWindow().setStatusBarColor(ContextCompat.getColor(MotionActivity.this,R.color.black));
        spinner=findViewById(R.id.spinner4);

        //spinner items
        List sensorList = new ArrayList<String>();

        List<Sensor> sensors = ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ALL);
        sensorList.add(0,"CHOOSE:");
        for (Sensor i : sensors){
            if(i.getName().contains("Step Counter")){
                sensorList.add(i.getName());
            }else if(i.getName().contains("Accelerometer")){
                sensorList.add(i.getName());
            }else if(i.getName().contains("Gyroscope")) {
                sensorList.add(i.getName());
            }else if(i.getName().contains("Gravity")) {
                sensorList.add(i.getName());
            }else if(i.getName().equals("Rotation Vector Sensor")) {
                sensorList.add(i.getName());
            }

        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);


        spinner.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,sensorList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("CHOOSE:")) {
                    //do nothing

                }
                else {
                    String item = spinner.getSelectedItem().toString();


                    if (spinner.getSelectedItem().toString().contains("Accelerometer")) {
                        Intent intent = new Intent(MotionActivity.this, AccelorometerActivity.class);
                        startActivity(intent);
                    } else if (spinner.getSelectedItem().toString().contains("Step")) {
                        Intent intent = new Intent(MotionActivity.this, StepCounterActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}