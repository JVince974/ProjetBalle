package com.example.vincent.projetballe.model.BallClasses;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

import com.example.vincent.projetballe.model.Ball;


public class UserBall extends Ball implements SensorEventListener {

    public UserBall(View v) {
        super(v.getWidth() / 2, v.getHeight() / 2, (int) (v.getWidth() * 4.63 / 100), Color.RED);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
