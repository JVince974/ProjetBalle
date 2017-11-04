package com.example.vincent.projetballe.model.GameObject.lesBonus;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.bibliotheque.MyChronometer;
import com.example.vincent.projetballe.bibliotheque.MyMediaPlayer;
import com.example.vincent.projetballe.controller.GameActivity;
import com.example.vincent.projetballe.model.GameObject.lesBalles.EnnemyBalle;
import com.example.vincent.projetballe.model.GameObject.lesBalles.UserBalle;

import java.util.ArrayList;
import java.util.Random;

public class Bonus extends BonusMalus {
    private static final String TAG = "Bonus";
    private static final int COLOR_BONUS = Color.GREEN;

    // bonus
    public static final int BONUS_STOP_IA_BALLS = 0;
    public static final int BONUS_INVINCIBILITY = 1;
    public static final int BONUS_EXTRALIFE = 2;
    public static final int BONUS_PROTEIN = 3;

    // attention ne pas se tromper dans le nombre de bonus pour avoir qu'ils apparaissent tous
    public static final int NUMBER_OF_BONUS = 4;

    private MyMediaPlayer mMediaPlayerBonus;


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

        // le bonus extralife ne dure que 1 seconde
        if (which == BONUS_EXTRALIFE) {
            return new Bonus(gameActivity, left, top, right, bottom, 1000, which);
        }

        return new Bonus(gameActivity, left, top, right, bottom, DURATION, which);
    }


    // Déclenche le bonus
    @Override
    synchronized public void run() {
        switch (this.getWhich()) {

            case BONUS_STOP_IA_BALLS:
                setBonusStopIaBalls();
                break;

            case BONUS_INVINCIBILITY:
                setBonusInvincibility();
                break;

            case BONUS_EXTRALIFE:
                setBonusExtralife();
                break;

            case BONUS_PROTEIN:
                setBonusProtein();
                break;

            default:
                Log.e(TAG, "run: pas d'action défini pour ce bonus : " + getWhich());
                break;

        }

        getGameActivity().resetBonusTimer(); // relancer le timer des bonus
    }


    /**
     * Arrete toutes les balles ennemies pendant un certains temps
     */
    public void setBonusStopIaBalls() {
        Log.d(TAG, "setBonusStopIaBalls() called");
        final GameActivity gameActivity = getGameActivity();
        getGameActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(gameActivity, "Stopping balls", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<EnnemyBalle> ennemyBalleArrayList = gameActivity.getEnnemyBalleArrayList();
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


    /**
     * Rends la balle utilisateur invincible
     * Joue une musique d'invincibilité
     * Faire clignoter la balle de toutes les couleurs
     */
    public void setBonusInvincibility() {
        Log.d(TAG, "setBonusInvincibility() called");
        GameActivity gameActivity = getGameActivity();
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Invincibility", Toast.LENGTH_SHORT).show();
            }
        });

        UserBalle mUserBalle = gameActivity.getUserBalle();
        // mettre en pause la musique actuelle et mettre la balle invincible
        gameActivity.getMediaPlayer().pause();
        mUserBalle.setInvincible(true);
        // jouer musique invincibilité
        mMediaPlayerBonus = new MyMediaPlayer(gameActivity, R.raw.invincible);
        mMediaPlayerBonus.start();

        // changer la vitesse et la couleur de la balle
        int defaultSpeed = mUserBalle.getSpeed(); // sauvegarder la vitesse actuelle pour restauration
        mUserBalle.setSpeed(defaultSpeed * 2);
        int[] invincibilityColors = {
                Color.CYAN,
                Color.GREEN,
                Color.DKGRAY,
                Color.MAGENTA,
                Color.YELLOW,
        };


        // changer la couleur de la balle pendant le temps d'invincibilité
        Random r = new Random();
        MyChronometer myChronometer = new MyChronometer(getDuration());
        myChronometer.start();
        while (!myChronometer.hasFinished()) {
            mUserBalle.setColor(invincibilityColors[r.nextInt(invincibilityColors.length)]);
        }

        // remettre la couleur et la vitesse de la balle a défaut
        mUserBalle.setColor(UserBalle.COLOR_BALL);
        mUserBalle.setSpeed(defaultSpeed);
        // enlever l'invincibilité remmetre la musique du jeu normal
        mMediaPlayerBonus.stop();
        gameActivity.getMediaPlayer().resume();
        mUserBalle.setInvincible(false);
    }


    /**
     * Donne + 2 vies au joueur
     * Le bonus ne dure qu'une seconde
     */
    public void setBonusExtralife() {
        Log.d(TAG, "setBonusExtralife() called");

        GameActivity gameActivity = getGameActivity();
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Extra Life !", Toast.LENGTH_SHORT).show();
            }
        });

        int currentLife = gameActivity.getLife();
        gameActivity.setLife(currentLife + 2);
        gameActivity.displayInfoPlayer();

    }

    /**
     * Fait grossir la balle, elle peut manger les autres balles
     */
    public void setBonusProtein() {
        Log.d(TAG, "setBonusProtein() called");

        GameActivity gameActivity = getGameActivity();
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGameActivity(), "Protein", Toast.LENGTH_SHORT).show();
            }
        });

        UserBalle mUserBalle = gameActivity.getUserBalle();
        int radius = mUserBalle.getRadius(); // pour la restauration du rayon
        // Fais grossir la balle
        mUserBalle.animateRadius(mUserBalle.getRadius() * 3);
        mUserBalle.setEatProtein(true);

        try { // attendre la durée du bonus
            Thread.sleep(getDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mUserBalle.animateRadius(radius); // restaurer le rayon
        mUserBalle.setEatProtein(false);
    }


    /**
     * FONCTION DEBOGGAGE
     */

    // fonction deboggage
    public static Bonus debugWhichBonus(GameActivity gameActivity, int which) {
        Bonus bonus = randomBonus(gameActivity);
        bonus.setWhich(which);
        if (which != BONUS_EXTRALIFE) bonus.setDuration(DURATION);
        return bonus;
    }
}
