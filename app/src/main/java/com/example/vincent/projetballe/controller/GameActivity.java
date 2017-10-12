package com.example.vincent.projetballe.controller;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vincent.projetballe.model.GameModel;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;
import com.example.vincent.projetballe.view.GameView;

/**
 * Cette classe gère le déplacement de la balle de l'utilisateur à l'aide de l'accéléromètre
 * Elle instancie toutes les balles
 * Elle gére toutes les interaction de la balle avec les autres objets
 * ex : collision avec une autre balle, prendre un bonus, etc.
 */
public class GameActivity extends Activity implements SensorEventListener {

    /***************
     * ATTRIBUTS
     ****************/
    public static int viewWidth;
    public static int viewHeight;

    private View mGameView; // Vue du jeu

    // Accelerometre
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    /***************
     * METHODES
     ****************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the view
        mGameView = new GameView(this);
        setContentView(mGameView);

        // créer les composants
        GameModel.onCreate();

        // recuperer l'accelerometre
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // lister tous les sensors
//        List<Sensor> sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor s : sensorsList) {
//            Log.v("SensorAvailable", "" + s.toString());
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME); // lancer l'accelerometre
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); // arreter l'accelerometre
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameModel.onDestroy(); // détruire le jeu
        Intent intent = new Intent(this, MainActivity.class); // retourner dans le main activity
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        GameView.viewWidth = mGameView.getWidth();
        GameView.viewHeight = mGameView.getHeight();
        Log.v("WindowsSize", "Width=" + GameView.viewWidth); // Longueur max
        Log.v("WindowsSize", "Height=" + GameView.viewHeight); // Hauteur Max
        createBalles();
    }

    /**
     * Gere la création des balles
     */
    private void createBalles() {
        // créer la balle de l'utilisateur
        if (GameModel.userBalle == null) {
            GameModel.userBalle = new UserBalle(mGameView);
        }
        GameModel.createIABalles(5);
    }

    /**
     * Gestion des mouvements de la balle
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (GameModel.userBalle != null) {
            GameModel.userBalle.setX((int) event.values[0]);
            GameModel.userBalle.setY((int) event.values[1]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
