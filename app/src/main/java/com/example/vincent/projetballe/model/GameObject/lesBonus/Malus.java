package com.example.vincent.projetballe.model.GameObject.lesBonus;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.vincent.projetballe.bibliotheque.MyChronometer;
import com.example.vincent.projetballe.controller.GameActivity;
import com.example.vincent.projetballe.model.GameObject.lesBalles.CatchBalle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.EnnemyBalle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.UserBalle;

import java.util.ArrayList;
import java.util.Random;

public class Malus extends BonusMalus {
    private static final String TAG = "Malus";
    private static final int COLOR_MALUS = Color.GREEN;


    // malus
    public static final int MALUS_DIVISION = 0;
    public static final int MALUS_SWITCH_BALLS = 1;
    public static final int MALUS_REVERSE_ACCELEROMETER = 2;


    // attention ne pas se tromper dans le nombre de malus pour qu'ils apparaissent tous
    public static final int NUMBER_OF_MALUS = 3;


    public Malus(GameActivity gameActivity, float left, float top, float right, float bottom, long duration, int which) {
        super(gameActivity, left, top, right, bottom, COLOR_MALUS, duration, which);
        Log.d(TAG, "Malus() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "], duration = [" + duration + "], which = [" + which + "]");
    }


    // malus au hasard
    public static Malus randomMalus(GameActivity gameActivity) {
        Random r = new Random();
        int left, top, right, bottom;
        int which;

        int maxWidth = gameActivity.getViewWidth();
        int maxHeight = gameActivity.getViewHeight();

        // générer des coordonées aléatoire pour le bonus
        // ne doit pas dépasser la taille de l'écran
        left = r.nextInt(maxWidth - LONGUEUR_COTE);
        top = r.nextInt(maxHeight - LONGUEUR_COTE);
        right = left + LONGUEUR_COTE;
        bottom = top + LONGUEUR_COTE;

        // quel bonus activer
        which = r.nextInt(NUMBER_OF_MALUS);

        return new Malus(gameActivity, left, top, right, bottom, DURATION, which);
    }


    @Override
    synchronized public void run() {
        switch (this.getWhich()) {

            case MALUS_DIVISION:
                setMalusDivision();
                break;

            // TODO: 30/10/2017 continuer
            case MALUS_SWITCH_BALLS:
                setMalusSwitchBalls();
                break;

            case MALUS_REVERSE_ACCELEROMETER:
                setMalusReverseAccelerometer();
                break;

            default:
                Log.e(TAG, "run: pas d'action défini pour ce malus : " + getWhich());
                break;

        }

        getGameActivity().resetBonusTimer(); // relancer le timer des bonus
    }


    /**
     * Ajouter 9 balles dans le jeu
     * Ralentis la vitesse des balles pour pas que ce soit trop dure
     */
    public void setMalusDivision() {
        Log.d(TAG, "setMalusDivision() called");
        GameActivity gameActivity = getGameActivity();

        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Run away !", Toast.LENGTH_SHORT).show();
            }
        });


        ArrayList<EnnemyBalle> ennemyBalleArrayList = gameActivity.getEnnemyBalleArrayList();
        UserBalle mUserBalle = gameActivity.getUserBalle();
        float iaBallSpeed = gameActivity.getIaBallSpeed(); // récupérer la vitesse des balles pour la restaurer après

        gameActivity.setIaBallSpeed(5); // réduire la vitesse des balles pour réduire la difficulté

        // ajouter jusqu'à 9 balle
        while (ennemyBalleArrayList.size() < 9) {
            EnnemyBalle ennemyBalle = EnnemyBalle.randomEnnmyBalle(gameActivity, mUserBalle.getCurrentRadius());
            ennemyBalleArrayList.add(ennemyBalle);
            ennemyBalle.start();
        }

        try {  // attendre la durée du bonus
            Thread.sleep(getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // arreter toutes les balles
        for (int i = 3; i < ennemyBalleArrayList.size(); i++) {
            EnnemyBalle ennemyBalle = ennemyBalleArrayList.get(i);
            ennemyBalle.stop();
            if (i == ennemyBalleArrayList.size() - 1) ennemyBalle.join();
        }

//        ennemyBalleArrayList.subList(3, 9).clear(); // détruire // TODO: 03/11/2017 toDelete les balles sont détruites automatiquement
        gameActivity.setIaBallSpeed(iaBallSpeed); // rétablir la vitesse par défaut des balles
    }


    /**
     * Alterne la balle à attraper avec toutes les autres balles
     */
    public void setMalusSwitchBalls() {
        Log.d(TAG, "setMalusSwitchBalls() called");
        GameActivity gameActivity = getGameActivity();
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Try {} catch { BadBallException }", Toast.LENGTH_SHORT).show();
            }
        });

        // récupérer les balles ia
        ArrayList<EnnemyBalle> ennemyBalleArrayList = gameActivity.getEnnemyBalleArrayList();

        // préparer un chronometre
        Random r = new Random(); // prendre une balle au hasard dans le tableau des balles
        MyChronometer myChronometer = new MyChronometer(getDuration());
        myChronometer.start();

        // changer la position de la catchballe avec les autres balles tant que le chronomètre n'est pas fini
        while (!myChronometer.hasFinished()) {
            CatchBalle catchBalle = gameActivity.getCatchBalle();
            EnnemyBalle ennemyBalle = ennemyBalleArrayList.get(r.nextInt(ennemyBalleArrayList.size()));
            catchBalle.switchBalle(ennemyBalle);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Inver
     */
    public void setMalusReverseAccelerometer() {
        Log.d(TAG, "setMalusReverseAccelerometer() called");
        GameActivity gameActivity = getGameActivity();
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Right left up left right down?!?", Toast.LENGTH_SHORT).show();
            }
        });

        gameActivity.setAccelerometerInversed(true);

        try {  // attendre la durée du bonus
            Thread.sleep(getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameActivity.setAccelerometerInversed(false);
    }


    /**
     * DEGUG
     */

    // fonction deboggage
    public static Malus debugWhichMalus(GameActivity gameActivity, int which) {
        Malus malus = randomMalus(gameActivity);
        malus.setWhich(which);
        return malus;
    }
}
