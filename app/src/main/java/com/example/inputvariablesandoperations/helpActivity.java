package com.example.inputvariablesandoperations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class helpActivity extends AppCompatActivity {
    private TextView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getWindow().setStatusBarColor(ContextCompat.getColor(helpActivity.this,R.color.black));

        help = findViewById(R.id.help_text);
        help.setMovementMethod(new ScrollingMovementMethod());

        help.setText("1. The Device Information Icon can be clicked to view Device Information such as Wifi Connection, Device Battery Status and Device Build,Model and Manufacturer." + "\n" + "\n" +
                "2. The Sensor List Icon can be clicked to display all sensors available on your device." + "\n" + "\n" +
                "3. Spinner below the Application name can be used to Select Types of Sensor to Access or Enter Compass page" + "\n" + "\n" +
                "4. On Clicking any Option from the spinner such as ENVIRONMENT SENSOR user is taken to the ENVIRONMENT SENSOR PAGE where user can select required SENSOR." +"\n" + "\n" +
                "5. On Clicking START BUTTON, Sensor Data Shown per second is recorded and displayed. STOP button stops this process." + "\n" + "\n" +
                "6. RESET button clears all the recorded data on your screen."
        );




    }
}