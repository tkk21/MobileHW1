package com.example.haotian.haotianalp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ted on 9/10/2015.
 */
public class MergedEventData implements EventData {

    private List<String> sensorEventDataList;
    private List<String> motionEventDataList;
    private int counter;
    private List<Point> pattern;

    public MergedEventData() {
        sensorEventDataList = new ArrayList<>();
        motionEventDataList = new ArrayList<>();
        counter = 0;
    }

    public void init(){
        sensorEventDataList = new ArrayList<>();
        motionEventDataList = new ArrayList<>();
    }

    public void setPattern(List<Point> pattern) {
        this.pattern = pattern;
    }

    public void record (SensorEventData sensorEventData, MotionEventData motionEventData){
        try {
            sensorEventDataList.add(sensorEventData.toString());
        }
        catch(NullPointerException e){
            Log.wtf("MergedEventData", "sensorEventData was not set");
        }
        motionEventDataList.add(motionEventData.toString());
    }

    public List<String> getSensorEventDataList() {
        return sensorEventDataList;
    }

    public List<String> getMotionEventDataList() {
        return motionEventDataList;
    }

    public int getCounter() {
        return counter;
    }

    public List<Point> getPattern() {
        return pattern;
    }

    public void incrementCounter(){
        counter++;
    }

    public static String firstRowString(){
        return MotionEventData.firstRowString() +","+ SensorEventData.firstRowString() + ",mCurrentPattern,Counter";
    }
}
