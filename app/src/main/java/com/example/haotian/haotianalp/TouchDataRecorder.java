package com.example.haotian.haotianalp;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Anna on 9/5/2015.
 */
public class TouchDataRecorder {

    public static int fileCount = 0;
    private final String filename;
    private FileOutputStream outputStream;
    private ContextWrapper cw;
    private Context context;
    public TouchDataRecorder(String filename, Context context){
        this.filename = filename;
        fileCount++;
        cw = new ContextWrapper(context);
        this.context = context;
    }

    public void writeData(MotionEventData touch){
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
//                outputStream =  new FileOutputStream(cw.getDir("DCIM/"+filename, Context.MODE_PRIVATE));
                File file = new File(context.getFilesDir(), filename);
                outputStream = new FileOutputStream(file);
                outputStream.write("position_X,position_Y,velocity_X,velocity_Y,pressure,size".getBytes());
                outputStream.write(System.lineSeparator().getBytes());
                outputStream.flush();
                Log.d("file is at: ", file.getAbsolutePath());
            } catch (IOException ex) {
                Log.wtf("touch data recorder", "failed to create a new file writer");
            }
        }
    }
}
