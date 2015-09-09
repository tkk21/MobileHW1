package com.example.haotian.haotianalp;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataRecorder {

    public static int fileCount = 0;
    private final String filename;
    private FileOutputStream outputStream;
    private EventData eventData;
    public DataRecorder(String filename, EventData data){
        this.filename = filename;
        fileCount++;
        this.eventData = data;
    }

    public void writeData(){
        initialize();
        try {
            outputStream.write(eventData.toString().getBytes());
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
                Log.d("state of the external storage", Environment.getExternalStorageState());
                Log.d("external storage emulated?: ", ""+Environment.isExternalStorageEmulated());
                Log.d("external storage removeable?: ", ""+Environment.isExternalStorageRemovable());
                        String root = Environment.getExternalStorageDirectory().toString();
                Log.d("external storage root is: ", root);
                File csvDir = new File (root + "/DCIM/");
                csvDir.mkdir();
                File file = new File(csvDir, filename);
                if (file.exists()){
                    file.delete();
                }
                outputStream = new FileOutputStream(file);
                outputStream.write(eventData.firstRowString().getBytes());
                outputStream.write(System.lineSeparator().getBytes());
                outputStream.flush();
                Log.d("file is at: ", file.getAbsolutePath());
            } catch (IOException ex) {
                Log.wtf("touch data recorder", "failed to create a new file writer");
            }
        }
    }
}
