package com.example.vincent.projetballe.model.GameObject.lesBalles;

import android.graphics.Color;
import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

import java.util.Random;


public class CatchBalle extends IABalle implements Runnable {

    private static final String TAG = "CatchBalle";
    private static final int COLOR_BALLE = Color.CYAN; // couleur de la balle

    // attribut
    protected int score = 1; // nombre de points que l'on obtient lorsque l'on touche la balle


    public CatchBalle(GameActivity gameActivity, int posX, int posY, int radius, int maxWidth, int maxHeight, int directionX, int directionY, int stepX, int stepY) {
        super(gameActivity, posX, posY, radius, COLOR_BALLE, maxWidth, maxHeight, directionX, directionY, stepX, stepY);
        Log.d(TAG, "CatchBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "], directionX = [" + directionX + "], directionY = [" + directionY + "], stepX = [" + stepX + "], stepY = [" + stepY + "]");
    }


    /**
     * Génére une balle avec une vitesse et position aléatoire
     * Positionne la balle à des coordonées aléatoires
     * choisit sa direction
     * choisit son pas
     *
     * @param radius le rayon de la balle
     */
    public static CatchBalle randomCatchBalle(GameActivity gameActivity, int radius) {
        Random r = new Random();

        int maxWidth = gameActivity.getViewWidth();
        int maxHeight = gameActivity.getViewHeight();

        // générer des coordonées aléatoire pour la balle
        // ne doit pas dépasser la taille de l'écran
        int posX = radius + r.nextInt((maxWidth - radius) - radius);
        int posY = radius + r.nextInt((maxHeight + radius) - radius);

        // si x est négatif la balle se déplace a gauche, sinon droite
        // si y est négatif la balle se déplace en haut, sinon bas
        int directionX = RANDOM_DIRECTION[r.nextInt(RANDOM_DIRECTION.length)];
        int directionY = RANDOM_DIRECTION[r.nextInt(RANDOM_DIRECTION.length)];

        // choisir un pas au hasard
        int stepX = RANDOM_STEP[r.nextInt(RANDOM_STEP.length)];
        int stepY = RANDOM_STEP[r.nextInt(RANDOM_STEP.length)];

        return new CatchBalle(gameActivity, posX, posY, radius, maxWidth, maxHeight, directionX, directionY, stepX, stepY);
    }


    public int getScore() {
        return score;
    }
}
