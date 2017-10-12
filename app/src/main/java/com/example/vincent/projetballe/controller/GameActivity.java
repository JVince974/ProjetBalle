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
import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;
import com.example.vincent.projetballe.view.GameView;

/**
 * Cette classe gère le déplacement de la balle de l'utilisateur à l'aide de l'accéléromètre
 * Elle instancie toutes les balles
 * Elle gére toutes les interaction de la balle avec les autres objets et l'environnement
 * ex : collision avec une autre balle, prendre un bonus, etc.
 */
public class GameActivity extends Activity implements SensorEventListener {

    // Vue
    private View mGameView;
    // Accéléromètre
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // récupérer la vue
        mGameView = new GameView(this);
        setContentView(mGameView);

        // créer les tableaux pour sauvegarder les balles
        GameModel.onCreate();

        // récupérer l'accéléromètre
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // lister tous les sensors du téléphone
//        List<Sensor> sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor s : sensorsList) {
//            Log.v("SensorAvailable", "" + s.toString());
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // lancer l'accéléromètre
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // arreter l'accéléromètre
        mSensorManager.unregisterListener(this);
    }

    /**
     * Détruire le jeu pour la partie suivante
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameModel.onDestroy(); // détruire le jeu
        Intent intent = new Intent(this, MainActivity.class); // retourner dans le main activity
        startActivity(intent);
    }

    /**
     * Récupérer la taille de la view et générer les balles ensuite
     */
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
     * Créer toutes les balles
     */
    private void createBalles() {
        // créer la balle de l'utilisateur
        if (GameModel.userBalle == null) GameModel.userBalle = new UserBalle(mGameView);
        // créer des balles IA
        GameModel.randomIABalles(5);
    }

    /**
     * Analyse chaque interaction de la balle avec l'environnement
     * ex : collision avec une autre balle, récupération d'un bonus
     */
    private void onUserBalleMoved() {
        for (IABalle uneBalle : GameModel.listIABalles) {
        }
    }

    /**
     * Bouger la balle avec l'accéléromètre
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (GameModel.userBalle != null) {
            GameModel.userBalle.move((int) event.values[0], (int) event.values[1]);
            onUserBalleMoved(); // gérer les évènements de la balle avec l'environnement
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
