package com.example.haotian.haotianalp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Anna on 9/5/2015.
 */
public class TouchDataRecorder {

    public static int fileCount = 0;
    private final String filename;
    private FileWriter out;
    public TouchDataRecorder(String filename){
        this.filename = filename;
        fileCount++;
    }

    public void writeData(TouchDataObject touch){
        initialize();
        try {
            out.write(touch.toString());
            out.write(System.lineSeparator());
            out.flush();
        }
        catch (IOException e){

        }
    }

    public void close() {
        try {
            out.close();
        }
        catch (IOException e){

        }
    }

    private void initialize () {
        if (out != null) {
            try {
                out = new FileWriter(filename);
            } catch (IOException ex) {
                Log.wtf("exception", "io exception");
            }
        }
        else {

        }
    }
}
