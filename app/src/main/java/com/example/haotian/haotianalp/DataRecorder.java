package com.example.haotian.haotianalp;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataRecorder {

    enum EventDataType{
        MotionEventData, SensorEventData;
    }
    public static int fileCount = 0;
    private final String filename;
    private FileOutputStream outputStream;
    private EventDataType eventDataType;
    public DataRecorder(String filename, EventDataType eventDataType){
        this.filename = filename;
        fileCount++;
        this.eventDataType = eventDataType;
    }

    public void writeData(EventData touch){
        initialize();
        try {
            outputStream.write(touch.toString().getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.flush();
        }
        catch (IOException e){
            Log.wtf("touch data recorder", "filed to write data");
        }
    }

    public void close() {
        try {
            outputStream.close();
        }
        catch (IOException e){
            Log.wtf("touch data recorder", "could not close the file");
        }
    }

    private void initialize () {
        if (outputStream == null) {
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File csvDir = new File (root + "/DCIM/");
                csvDir.mkdir();
                File file = new File(csvDir, filename);
                if (file.exists()){
                    file.delete();
                }
                outputStream = new FileOutputStream(file);

                switch(eventDataType){
                    case MotionEventData:
                        outputStream.write(MotionEventData.firstRowString().getBytes());
                        break;
                    case SensorEventData:
                        outputStream.write(SensorEventData.firstRowString().getBytes());
                        break;
                    default:
                        break;
                }
                outputStream.write(System.lineSeparator().getBytes());
                outputStream.flush();
                Log.d("file is at: ", file.getAbsolutePath());
            } catch (IOException ex) {
                Log.wtf("touch data recorder", "failed to create a new file writer");
            }
        }
    }
}
