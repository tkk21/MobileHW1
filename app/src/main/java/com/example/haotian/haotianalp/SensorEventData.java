package com.example.haotian.haotianalp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.method.DateTimeKeyListener;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private boolean[] isRecorded;

    public SensorEventData() {
        isRecorded = new boolean[6];
    }

    public void setTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.timestamp = format.format(date);
    }

    public void setAccX(float accX) {
        this.accX = accX;
        isRecorded[0] = true;
    }

    public void setAccY(float accY) {
        this.accY = accY;
        isRecorded[0] = true;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
        isRecorded[0] = true;
    }

    public void setMagX(float magX) {
        this.magX = magX;
        isRecorded[1] = true;
    }

    public void setMagY(float magY) {
        this.magY = magY;
        isRecorded[1] = true;
    }

    public void setMagZ(float magZ) {
        this.magZ = magZ;
        isRecorded[1] = true;
    }

    public void setGyrX(float gyrX) {
        this.gyrX = gyrX;
        isRecorded[2] = true;
    }

    public void setGyrY(float gyrY) {
        this.gyrY = gyrY;
        isRecorded[2] = true;
    }

    public void setGyrZ(float gyrZ) {
        this.gyrZ = gyrZ;
        isRecorded[2] = true;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
        isRecorded[3] = true;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
        isRecorded[3] = true;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
        isRecorded[3] = true;
    }

    public void setLinAccX(float linAccX) {
        this.linAccX = linAccX;
        isRecorded[4] = true;
    }

    public void setLinAccY(float linAccY) {
        this.linAccY = linAccY;
        isRecorded[4] = true;
    }

    public void setLinAccZ(float linAccZ) {
        this.linAccZ = linAccZ;
        isRecorded[4] = true;
    }

    public void setGravX(float gravX) {
        this.gravX = gravX;
        isRecorded[5] = true;
    }

    public void setGravY(float gravY) {
        this.gravY = gravY;
        isRecorded[5] = true;
    }

    public void setGravZ(float gravZ) {
        this.gravZ = gravZ;
        isRecorded[5] = true;
    }

    public boolean isComplete() {
        boolean isComplete = true;

        for (int i = 0; i < isRecorded.length; i++) {
            isComplete &= isRecorded[i];
        }

        return isComplete;
    }

    public String toString () {
        return String.format("%s,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f",
                timestamp,accX,accY,accZ,magX,magY,magZ,gyrX,gyrY,gyrZ,rotX,rotY,rotZ,
                linAccX,linAccY,linAccZ,gravX,gravY,gravZ);
    }

    public static String firstRowString () {
        return "TimeStamp,TYPE_ACCELEROMETER_X,TYPE_ACCELEROMETER_Y,TYPE_ACCELEROMETER_Z,TYPE_MAGNETIC_FIELD_X,TYPE_MAGNETIC_FIELD_Y,TYPE_MAGNETIC_FIELD_Z,TYPE_GYROSCOPE_X,TYPE_GYROSCOPE_Y,TYPE_GYROSCOPE_Z,TYPE_ROTATION_VECTOR_X,TYPE_ROTATION_VECTOR_Y,TYPE_ROTATION_VECTOR_Z,TYPE_LINEAR_ACCELERATION_X,TYPE_LINEAR_ACCELERATION_Y,TYPE_LINEAR_ACCELERATION_Z,TYPE_GRAVITY_X,TYPE_GRAVITY_Y,TYPE_GRAVITY_Z";
    }
}