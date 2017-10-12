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

    public static int viewWidth;
    public static int viewHeight;

    private View mGameView; // Vue du jeu

    // Accelerometre
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


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
        if (GameModel.getUserBalle() == null) {
            GameModel.setUserBalle(new UserBalle(mGameView));
        }
    }

    /**
     * Gestion des mouvements de la balle
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && userBalle != null) {
        UserBalle userBalle = GameModel.getUserBalle();
        if (userBalle != null) {
            int x = userBalle.getX() - (int) event.values[0];
            int y = userBalle.getY() + (int) event.values[1];
            //Make sure we do not draw outside the bounds of the view.
            //So the max values we can draw to are the bounds + the size of the circle
            // ne doit pas depasser le rebord gauche
            if (x <= userBalle.getRadius()) {
                x = userBalle.getRadius();
            }
            // ne doit pas depasser le rebord droit
            if (x >= viewWidth - userBalle.getRadius()) {
                x = viewWidth - userBalle.getRadius();
            }
            // ne doit pas depasser le rebord haut
            if (y <= userBalle.getRadius()) {
                y = userBalle.getRadius();
            }
            // ne doit pas depasser le rebord bas
            if (y >= viewHeight - userBalle.getRadius()) {
                y = viewHeight - userBalle.getRadius();
            }
            GameModel.getUserBalle().setX(x);
            GameModel.getUserBalle().setY(y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
