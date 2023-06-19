package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.widget.TextView;

public class PhoneInformationActivity extends AppCompatActivity {

    private TextView batteryinfo, healthinfo, chargingstatusinfo, tempinfo, voltageinfo;
    private TextView MMMinfo;
    private BroadcastReceiver batteryBR;
    private IntentFilter intentFilter;

    private boolean WiFiConnected=false;private boolean MobileConnected=false;private TextView WiFiinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_information);
        getWindow().setStatusBarColor(ContextCompat.getColor(PhoneInformationActivity.this,R.color.black));
        MMMinfo=findViewById(R.id.MMM_info);
        checkinfo();

        WiFiinfo=findViewById(R.id.WiFi_info);
        checkNet();

        batteryinfo= findViewById(R.id.battery_info);
        healthinfo =findViewById(R.id.health_info);
        voltageinfo =findViewById(R.id.voltage_info);
        chargingstatusinfo =findViewById(R.id.chargingstatus_info);
        tempinfo =findViewById(R.id.temp_info);

        INFandBBR();
    }



    private void checkNet() {
        //Creating ConnectivityManager Object CM
        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); //CONNECTIVITY_SERVICE --> with getSystemService to retrieve a ConnectivityManager for handling management of network connections.
        //Creating NetworkInfo Object NI and use ConnectivityManager Object to get Network Info.
        NetworkInfo NI = CM.getActiveNetworkInfo();

        if (NI!=null && NI.isConnected()){
            WiFiConnected = NI.getType() == ConnectivityManager.TYPE_WIFI;
            MobileConnected= NI.getType() == ConnectivityManager.TYPE_MOBILE;
            if (WiFiConnected){
                WiFiinfo.setText("CONNECTED TO WIFI");
            }
            else if (MobileConnected) {
                WiFiinfo.setText("CONNECTED TO MOBILE DATA");
            }
        }
        else{
            WiFiinfo.setText("NOT CONNECTED");

        }
    }

    private void checkinfo() {
        String stringbuildermodel = "MODEL:" + Build.MODEL +
                "\nMANUFACTURER:" + Build.MANUFACTURER +
                "\nDEVICE:" + Build.DEVICE;

        MMMinfo.setText(stringbuildermodel);
    }

    private void INFandBBR(){
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                    batteryinfo.setText(String.valueOf(intent.getIntExtra("level",0))+"%");

                    float temp = (float) (intent.getIntExtra("temperature",-1)/10);
                    tempinfo.setText(temp+" °C");

                    float voltage = (float) (intent.getIntExtra("voltage",0)*0.001);
                    voltageinfo.setText(voltage+"V");

                    int health = intent.getIntExtra("health",0);
                    switch (health){
                        case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                            healthinfo.setText("UNKNOWN");
                            break;
                        case BatteryManager.BATTERY_HEALTH_GOOD:
                            healthinfo.setText("GOOD");
                            break;
                        case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                            healthinfo.setText("FAILURE");
                            break;
                        case BatteryManager.BATTERY_HEALTH_COLD:
                            healthinfo.setText("COLD");
                            break;
                        case BatteryManager.BATTERY_HEALTH_DEAD:
                            healthinfo.setText("DEAD");
                            break;
                        case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                            healthinfo.setText("OVER VOLTAGE");
                            break;
                    }


                    int status = intent.getIntExtra("status",-1);
                    switch (status) {
                        case BatteryManager.BATTERY_STATUS_CHARGING:
                            chargingstatusinfo.setText("CHARGING");
                            break;
                        case BatteryManager.BATTERY_STATUS_DISCHARGING:
                            healthinfo.setText("DISCHARGING");
                            break;
                        case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                            healthinfo.setText("NOT CHARGING");
                            break;
                        case BatteryManager.BATTERY_STATUS_FULL:
                            healthinfo.setText("FULL");
                            break;
                        case BatteryManager.BATTERY_STATUS_UNKNOWN:
                            healthinfo.setText("UNKNOWN");
                            break;
                        default:
                            healthinfo.setText(null);
                    }


                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryBR, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryBR);
    }
}