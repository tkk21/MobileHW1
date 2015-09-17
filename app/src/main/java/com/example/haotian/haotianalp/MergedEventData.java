package com.example.haotian.haotianalp;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private FileWriter fileWriter;
    private File file;

    public MergedEventData() {
        sensorEventDataList = new ArrayList<>();
        motionEventDataList = new ArrayList<>();

    }

    public void ioinit(){
        String root = Environment.getExternalStorageDirectory().toString();
        File csvDir = new File (root + "/DCIM/");
        csvDir.mkdir();
        file = new File(csvDir, "counter");
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            if (line == null){
                counter = 0;
            }
            else{
                counter = Integer.parseInt(line);
            }
            bufferedReader.close();
        }
        catch(IOException e){
            Log.wtf("MergedEventData", "could not read counter");
        }
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
        try{
            fileWriter = new FileWriter(file);
            counter++;
            fileWriter.write(""+counter);
            fileWriter.flush();
            fileWriter.close();
        }
        catch(IOException e){
            Log.wtf("MergedEventData", "could not write the counter");
        }
    }

    public static String firstRowString(){
        return SensorEventData.firstRowString() + ","+ MotionEventData.firstRowString() + ",mCurrentPattern,Counter";
    }
}
