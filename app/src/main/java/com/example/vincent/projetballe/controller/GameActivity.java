package com.example.vincent.projetballe.controller;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vincent.projetballe.model.Ball;
import com.example.vincent.projetballe.model.BallClasses.UserBall;
import com.example.vincent.projetballe.view.GameView;

import java.util.List;

/**
 * Cette classe gère le déplacement de la balle de l'utilisateur à l'aide de l'accéléromètre
 * Elle instancie toutes les balles
 * Elle gére toutes les interaction de la balle avec les autres objets
 * ex : collision avec une autre balle, prendre un bonus, etc.
 */
public class GameActivity extends Activity implements SensorEventListener {

    public static int viewWidth;
    public static int viewHeight;

    private View mGameView; // Vue du jeu

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
        // lister tous les sensors
        List<Sensor> sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorsList) {
            Log.v("SensorAvailable", "" + s.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST); // lancer l'accelerometre
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); // arreter l'ecoute pour economiser la batterie
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        viewWidth = mGameView.getWidth();
        viewHeight = mGameView.getHeight();
        Log.v("WindowsSize", "Width=" + mGameView.getWidth()); // Longueur max
        Log.v("WindowsSize", "Height=" + mGameView.getHeight()); // Hauteur Max
        createBalls();
    }

    /**
     * Gere la création des balles
     */
    private void createBalls() {
        if (userBall == null) {
            userBall = new UserBall(mGameView);
            Ball.lesBalles.add(userBall);
        }
    }

    /**
     * Gestion des mouvements de la balle
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && userBall != null) {
        if (userBall != null) {
            int x = userBall.getX() - (int) event.values[0];
            int y = userBall.getY() + (int) event.values[1];
            //Make sure we do not draw outside the bounds of the view.
            //So the max values we can draw to are the bounds + the size of the circle
            // ne doit pas depasser le rebord gauche
            if (x <= userBall.getRadius()) {
                x = userBall.getRadius();
            }
            // ne doit pas depasser le rebord droit
            if (x >= viewWidth - userBall.getRadius()) {
                x = viewWidth - userBall.getRadius();
            }
            // ne doit pas depasser le rebord haut
            if (y <= userBall.getRadius()) {
                y = userBall.getRadius();
            }
            // ne doit pas depasser le rebord bas
            if (y >= viewHeight - userBall.getRadius()) {
                y = viewHeight - userBall.getRadius();
            }
            userBall.setX(x);
            userBall.setY(y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
