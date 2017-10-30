package com.example.vincent.projetballe.model.GameObject.lesBonus;

import android.graphics.Color;
import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

import java.util.Random;

public class Malus extends BonusMalus {
    // malus
    public static final int MALUS_DIVISION = 0;
    // attention ne pas se tromper dans le nombre de malus pour avoir qu'ils apparaissent tous
    public static final int NUMBER_OF_MALUS = 1;
    private static final String TAG = "Malus";
    private static final int COLOR_MALUS = Color.GREEN;


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

    // fonction deboggage
    public static Malus debugWhichMalus(GameActivity gameActivity, int which) {
        Malus malus = randomMalus(gameActivity);
        malus.setWhich(which);
        return malus;
    }


    /*
     * DEGUG
     */

    @Override
    synchronized public void run() {

    }

}
