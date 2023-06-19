package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class TemperatureSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView tempresult; private Sensor tempsensor; private double celciusvalue; private double result;
    private TextView datarefresh; int seconds = 0;
    private TextView startrecord,stoprecord,resetrecord; //Start, Stop, Reset Buttons
    private RadioButton CELCIUS; private RadioButton FARENHEIT; private RadioButton KELVIN;
    private Boolean recordingdata;

    private Boolean isTempsensoravailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_sensor);
        getWindow().setStatusBarColor(ContextCompat.getColor(TemperatureSensor.this,R.color.black));
        tempresult=findViewById(R.id.temp_sensor);

        CELCIUS = findViewById(R.id.radioButton);
        FARENHEIT = findViewById(R.id.radioButton2);
        KELVIN=findViewById(R.id.radioButton3);

        datarefresh= (TextView) findViewById(R.id.data_refresh);
        datarefresh.setMovementMethod(new ScrollingMovementMethod());

        startrecord = (Button) findViewById(R.id.start_button);
        stoprecord = (Button) findViewById(R.id.stop_button);
        resetrecord = (Button) findViewById(R.id.reset_button);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //sensorManager object created to access all sensors in device

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){

            tempsensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTempsensoravailable = true;
        }else{
            tempresult.setText("TEMP SENSOR NOT AVAILABLE");
            isTempsensoravailable = false;
        }

        startrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordingdata = true;
                datarefresh.setText(" TIME(s)");
                content();
            }
        });

        stoprecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordingdata=false;
            }
        });

        resetrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datarefresh.setText(null);
                seconds = 0;
            }
        });

    }

    public void content(){

        if(seconds>0) {
            datarefresh.setText(datarefresh.getText() + "\n" + "  " + result + "        " + seconds);
        }
        seconds++;

        if(recordingdata){
            refresh(1000);
        }
    }

    private void refresh(int ms){
        final Handler handler = new Handler ();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        handler.postDelayed(runnable,ms);
    }
    @Override
    //code to get value of sensor
    public void onSensorChanged(SensorEvent sensorEvent) { //If any changes are sensed, this runs
        celciusvalue= (int) sensorEvent.values[0];
        if (CELCIUS.isChecked()){
            double temp = Double.parseDouble(String.valueOf(celciusvalue));
            result= temp;
            String display= String.valueOf(result) + " °C";
            tempresult.setText("TEMPERATURE DETECTED" + display);

        }else if (FARENHEIT.isChecked()){
            double temp = Double.parseDouble(String.valueOf(celciusvalue));
            result= (temp*1.8)+32;
            String display= String.valueOf(result) + " F";
            tempresult.setText("TEMPERATURE DETECTED" + display);
        }
        else if (KELVIN.isChecked()){
            double temp = Double.parseDouble(String.valueOf(celciusvalue));
            result= temp+273.15;
            String display= String.valueOf(result) + " K";
            tempresult.setText("TEMPERATURE DETECTED" + display);
        }
        else{
            double temp = Double.parseDouble(String.valueOf(celciusvalue));
            result= temp;
            String display= String.valueOf(result) + " °C";
            tempresult.setText("TEMPERATURE DETECTED" + display);

        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,tempsensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);


    }
}