package com.example.vincent.projetballe.model.GameObject.lesBonus;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.vincent.projetballe.controller.GameActivity;
import com.example.vincent.projetballe.model.GameObject.lesBalles.EnnemyBalle;

import java.util.ArrayList;
import java.util.Random;

public class Bonus extends BonusMalus {
    // bonus
    public static final int BONUS_STOP_IA_BALLS = 0;
    public static final int BONUS_YOU_CAN_EAT_OTHERS_BALLS = 1;
    public static final int BONUS_INVINCIBILITY = 2;
    // attention ne pas se tromper dans le nombre de bonus pour avoir qu'ils apparaissent tous
    public static final int NUMBER_OF_BONUS = 3;
    private static final String TAG = "Bonus";
    private static final int COLOR_BONUS = Color.GREEN;

    public Bonus(GameActivity gameActivity, float left, float top, float right, float bottom, long duration, int which) {
        super(gameActivity, left, top, right, bottom, COLOR_BONUS, duration, which);
        Log.d(TAG, "Bonus() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "], duration = [" + duration + "], which = [" + which + "]");
    }


    // bonus au hasard
    public static Bonus randomBonus(GameActivity gameActivity) {
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
        which = r.nextInt(NUMBER_OF_BONUS);

        return new Bonus(gameActivity, left, top, right, bottom, DURATION, which);
    }

    /**
     * FONCTION DEBOGGAGE
     */

    // fonction deboggage
    public static Bonus debugWhichBonus(GameActivity gameActivity, int which) {
        Bonus bonus = randomBonus(gameActivity);
        bonus.setWhich(which);
        return bonus;
    }

    // Déclenche le bonus
    @Override
    synchronized public void run() {
        switch (this.getWhich()) {

            case BONUS_STOP_IA_BALLS:
                setBonusStopIaBalls();
                break;

            case BONUS_YOU_CAN_EAT_OTHERS_BALLS:
                setBonusYouCanEatOthersBalls();
                break;

            case BONUS_INVINCIBILITY:
                setBonusInvincibility();
                break;

            default:
                Log.e(TAG, "run: pas d'action défini pour ce bonus : " + getWhich());
                break;

        }
    }

    /**
     * Arrete toutes les balles ennemies pendant un certains temps
     */
    public void setBonusStopIaBalls() {
        Log.d(TAG, "setBonusStopIaBalls() called");
        getGameActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "stopping balls", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<EnnemyBalle> ennemyBalleArrayList = getGameActivity().getEnnemyBalleArrayList();
        for (EnnemyBalle ennemyBalle : ennemyBalleArrayList) { // mettre en pause les balles
            ennemyBalle.pause();
        }

        try { // attendre la durée du bonus
            Thread.sleep(getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (EnnemyBalle ennemyBalle : ennemyBalleArrayList) { // relancer les balles
            ennemyBalle.resume();
        }
    }

    public void setBonusYouCanEatOthersBalls() {
        // a ne pas faire tout de suite
        //  voir invincibility
    }

    public void setBonusInvincibility() {
        Log.d(TAG, "setBonusInvincibility() called");
    }
}
