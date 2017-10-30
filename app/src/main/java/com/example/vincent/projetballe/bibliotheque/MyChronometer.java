package com.example.vincent.projetballe.bibliotheque;


public class MyChronometer {

    public static final double SECOND = 1000.0;
    public static final double MINUTE = 60000.0;
    public static final double HOUR = 3600000.0;

    private long duration;
    private long end;


    public MyChronometer(long duration) {
        this.duration = duration;
    }

    public void start() {
        end = System.currentTimeMillis() + duration;
    }

    // useless
    public boolean hasFinished() {
        return System.currentTimeMillis() > end;
    }

    public long getTime() {
        return end - System.currentTimeMillis();
    }

    public long getMilliseconds() {
        return end - System.currentTimeMillis();
    }

    public double getSeconds() {
        return (end - System.currentTimeMillis()) / 1000.0;
    }

    public double getMinutes() {
        return (end - System.currentTimeMillis()) / 60000.0;
    }

    public double getHours() {
        return (end - System.currentTimeMillis()) / 3600000.0;
    }

}