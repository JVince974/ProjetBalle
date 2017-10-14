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

import com.example.vincent.projetballe.model.GameData;
import com.example.vincent.projetballe.model.LesBalles.CatchBall;
import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;
import com.example.vincent.projetballe.view.GameView;

import java.util.ArrayList;

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
        Log.v(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        // lancer l'accéléromètre
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.v(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        // arreter l'accéléromètre
        mSensorManager.unregisterListener(this);
    }

    /**
     * Détruire le jeu pour la partie suivante
     */
    @Override
    protected void onDestroy() {
        Log.v(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        super.onDestroy();
        endGame();
        Intent intent = new Intent(this, MainActivity.class); // retourner dans le main activity
        startActivity(intent);
    }

    /**
     * Récupérer la taille de la view et générer les balles ensuite
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.v(getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        GameData.viewWidth = mGameView.getWidth();
        GameData.viewHeight = mGameView.getHeight();
        Log.v("WindowsSize", "Width=" + GameData.viewWidth); // Longueur max
        Log.v("WindowsSize", "Height=" + GameData.viewHeight); // Hauteur Max
        startGame();
    }


    /**
     * Démarrer le jeu
     */
    private void startGame() {
        Log.v(getClass().getName(), "Starting game...");
        // créer les tableaux pour stocker les balles
        if (GameData.listIABalles == null) GameData.listIABalles = new ArrayList<>();

        // positionner la balle de l'utilisateur au milieu
        if (GameData.userBalle == null)
            GameData.userBalle = new UserBalle(GameData.viewWidth / 2, GameData.viewHeight / 2, (int) (GameData.viewWidth * 4.63 / 100));

        // ajouter 5 balles ia
        while (GameData.listIABalles.size() < 5) {
            GameData.listIABalles.add(IABalle.RandomBalle(GameData.userBalle.getRadius()));
        }

        // lancer les balles ia
        for (IABalle uneBalle : GameData.listIABalles) {
            uneBalle.start();
        }

        // la balle a attraper
        if (GameData.catchBall == null) {
            GameData.catchBall = CatchBall.RandomBalle(GameData.userBalle.getRadius());
            GameData.catchBall.start();
        }

    }


    private void endGame() {
        Log.v(getClass().getName(), "Ending game...");
    }

    /**
     * Analyse chaque interaction de la balle avec l'environnement
     * ex : collision avec une autre balle, récupération d'un bonus
     */
    private void onUserBalleMoved() {

    }

    /**
     * Bouger la balle avec l'accéléromètre
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (GameData.userBalle != null) {
            GameData.userBalle.move((int) event.values[0], (int) event.values[1]);
            onUserBalleMoved(); // gérer les évènements de la balle avec l'environnement
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
