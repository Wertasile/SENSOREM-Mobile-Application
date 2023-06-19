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
public class PositionActivity extends AppCompatActivity {
    private Spinner spinner; private List<String> items; private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        getWindow().setStatusBarColor(ContextCompat.getColor(PositionActivity.this,R.color.black));

        spinner=findViewById(R.id.spinner3);

        List sensorList = new ArrayList<String>();

        List<Sensor> sensors = ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ALL);
        sensorList.add(0,"CHOOSE:");
        for (Sensor i : sensors){
            if(i.getName().contains("Magnetic")){
                sensorList.add(i.getName());
            }else if(i.getName().contains("Proximity")){
                sensorList.add(i.getName());
            }else if(i.getName().contains("Orientation")) {
                sensorList.add(i.getName());
            }else if(i.getName().contains("Game")) {
                sensorList.add(i.getName());
            }else if(i.getName().contains("GeoMag")) {
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

                    if (spinner.getSelectedItem().toString().contains("Magnetic")) {
                        Intent intent = new Intent(PositionActivity.this, GeoMagneticActivity.class);
                        startActivity(intent);
                    } else if (spinner.getSelectedItem().toString().contains("Proximity")) {
                        Intent intent = new Intent(PositionActivity.this, ProximitySensorAcitivty.class);
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