package com.example.vincent.projetballe.controller;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vincent.projetballe.model.Ball;
import com.example.vincent.projetballe.view.GameView;

import java.util.ArrayList;

public class GameActivity extends Activity implements SensorEventListener {

    // Liste des balls
    public static ArrayList<Ball> lesBalles;
    public static int windowsWidth;
    public static int windowsHeight;

    // Vue du jeu
    private View mGameView;
    // Accelerometre
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Ball userBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_game); // useless
        // mGameView = findViewById(R.id.GameView); // map le game
        mGameView = new GameView(this);
        setContentView(mGameView);

        // recuperer l'accelerometre
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Gestion des mouvements de la balle
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//          userBall.se x - (int) event.values[0];
//            y = y + (int) event.values[1];
//            //Make sure we do not draw outside the bounds of the view.
//            //So the max values we can draw to are the bounds + the size of the circle
//            if (x <= 0 + CIRCLE_RADIUS) {
//                x = 0 + CIRCLE_RADIUS;
//            }
//            if (x >= viewWidth - CIRCLE_RADIUS) {
//                x = viewWidth - CIRCLE_RADIUS;
//            }
//            if (y <= 0 + CIRCLE_RADIUS) {
//                y = 0 + CIRCLE_RADIUS;
//            }
//            if (y >= viewHeight - CIRCLE_RADIUS) {
//                y = viewHeight - CIRCLE_RADIUS;
//            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME); // reprendre l'accelerometre

        windowsWidth = mGameView.getWidth();
        windowsHeight = mGameView.getHeight();
        Log.v("WindowsSize", "Width=" + mGameView.getWidth()); // Longueur max
        Log.v("WindowsSize", "Height=" + mGameView.getHeight()); // Hauteur Max
        // add new ball
        userBall = new Ball(windowsWidth / 2, windowsHeight / 2, (int) (windowsWidth * 4.63 / 100), Color.RED);
        lesBalles = new ArrayList<>();
        lesBalles.add(userBall);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); // arreter l'ecoute pour economiser la batterie
    }
}
