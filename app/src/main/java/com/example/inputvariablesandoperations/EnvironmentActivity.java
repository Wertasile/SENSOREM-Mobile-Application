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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class EnvironmentActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Spinner spinner; private List<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        getWindow().setStatusBarColor(ContextCompat.getColor(EnvironmentActivity.this,R.color.black));

        spinner=findViewById(R.id.spinner2);

        List sensorList = new ArrayList<String>();

        List<Sensor> sensors = ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ALL);
        sensorList.add(0,"CHOOSE:");
        for (Sensor i : sensors){
            if(i.getName().contains("Temperature")){
                sensorList.add(i.getName());
            }else if(i.getName().contains("Humidity")){
                sensorList.add(i.getName());
            }else if(i.getName().contains("Pressure")) {
                sensorList.add(i.getName());
            }else if(i.getName().contains("Light")){
                sensorList.add(i.getName());
            }

        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //spinner items


        spinner.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,sensorList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("CHOOSE:")) {
                    //do nothing
                }
                else {
                    String item = spinner.getSelectedItem().toString();

                    if (spinner.getSelectedItem().toString().contains("Temperature")) {
                        Intent intent = new Intent(EnvironmentActivity.this, TemperatureSensor.class);
                        startActivity(intent);
                    } else if (spinner.getSelectedItem().toString().contains("Humidity")) {
                        Intent intent = new Intent(EnvironmentActivity.this, HumiditySensor.class);
                        startActivity(intent);
                    } else if (spinner.getSelectedItem().toString().contains("Pressure")) {
                        Intent intent = new Intent(EnvironmentActivity.this, PressureSensor.class);
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