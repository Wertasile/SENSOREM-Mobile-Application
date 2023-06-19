package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private ImageView imagecompass;
    private TextView txtazimuth;
    private int azimuth;

    float[] rotationMatrix = new float[9];
    float[] orientation = new float[9];
    private float[] lastaccelerometer = new float[3];
    private float[] lastmagnetometer = new float[3];

    private boolean accelerometervaluealreadyacquired = false;
    private boolean magnetometervaluealreadyacquired = false;

    long lastupdatedtime = 0;
    float currentDegree = 0f;

    private Sensor accsensor, magsensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        getWindow().setStatusBarColor(ContextCompat.getColor(CompassActivity.this,R.color.black));

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        imagecompass = findViewById(R.id.imageView4);
        txtazimuth = findViewById(R.id.txt_azimuth);


        accsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == accsensor){
            System.arraycopy(sensorEvent.values, 0, lastaccelerometer, 0, sensorEvent.values.length);
            accelerometervaluealreadyacquired = true;
        } else if (sensorEvent.sensor == magsensor) {
            System.arraycopy(sensorEvent.values, 0, lastmagnetometer, 0, sensorEvent.values.length);
            magnetometervaluealreadyacquired = true;
        }

        if (accelerometervaluealreadyacquired && magnetometervaluealreadyacquired && System.currentTimeMillis() - lastupdatedtime>250){
            SensorManager.getRotationMatrix(rotationMatrix, null, lastaccelerometer,lastmagnetometer);
            SensorManager.getOrientation(rotationMatrix,orientation);

            float azimuthInRadian = orientation[0];
            float azimuthIndegree = (float) Math.toDegrees(azimuthInRadian);

            RotateAnimation rotateAnimation = new RotateAnimation(currentDegree,-azimuthIndegree,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);
            imagecompass.startAnimation(rotateAnimation);

            currentDegree = -azimuthIndegree;
            lastupdatedtime = System.currentTimeMillis();

            int x = (int) azimuthIndegree;
            if (x<0){
                int newx = 360+x;
                txtazimuth.setText(newx + "°");
            }else{
                txtazimuth.setText(x + "°");
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accsensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,magsensor, SensorManager.SENSOR_DELAY_NORMAL);;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,accsensor);
        sensorManager.unregisterListener(this,magsensor);
    }
}