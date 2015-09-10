package com.example.haotian.haotianalp;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataRecorder {

    enum EventDataType{
        MotionEventData, SensorEventData, MergedEventData;
    }
    public static int fileCount = 0;
    private final String filename;
    private BufferedWriter bufferedWriter;
    private EventDataType eventDataType;
    public DataRecorder(String filename, EventDataType eventDataType){
        this.filename = filename;
        fileCount++;
        this.eventDataType = eventDataType;
    }

    public void writeData(EventData touch){
        initialize();
        try {
            bufferedWriter.append(touch.toString());
            bufferedWriter.append(System.lineSeparator());
            bufferedWriter.flush();
        }
        catch (IOException e){
            Log.wtf("data recorder", "filed to write data");
        }
    }

    public void writeData(String data){
        initialize();
        try {
            bufferedWriter.append(data);
            bufferedWriter.append(System.lineSeparator());
            bufferedWriter.flush();
        }
        catch (IOException e){
            Log.wtf("data recorder", "filed to write data");
        }
    }

    public void close() {
        try {
            bufferedWriter.close();
        }
        catch (IOException e){
            Log.wtf("touch data recorder", "could not close the file");
        }
    }

    private void initialize () {
        if (bufferedWriter == null) {
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File csvDir = new File (root + "/DCIM/");
                csvDir.mkdir();
                File file = new File(csvDir, filename);
                if (file.exists()){
                    file.delete();
                }
                bufferedWriter = new BufferedWriter(new FileWriter (file));

                switch(eventDataType){
                    case MotionEventData:
                        bufferedWriter.write(MotionEventData.firstRowString());
                        break;
                    case SensorEventData:
                        bufferedWriter.write(SensorEventData.firstRowString());
                        break;
                    case MergedEventData:
                        bufferedWriter.write(MotionEventData.firstRowString() + SensorEventData.firstRowString() + ",mCurrentPattern,Counter");
                        break;
                    default:
                        break;
                }
                bufferedWriter.append(System.lineSeparator());
                bufferedWriter.flush();
                Log.d("file is at: ", file.getAbsolutePath());
            } catch (IOException ex) {
                Log.wtf("touch data recorder", "failed to create a new file writer");
            }
        }
    }
}
