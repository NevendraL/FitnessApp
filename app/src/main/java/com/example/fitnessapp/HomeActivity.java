package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    private TextView stepsTakenTextView;
    private TextView distanceTraveledTextView;
    private SensorManager sensorManager;
    private Boolean running = false;
    private UserModel userModel;
    private Button officeMode;
    private String stepsTaken;
    private TextView congratsFeedBackText;
    private DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stepsTakenTextView = findViewById(R.id.stepsTakenTextView);
        congratsFeedBackText = findViewById(R.id.congratsFeedBackText);
        distanceTraveledTextView = findViewById(R.id.distanceTraveledTextView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mDatabaseHelper = new DatabaseHelper(this);
        officeMode = findViewById(R.id.offceModeButton);

        officeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when this is activated users get notification every hour to walk if they are in the office..
                //starts the service
                startService(new Intent(getApplicationContext(), BackgroundService.class));
                Toast.makeText(HomeActivity.this, "Office mode activated ", Toast.LENGTH_SHORT).show();


            }
        });

    }

    //continue sensor once the app is active again
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            //set listener on the sensor
        } else {
            //display message if the user's device does not have a sensor
            stepsTakenTextView.setText(getString(R.string.no_sensor));
        }

    }

    //disable sensor when the app is in the background to save battery
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            //if the sensor is running save the steps taken
            float stepsTaken = event.values[0];
            //convert steps taken to distance..
            double distanceInFeet = stepsTaken * 2.5;

            //for every 1000 steps provide a feedback to the user...
            for (float i = stepsTaken; i < stepsTaken; i++) {
                if (i % 1000 == 0) {
                    congratsFeedBackText.setText(" Congrats you have walked a total of " + stepsTaken + " Steps + ");
                }

            }
            //populate user model with steps taken and distance in feet..
            userModel = new UserModel(stepsTaken, distanceInFeet);
            updateUi();

        }

    }

    //updates ui with distance and steps taken
    public void updateUi() {
        distanceTraveledTextView.setText(String.valueOf("Distance in feet: " + userModel.getDistanceTraveled()));
        stepsTakenTextView.setText(String.valueOf("Steps: " + userModel.getUserStepsTaken()));
        //saves data into sql database here
        saveData();
    }

    //Saves step taken to SQLite database
    public void saveData() {
        float stepsTaken = userModel.getUserStepsTaken();
        if (stepsTakenTextView.length() != 0) {
            addDataDb(stepsTaken + "");
            Log.i("Data saved", "Data SAVED to sql successfully");

            //Toast.makeText(this, "sucessfully saved data...", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Fail", "Error, occurred, text field is blank...");
        }


    }


    public void addDataDb(String newEntry) {
        boolean insertData = mDatabaseHelper.addDate(newEntry);

        if (insertData) {
            Log.i("Success", "Data added to sql successfully");
        } else {
            Log.i("Fail", "Error, occurred");


        }
    }

    //gets data from SQLite database..
    public void retreaiveData() {
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()) {
            stepsTaken = data.getString(1);
            Log.i("STEPSTAKEN:", stepsTaken);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
