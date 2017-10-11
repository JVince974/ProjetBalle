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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.GameView); // map le game

        // recuperer l'accelerometre
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Ball myBall = new Ball(Color.RED, GameView.windowsWidth / 2, GameView.windowsHeight / 2, Color.RED);

        lesBalles.add(myBall);
    }

    /**
     * Gestion des mouvements de la balle
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            
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
