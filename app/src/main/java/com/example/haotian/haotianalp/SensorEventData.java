package com.example.haotian.haotianalp;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.sql.Timestamp;

/**
 * Created by ted on 9/6/2015.
 */
public class SensorEventData implements EventData{

    private long timestamp;
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

    public SensorEventData() {
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
    }

    public void setMagX(float magX) {
        this.magX = magX;
    }

    public void setMagY(float magY) {
        this.magY = magY;
    }

    public void setMagZ(float magZ) {
        this.magZ = magZ;
    }

    public void setGyrX(float gyrX) {
        this.gyrX = gyrX;
    }

    public void setGyrY(float gyrY) {
        this.gyrY = gyrY;
    }

    public void setGyrZ(float gyrZ) {
        this.gyrZ = gyrZ;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setLinAccX(float linAccX) {
        this.linAccX = linAccX;
    }

    public void setLinAccY(float linAccY) {
        this.linAccY = linAccY;
    }

    public void setLinAccZ(float linAccZ) {
        this.linAccZ = linAccZ;
    }

    public void setGravX(float gravX) {
        this.gravX = gravX;
    }

    public void setGravY(float gravY) {
        this.gravY = gravY;
    }

    public void setGravZ(float gravZ) {
        this.gravZ = gravZ;
    }

    public String toString () {
        return String.format("%d,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f",
                timestamp,accX,accY,accZ,magX,magY,magZ,gyrX,gyrY,gyrZ,rotX,rotY,rotZ,
                linAccX,linAccY,linAccZ,gravX,gravY,gravZ);
    }

    public static String firstRowString () {
        return "TimeStamp,TYPE_ACCELEROMETER_X,TYPE_ACCELEROMETER_Y,TYPE_ACCELEROMETER_Z,TYPE_MAGNETIC_FIELD_X,TYPE_MAGNETIC_FIELD_Y,TYPE_MAGNETIC_FIELD_Z,TYPE_GYROSCOPE_X,TYPE_GYROSCOPE_Y,TYPE_GYROSCOPE_Z,TYPE_ROTATION_VECTOR_X,TYPE_ROTATION_VECTOR_Y,TYPE_ROTATION_VECTOR_Z,TYPE_LINEAR_ACCELERATION_X,TYPE_LINEAR_ACCELERATION_Y,TYPE_LINEAR_ACCELERATION_Z,TYPE_GRAVITY_X,TYPE_GRAVITY_Y,TYPE_GRAVITY_Z";
    }
}