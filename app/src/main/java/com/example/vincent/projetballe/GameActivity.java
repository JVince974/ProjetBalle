package com.example.vincent.projetballe;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.example.vincent.projetballe.game.Ball;
import com.example.vincent.projetballe.game.GameView;

import java.util.ArrayList;

public class GameActivity extends Activity implements SensorEventListener {

    // canvas du jeu
    private View gameView;

    // Accelerometre
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    // Liste des balls
    public static ArrayList<Ball> lesBalles = new ArrayList<>();

    private Ball myBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.GameView); // map le game

        // recuperer l'accelerometre
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        myBall = new Ball(GameView.windowsWidth / 2, GameView.windowsHeight / 2, (int) (GameView.windowsHeight * 4.63 / 100), Color.RED);

        lesBalles.add(myBall);
    }

    /**
     * Gestion des mouvements de la balle
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//          myBall.se x - (int) event.values[0];
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); // arreter l'ecoute pour economiser la batterie
    }
}
