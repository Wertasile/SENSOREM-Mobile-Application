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
import android.widget.TextView;

public class HumiditySensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView humidityresult;
    private Sensor humiditysensor;
    private boolean isHumiditysensoravailable;

    private Boolean recordingdata;
    private int result;

    private TextView datarefresh; int seconds = 0;

    private TextView startrecord,stoprecord,resetrecord; //Start, Stop, Reset Buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_sensor);
        getWindow().setStatusBarColor(ContextCompat.getColor(HumiditySensor.this,R.color.black));
        humidityresult=findViewById(R.id.humidity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //sensorManager object created to access all sensors in device

        datarefresh= (TextView) findViewById(R.id.data_refresh);
        datarefresh.setMovementMethod(new ScrollingMovementMethod());

        startrecord = (Button) findViewById(R.id.start_button);
        stoprecord = (Button) findViewById(R.id.stop_button);
        resetrecord = (Button) findViewById(R.id.reset_button);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)!=null){

            humiditysensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditysensoravailable = true;
        }else{
            humidityresult.setText("HUMIDITY SENSOR NOT AVAILABLE");
            isHumiditysensoravailable = false;
        }

        startrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordingdata = true;
                datarefresh.setText("HUMIDITY TIME(s)");
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
            datarefresh.setText(datarefresh.getText() + "\n" + "  " + result + "%           " + seconds);
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
    public void onSensorChanged(SensorEvent sensorEvent) {
        result = (int) sensorEvent.values[0];
        humidityresult.setText("HUMIDITY" + sensorEvent.values[0]+" %");   //If any changes are sensed, this runs



    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this,humiditysensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);


    }


    }
