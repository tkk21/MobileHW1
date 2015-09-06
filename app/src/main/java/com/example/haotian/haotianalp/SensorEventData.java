package com.example.haotian.haotianalp;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.sql.Timestamp;

/**
 * Created by ted on 9/6/2015.
 */
public class SensorEventData implements EventData{

    private String timestamp;
    private float accX;
    private float accY;
    private float accZ;
    private float magX;
    private float magY;
    private float magZ;
    private float gyrX;
    private float gyrY;
    private float gyrZ;
    private float rotX;
    private float rotY;
    private float rotZ;
    private float linAccX;
    private float linAccY;
    private float linAccZ;
    private float gravX;
    private float gravY;
    private float gravZ;
    public SensorEventData (SensorManager sensorManager){
        Long ts = System.currentTimeMillis();
        timestamp = ts.toString();
    }


    public String toString () {
        return "";
    }
    public static String firstRowString () {
        return "TimeStamp,TYPE_ACCELEROMETER_X,TYPE_ACCELEROMETER_Y,TYPE_ACCELEROMETER_Z,TYPE_MAGNETIC_FIELD_X,TYPE_MAGNETIC_FIELD_Y,TYPE_MAGNETIC_FIELD_Z,TYPE_GYROSCOPE_X,TYPE_GYROSCOPE_Y,TYPE_GYROSCOPE_Z,TYPE_ROTATION_VECTOR_X,TYPE_ROTATION_VECTOR_Y,TYPE_ROTATION_VECTOR_Z,TYPE_LINEAR_ACCELERATION_X,TYPE_LINEAR_ACCELERATION_Y,TYPE_LINEAR_ACCELERATION_Z,TYPE_GRAVITY_X,TYPE_GRAVITY_Y,TYPE_GRAVITY_Z";
    }
}
