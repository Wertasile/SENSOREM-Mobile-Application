package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class AccelorometerActivity extends AppCompatActivity implements SensorEventListener {
    private TextView xaxis,yaxis,zaxis;
    private SensorManager sensorManager;
    private TextView datarefresh; int seconds = 0;
    private TextView startrecord,stoprecord,resetrecord;
    private Boolean recordingdata;
    Vibrator v;
    private Sensor accsensor;
    private float currentx,currenty,currentz;
    private float prevx,prevy,prevz;
    private float x,y,z;
    private int newx,newy,newz;
    private float diffx,diffy,diffz;
    private Boolean isaccsensoravailable;
    private float shaketrigger = 3f;
    private RadioButton ft,m;
    private boolean ifcurrentdataalreadyrecorded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelorometer);
        getWindow().setStatusBarColor(ContextCompat.getColor(AccelorometerActivity.this,R.color.black));

        xaxis=findViewById(R.id.x_axis);
        yaxis=findViewById(R.id.y_axis);
        zaxis=findViewById(R.id.z_axis);

        datarefresh= (TextView) findViewById(R.id.data_refresh);
        datarefresh.setMovementMethod(new ScrollingMovementMethod());

        startrecord = (Button) findViewById(R.id.start_button);
        stoprecord = (Button) findViewById(R.id.stop_button);
        resetrecord = (Button) findViewById(R.id.reset_button);

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        m=findViewById(R.id.radioButton4);
        ft = findViewById(R.id.radioButton5);

        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            accsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isaccsensoravailable=true;
        }else{
            xaxis.setText("SENSOR NOT AVAILABLE");
            isaccsensoravailable=false;
        }

        startrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordingdata = true;
                datarefresh.setText("ACC DATA TIME(s)");
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

        if (ft.isChecked()){
            x = (float) (sensorEvent.values[0]*3.28084);
            y = (float) (sensorEvent.values[1]*3.28084);
            z = (float) (sensorEvent.values[2]*3.28084);
            newx = (int) x; newy = (int) y; newz = (int) z;
            xaxis.setText("X-AXIS: "+x+"ft/s^2");
            yaxis.setText("Y-AXIS: "+y+"ft/s^2");
            zaxis.setText("Z-AXIS: "+z+"ft/s^2");
        } else if (m.isChecked()) {
            x = (float) (sensorEvent.values[0]); y = (float) (sensorEvent.values[1]); z = (float) (sensorEvent.values[2]);
            newx = (int) x; newy = (int) y; newz = (int) z;
            xaxis.setText("X-AXIS: "+sensorEvent.values[0]+"m/s^2");
            yaxis.setText("Y-AXIS: "+sensorEvent.values[1]+"m/s^2");
            zaxis.setText("Z-AXIS: "+sensorEvent.values[2]+"m/s^2");
        } else{
            x = (float) (sensorEvent.values[0]); y = (float) (sensorEvent.values[1]);z = (float) (sensorEvent.values[2]);
            newx = (int) x; newy = (int) y; newz = (int) z;
            xaxis.setText("X-AXIS: "+sensorEvent.values[0]+"m/s^2");
            yaxis.setText("Y-AXIS: "+sensorEvent.values[1]+"m/s^2");
            zaxis.setText("Z-AXIS: "+sensorEvent.values[2]+"m/s^2");
        }


        currentx=sensorEvent.values[0];
        currenty=sensorEvent.values[1];
        currentz=sensorEvent.values[2];

        if (ifcurrentdataalreadyrecorded){
            diffx = Math.abs(prevx-currentx);
            diffy = Math.abs(prevy-currenty);
            diffz = Math.abs(prevz-currentz);

            if ((diffx > shaketrigger && diffy > shaketrigger) || (diffy > shaketrigger && diffz> shaketrigger)
                    || (diffx > shaketrigger && diffz > shaketrigger) || (diffx > shaketrigger && diffy > shaketrigger)){
                if (Build.VERSION.SDK_INT >= 26){
                    v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));

                }else{
                    v.vibrate(500);
                }

            }
        }

        prevx = currentx;
        prevy = currenty;
        prevz = currentz;

        ifcurrentdataalreadyrecorded=true;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accsensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}