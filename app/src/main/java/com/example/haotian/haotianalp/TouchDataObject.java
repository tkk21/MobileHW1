package com.example.haotian.haotianalp;


/**
 * Created by Anna on 9/5/2015.
 */


public class TouchDataObject {

    private double position_X;
    private double position_y;
    private double velocity_X;
    private double velocity_Y;
    private double pressure;
    private double size;

    public TouchDataObject(double position_X, double position_y, double velocity_X, double velocity_Y, double pressure, double size) {
        this.position_X = position_X;
        this.position_y = position_y;
        this.velocity_X = velocity_X;
        this.velocity_Y = velocity_Y;
        this.pressure = pressure;
        this.size = size;
    }

    public double getPosition_X() {
        return position_X;
    }

    public double getPosition_y() {
        return position_y;
    }

    public double getVelocity_X() {
        return velocity_X;
    }

    public double getVelocity_Y() {
        return velocity_Y;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSize() {
        return size;
    }

    public void setVelocity_X(double velocity_X) {
        this.velocity_X = velocity_X;
    }

    public void setVelocity_Y(double velocity_Y) {
        this.velocity_Y = velocity_Y;
    }

    public String toString (){
        return String.format("%f,%f,%f,%f,%f,%f", position_X, position_y, velocity_X, velocity_Y, pressure, size);
    }
}
