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
import android.widget.TextView;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.model.GameData;
import com.example.vincent.projetballe.model.LesBalles.CatchBall;
import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

import java.util.ArrayList;

/**
 * Cette classe gère le déplacement de la balle de l'utilisateur à l'aide de l'accéléromètre
 * Elle instancie toutes les balles
 * Elle gére toutes les interaction de la balle avec les autres objets et l'environnement
 * ex : collision avec une autre balle, prendre un bonus, etc.
 */
public class GameActivity extends Activity implements SensorEventListener {

    public final static String MY_INTENT_EXTRA_SCORE = "score";

    // Vue
    private View mGameView;
    private TextView tvScore;
    // Accéléromètre
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean gameAlreadyStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
        setContentView(R.layout.activity_game);

        // récupérer la vue
        mGameView = findViewById(R.id.game_view);
        tvScore = (TextView) findViewById(R.id.tv_score);
        diplayScore();

        // récupérer la vue
//        mGameView = new GameView(this);
//        setContentView(mGameView);

        // récupérer l'accéléromètre
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // lister tous les sensors du téléphone
//        List<Sensor> sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor s : sensorsList) {
//            Log.v("SensorAvailable", "" + s.toString());
//        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
//        // lancer l'accéléromètre
//        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
//        pauseGame();
//    }

    /**
     * Détruire le jeu pour la partie suivante
     */
    @Override
    protected void onDestroy() {
        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
        super.onDestroy();
        destroyGame();
        // TODO: 25/10/2017 Supprimer les lignes en dessous
//        Intent intent = new Intent(this, MainActivity.class); // retourner dans le main activity
//        startActivity(intent);
    }

    /**
     * Récupérer la taille de la view et générer les balles ensuite
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + " : hasFocus = " + hasFocus);
        if (hasFocus) {
            if (gameAlreadyStarted) {
                resumeGame();
            } else {
                GameData.viewWidth = mGameView.getWidth();
                GameData.viewHeight = mGameView.getHeight();
                Log.v("WindowsSize", "Width=" + GameData.viewWidth + ", Height=" + GameData.viewHeight); // Longueur max
                startGame();
            }
        } else {
            pauseGame();
        }

    }


    /*
     * GESTION DE LA PARTIE
     * startGame(), resumeGame(), pauseGame(), destroyGame()
     */


    private void startGame() {
        Log.v(getClass().getSimpleName(), "Starting Game...");
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

        gameAlreadyStarted = true;
        // lancer l'accéléromètre
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }


    /**
     * Mettre en pause l'accéléromètre et les balles IA
     */
    private void pauseGame() {
        Log.v(getClass().getSimpleName(), "Pausing Game...");
        // arreter l'accéléromètre
        mSensorManager.unregisterListener(this);

        // mettre en pause les balles ia
        for (IABalle balle : GameData.listIABalles) {
            balle.pause();
        }

        // la balles a attraper
        GameData.catchBall.pause();
    }

    private void resumeGame() {
        Log.v(getClass().getSimpleName(), "Resuming Game...");
        // lancer l'accéléromètre
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        // mettre en pause les balles ia
        for (IABalle balle : GameData.listIABalles) {
            balle.resume();
        }

        // la balles a attraper
        GameData.catchBall.resume();
    }

    private void destroyGame() {
        Log.v(getClass().getSimpleName(), "Destroying Game...");

        // mettre en pause les balles ia
        for (IABalle balle : GameData.listIABalles) {
            balle.stop();
        }
        // la balles a attraper
        GameData.catchBall.stop();
        GameData.clear();
    }


    /**
     * Analyse chaque interaction de la balle avec l'environnement
     * ex : collision avec une autre balle, récupération d'un bonus
     */
    private void onUserBalleMovedListener() {
        UserBalle mUserBall = GameData.userBalle;
        for (IABalle balle : GameData.listIABalles) {
            if (mUserBall.touched(balle)) {
                Log.v(new Exception().getStackTrace()[0].getMethodName(), "UserBall touched IABall");
                // Envoyer le score au main Activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(MY_INTENT_EXTRA_SCORE, GameData.score);
                if (GameData.score > 0)
                    setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            }
        }

        // Quand on touche la balle a attraper
        if (mUserBall.touched(GameData.catchBall)) {
            Log.v(new Exception().getStackTrace()[0].getMethodName(), "UserBall touched CatchBall");
            GameData.score++;
            diplayScore();
            // Détruire la catch balle et faire réapparaitre une plus tard
            new Thread(new Runnable() {
                @Override
                public void run() {
                    IABalle.speed *= 1.2;
                    GameData.catchBall.stop();
                    GameData.catchBall.setPosX(GameData.viewWidth + 10000);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameData.catchBall = CatchBall.RandomBalle(GameData.userBalle.getRadius());
                    GameData.catchBall.start();
                }
            }).start();
        }

    }


    private void diplayScore() {
        tvScore.setText("Score : " + GameData.score);
    }


    /**
     * Bouger la balle avec l'accéléromètre
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + " : x=" + event.values[0] + ", y=" + event.values[1]);
        if (GameData.userBalle != null) {
            GameData.userBalle.move((int) event.values[0], (int) event.values[1]);
            onUserBalleMovedListener(); // gérer les évènements de la balle avec l'environnement
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
