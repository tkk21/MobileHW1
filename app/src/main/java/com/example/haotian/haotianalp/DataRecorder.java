package com.example.haotian.haotianalp;

import android.content.Context;

import java.io.FileOutputStream;

/**
 * Created by Anna on 9/5/2015.
 */
public class DataRecorder {


    public static void recordData() {
        String FILENAME = "Motion_Event_Data";
        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
    }


}
