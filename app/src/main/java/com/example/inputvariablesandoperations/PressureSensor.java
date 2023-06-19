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

public class PressureSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView pressureresult;
    private Sensor pressuresensor;
    private boolean isPressuresensoravailable;

    private double result;

    private RadioButton HECTOPASCALP, BARP , ATMP ;

    private TextView datarefresh;

    private TextView startrecord,stoprecord,resetrecord; private Boolean recordingdata; private int seconds;

    private float hPavalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_sensor);
        getWindow().setStatusBarColor(ContextCompat.getColor(PressureSensor.this,R.color.black));

        pressureresult=findViewById(R.id.pressure_sensor);

        HECTOPASCALP = findViewById(R.id.radioButton);
        BARP = findViewById(R.id.radioButton2);
        ATMP =findViewById(R.id.radioButton3);

        datarefresh= (TextView) findViewById(R.id.data_refresh);
        datarefresh.setMovementMethod(new ScrollingMovementMethod());

        startrecord = (Button) findViewById(R.id.start_button);
        stoprecord = (Button) findViewById(R.id.stop_button);
        resetrecord = (Button) findViewById(R.id.reset_button);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //sensorManager object created to access all sensors in device



        if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)!=null){

            pressuresensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isPressuresensoravailable = true;
        }else{
            pressureresult.setText("PRESSURE SENSOR NOT AVAILABLE");
            isPressuresensoravailable = false;
        }

        startrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordingdata = true;
                datarefresh.setText("PRES TIME(s)");
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


    public void onSensorChanged(SensorEvent sensorEvent) {
        hPavalue= (int) sensorEvent.values[0];
        if (HECTOPASCALP.isChecked()){
            double pres = Double.parseDouble(String.valueOf(hPavalue));
            result= pres;
            String display= String.valueOf(result) + " hPa";
            pressureresult.setText("PRESSURE: " + display);

        }else if (BARP.isChecked()){
            double pres = Double.parseDouble(String.valueOf(hPavalue));
            result= (pres*0.001);
            String display= String.valueOf(result) + " Bar";
            pressureresult.setText("PRESSURE: " + display);

        }else if (ATMP.isChecked()){
            double pres = Double.parseDouble(String.valueOf(hPavalue));
            result= pres*0.000986923;
            String display= String.valueOf(result) + " Atm ";
            pressureresult.setText("PRESSURE: " + display);
        }
        else{
            double pres = Double.parseDouble(String.valueOf(hPavalue));
            result= pres;
            String display= String.valueOf(result) + " hPa";
            pressureresult.setText("PRESSURE: " + display);

        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,pressuresensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}