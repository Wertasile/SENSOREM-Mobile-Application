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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private List<String> items; private SensorManager sensorManager;
    private ImageButton iconinfo; private ImageButton allavailablesensors; private ImageButton help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));

        iconinfo=(ImageButton) this.findViewById(R.id.info_icon);
        allavailablesensors=(ImageButton) this.findViewById(R.id.ImageButton2);
        help=(ImageButton) this.findViewById(R.id.help_icon);

        spinner=findViewById(R.id.spinner);

        //spinner items
        items=new ArrayList<>();
        items.add(0,"CHOOSE:");

        items.add("ENVIRONMENT SENSOR");
        items.add("POSITION SENSOR");
        items.add("MOTION SENSOR");

        items.add("COMPASS");


        //Style and populate the Spinner

        //putting data in spinner
        spinner.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items));

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,helpActivity.class);
                startActivity(intent);
            }
        });

        allavailablesensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllAvailableSensors.class);
                startActivity(intent);
            }
        });
        iconinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PhoneInformationActivity.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("CHOOSE:")) {
                    //do nothing

                }
                else {
                    String item = spinner.getSelectedItem().toString();


                    if (spinner.getSelectedItem().toString().equals("ENVIRONMENT SENSOR")) {
                        Intent intent = new Intent(MainActivity.this, EnvironmentActivity.class);
                        startActivity(intent);
                    } else if (spinner.getSelectedItem().toString().equals("MOTION SENSOR")) {
                        Intent intent = new Intent(MainActivity.this, MotionActivity.class);
                        startActivity(intent);
                    } else if (spinner.getSelectedItem().toString().equals("POSITION SENSOR")) {
                        Intent intent = new Intent(MainActivity.this, PositionActivity.class);
                        startActivity(intent);

                    } else if (spinner.getSelectedItem().toString().equals("COMPASS")) {
                        Intent intent = new Intent(MainActivity.this, CompassActivity.class);
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
