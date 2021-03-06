package com.example.haotian.haotianalp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.method.Touch;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ALPActivity extends Activity implements SensorEventListener{
    protected LockPatternView mPatternView;
    protected PatternGenerator mGenerator;
    protected Button mGenerateButton;
    protected Button mDesigner;
    protected ToggleButton mPracticeToggle;
    private List<Point> mEasterEggPattern;
    protected SharedPreferences mPreferences;
    protected int mGridLength=0;
    protected int mPatternMin=0;
    protected int mPatternMax=0;
    protected String mHighlightMode;
    protected boolean mTactileFeedback;
    protected SensorEventData sensorEventData;

    private static final String TAG = "SensorActivity";
    private static final String TAGmotion = "motionEvent";
    private SensorManager mSensorManager = null;

    public List<Sensor> deviceSensors;
    private  Sensor mAccelerometer, mMagnetometer, mGyroscope, mRotation, mGravity, myLinearAcc;
    private DataRecorder mDataRecorder;

    private File file;
    public static String[] mLine;
    public BufferedWriter bufferedWriter;
    private VelocityTracker mVelocityTracker = null;
    private int control = 0;
    DateFormat mDateFormat;
    String mTimestamp;
    private int counter=0;
    private String myStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mGenerator = new PatternGenerator();

        setContentView(R.layout.activity_alp);
        mPatternView = (LockPatternView) findViewById(R.id.pattern_view);
        mGenerateButton = (Button) findViewById(R.id.generate_button);

        mGenerateButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        if (!mPatternView.getPracticeMode()) {
                            mPatternView.setPattern(mGenerator.getPattern());
                            mPatternView.invalidate();
                        }
                    }
                }
        );

        mPracticeToggle = (ToggleButton) findViewById(R.id.practice_toggle);


        mPracticeToggle.setOnCheckedChangeListener(
                new ToggleButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        mPatternView.setPracticeMode(!mPatternView.getPracticeMode());

                    }
                });
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        myLinearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mDataRecorder = new DataRecorder("SensorData.txt", DataRecorder.EventDataType.SensorEventData);
        sensorEventData = new SensorEventData();
        mPatternView.setSensorEventData(sensorEventData);
    }

    @Override
    protected void onResume()
    {
        super.onResume();


        updateFromPrefs();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotation, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, myLinearAcc, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mDataRecorder = new DataRecorder("SensorData.csv", DataRecorder.EventDataType.SensorEventData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_al, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        mDataRecorder.close();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        SensorEventData data = new SensorEventData(sensorEvent.timestamp, sensorEvent.
//        TouchDataRecorder recorder = new TouchDataRecorder(String.format("SensorData%d.csv", TouchDataRecorder.fileCount), )
        //don't have context here how to save?

        sensorEventData.setTimestamp();
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                sensorEventData.setAccX(sensorEvent.values[0]);
                sensorEventData.setAccY(sensorEvent.values[1]);
                sensorEventData.setAccZ(sensorEvent.values[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                sensorEventData.setMagX(sensorEvent.values[0]);
                sensorEventData.setMagY(sensorEvent.values[1]);
                sensorEventData.setMagZ(sensorEvent.values[2]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                sensorEventData.setGyrX(sensorEvent.values[0]);
                sensorEventData.setGyrY(sensorEvent.values[1]);
                sensorEventData.setGyrZ(sensorEvent.values[2]);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                sensorEventData.setRotX(sensorEvent.values[0]);
                sensorEventData.setRotY(sensorEvent.values[1]);
                sensorEventData.setRotZ(sensorEvent.values[2]);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                sensorEventData.setLinAccX(sensorEvent.values[0]);
                sensorEventData.setLinAccY(sensorEvent.values[1]);
                sensorEventData.setLinAccZ(sensorEvent.values[2]);
                break;
            case Sensor.TYPE_GRAVITY:
                sensorEventData.setGravX(sensorEvent.values[0]);
                sensorEventData.setGravY(sensorEvent.values[1]);
                sensorEventData.setGravZ(sensorEvent.values[2]);
                break;
            default:
                Log.d("onSensorChanged", "a sensor event that shouldn't happen, happened");
                break;
        }

        if (sensorEventData.isComplete()) {
            mDataRecorder.writeData(sensorEventData);
            sensorEventData.init();
        }

    }

    @Override
    public final void onAccuracyChanged (Sensor sensor, int accuracy){
        //don't need to do anything
    }



    private void updateFromPrefs()
    {
        int gridLength =
                mPreferences.getInt("grid_length", Defaults.GRID_LENGTH);
        int patternMin =
                mPreferences.getInt("pattern_min", Defaults.PATTERN_MIN);
        int patternMax =
                mPreferences.getInt("pattern_max", Defaults.PATTERN_MAX);
        String highlightMode =
                mPreferences.getString("highlight_mode", Defaults.HIGHLIGHT_MODE);
        boolean tactileFeedback = mPreferences.getBoolean("tactile_feedback",
                Defaults.TACTILE_FEEDBACK);

        // sanity checking
        if(gridLength < 1)
        {
            gridLength = 1;
        }
        if(patternMin < 1)
        {
            patternMin = 1;
        }
        if(patternMax < 1)
        {
            patternMax = 1;
        }
        int nodeCount = (int) Math.pow(gridLength, 2);
        if(patternMin > nodeCount)
        {
            patternMin = nodeCount;
        }
        if(patternMax > nodeCount)
        {
            patternMax = nodeCount;
        }
        if(patternMin > patternMax)
        {
            patternMin = patternMax;
        }

        // only update values that differ
        if(gridLength != mGridLength)
        {
            setGridLength(gridLength);
        }
        if(patternMax != mPatternMax)
        {
            setPatternMax(patternMax);
        }
        if(patternMin != mPatternMin)
        {
            setPatternMin(patternMin);
        }
        if(!highlightMode.equals(mHighlightMode))
        {
            setHighlightMode(highlightMode);
        }
        if(tactileFeedback ^ mTactileFeedback)
        {
            setTactileFeedback(tactileFeedback);
        }
    }

    private void setGridLength(int length)
    {
        mGridLength = length;
        mGenerator.setGridLength(length);
        mPatternView.setGridLength(length);
    }
    private void setPatternMin(int nodes)
    {
        mPatternMin = nodes;
        mGenerator.setMinNodes(nodes);
    }
    private void setPatternMax(int nodes)
    {
        mPatternMax = nodes;
        mGenerator.setMaxNodes(nodes);
    }
    private void setHighlightMode(String mode)
    {
        if("no".equals(mode))
        {
            mPatternView.setHighlightMode(new LockPatternView.NoHighlight());
        }
        else if("first".equals(mode))
        {
            mPatternView.setHighlightMode(new LockPatternView.FirstHighlight());
        }
        else if("rainbow".equals(mode))
        {
            mPatternView.setHighlightMode(
                    new LockPatternView.RainbowHighlight());
        }

        mHighlightMode = mode;
    }
    private void setTactileFeedback(boolean enabled)
    {
        mTactileFeedback = enabled;
        mPatternView.setTactileFeedbackEnabled(enabled);
    }

}
