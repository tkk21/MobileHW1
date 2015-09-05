package com.example.haotian.haotianalp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.io.FileOutputStream;

/**
 * Created by Anna on 9/5/2015.
 */
public class MotionEvent implements SensorEventListener {

    public void getMotionEvents() {


        String FILENAME = "Motion_Event_Data";

        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
