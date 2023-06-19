package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
public class GeoMagneticActivity extends AppCompatActivity implements SensorEventListener {
    private TextView xaxis,yaxis,zaxis;
    private Sensor magsensor; private SensorManager sensorManager; private boolean ismagsensoravailable;
    private RadioButton tesla, microstesla, gauss;
    private TextView datarefresh; int seconds = 0;
    private TextView startrecord,stoprecord,resetrecord; private Boolean recordingdata;
    private float x,y,z,newx,newy,newz;
    private double dubx,duby,dubz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_magnetic);
        getWindow().setStatusBarColor(ContextCompat.getColor(GeoMagneticActivity.this,R.color.black));

        xaxis=findViewById(R.id.x_axis); yaxis=findViewById(R.id.y_axis); zaxis=findViewById(R.id.z_axis);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tesla=findViewById(R.id.radioButton5);
        microstesla = findViewById(R.id.radioButton4);
        gauss = findViewById(R.id.radioButton6);

        datarefresh= (TextView) findViewById(R.id.data_refresh);
        datarefresh.setMovementMethod(new ScrollingMovementMethod());

        startrecord = (Button) findViewById(R.id.start_button);
        stoprecord = (Button) findViewById(R.id.stop_button);
        resetrecord = (Button) findViewById(R.id.reset_button);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!=null){
            magsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            ismagsensoravailable=true;
        }else{
            xaxis.setText("SENSOR NOT AVAILABLE");
            ismagsensoravailable=false;
        }

        startrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordingdata = true;
                datarefresh.setText("MAG DATA TIME(s)");
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
            datarefresh.setText(datarefresh.getText() + "\n x=" + "  " + newx + "        " + "\n y= "+newy + "                 " + seconds+ "s" + "\n z =" + newz + "      " + "\n  ");
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
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (gauss.isChecked()){
            x = (float) (sensorEvent.values[0]*0.01);
            y = (float) (sensorEvent.values[1]*0.01);
            z = (float) (sensorEvent.values[2]*0.01);
            xaxis.setText("X-AXIS: "+x+"G");
            yaxis.setText("Y-AXIS: "+y+"G");
            zaxis.setText("Z-AXIS: "+z+"G");
            newx = (int) x; newy = (int) y; newz = (int) z;
        } else if (microstesla.isChecked()) {
            x = (float) (sensorEvent.values[0]);
            y = (float) (sensorEvent.values[1]);
            z = (float) (sensorEvent.values[2]);
            xaxis.setText("X-AXIS: "+sensorEvent.values[0]+"μT");
            yaxis.setText("Y-AXIS: "+sensorEvent.values[1]+"μT");
            zaxis.setText("Z-AXIS: "+sensorEvent.values[2]+"μT");
            newx = (int) x; newy = (int) y; newz = (int) z;
        }else if (tesla.isChecked()) {
            dubx = (double) (sensorEvent.values[0]*0.000001);
            duby = (double) (sensorEvent.values[1]*0.000001);
            dubz = (double) (sensorEvent.values[2]*0.000001);
            xaxis.setText("X-AXIS: " + dubx + "T");
            yaxis.setText("Y-AXIS: " + duby + "T");
            zaxis.setText("Z-AXIS: " + dubz + "T");
            newx = (int) x; newy = (int) y; newz = (int) z;
        }else{
            x = (float) (sensorEvent.values[0]);
            y = (float) (sensorEvent.values[1]);
            z = (float) (sensorEvent.values[2]);
            xaxis.setText("X-AXIS: "+sensorEvent.values[0]+"μT");
            yaxis.setText("Y-AXIS: "+sensorEvent.values[1]+"μT");
            zaxis.setText("Z-AXIS: "+sensorEvent.values[2]+"μT");
            newx = (int) x; newy = (int) y; newz = (int) z;
        }






    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,magsensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}