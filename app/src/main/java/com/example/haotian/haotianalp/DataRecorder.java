package com.example.haotian.haotianalp;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataRecorder {

    enum EventDataType{
        MotionEventData, SensorEventData, MergedEventData;
    }
    private final String filename;
    private BufferedWriter bufferedWriter;
    private EventDataType eventDataType;
    public DataRecorder(String filename, EventDataType eventDataType){
        this.filename = filename;
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

    public void writeBulkData (MergedEventData data){
        initialize();
        List<String> sensorEventDataList = data.getSensorEventDataList();
        List<String> motionEventDataList = data.getMotionEventDataList();

        for (int i = 0; i<sensorEventDataList.size(); i++){
            try{
                bufferedWriter.append(sensorEventDataList.get(i)+","+motionEventDataList.get(i)+","
                        +PatternGenerator.patternToString(data.getPattern())+","+data.getCounter());
                bufferedWriter.append(System.lineSeparator());
            }
            catch(IOException e){
                Log.wtf("data recorder", "failed to write merged data");
            }
        }
        try {
            bufferedWriter.flush();
        }
        catch(IOException e){
            Log.wtf("data recorder", "failed to flush the merged data");
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
                boolean isMergedEventData = eventDataType.equals(EventDataType.MergedEventData);
                csvDir.mkdir();
                File file = new File(csvDir, filename);
                if (file.exists() && !isMergedEventData){
                    file.delete();
                }
                bufferedWriter = new BufferedWriter(new FileWriter (file, true));

                switch(eventDataType){
                    case MotionEventData:
                        bufferedWriter.write(MotionEventData.firstRowString());
                        break;
                    case SensorEventData:
                        bufferedWriter.write(SensorEventData.firstRowString());
                        break;
                    case MergedEventData:
                        if (!file.exists()) {
                            bufferedWriter.write(MergedEventData.firstRowString());
                        }
                        break;
                    default:
                        break;
                }
                if (!isMergedEventData || (isMergedEventData && !file.exists())) {
                    bufferedWriter.append(System.lineSeparator());
                }
                bufferedWriter.flush();
                Log.d("file is at: ", file.getAbsolutePath());
            } catch (IOException ex) {
                Log.wtf("touch data recorder", "failed to create a new file writer");
            }
        }
    }
}
