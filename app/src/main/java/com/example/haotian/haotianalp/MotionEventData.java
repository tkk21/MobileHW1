package com.example.haotian.haotianalp;


/**
 * Created by Anna on 9/5/2015.
 */


public class MotionEventData implements EventData{

    private float position_X;
    private float position_y;
    private float velocity_X;
    private float velocity_Y;
    private float pressure;
    private float size;

    public MotionEventData(float position_X, float position_y, float velocity_X, float velocity_Y, float pressure, float size) {
        this.position_X = position_X;
        this.position_y = position_y;
        this.velocity_X = velocity_X;
        this.velocity_Y = velocity_Y;
        this.pressure = pressure;
        this.size = size;
    }

    public void setVelocity_X(float velocity_X) {
        this.velocity_X = velocity_X;
    }

    public void setVelocity_Y(float velocity_Y) {
        this.velocity_Y = velocity_Y;
    }

    public String toString (){
        return String.format("%f,%f,%f,%f,%f,%f", position_X, position_y, velocity_X, velocity_Y, pressure, size);
    }

    public static String firstRowString() {
        return "position_X,position_Y,velocity_X,velocity_Y,pressure,size";
    }
}
