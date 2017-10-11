package com.example.vincent.projetballe.model;

import android.graphics.Color;
import android.util.Log;

public class Ball {


    private int color = Color.RED;
    private int radius;

    private int x;
    private int y;

    public Ball(int x, int y, int radius, int color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        Log.v("NewBalle", toString());
    }


    public String toString() {
        return "[x=" + x + "; " +
                "y=" + y + "; " +
                "radius=" + radius + "; " +
                "color=" + color + ";";
    }

    /*******************
     * GETTER AND SETTER
     ********************/
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
