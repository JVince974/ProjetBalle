package com.example.vincent.projetballe.model.GameObject.lesBalles;

import android.graphics.Color;
import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

import java.util.Random;


public class CatchBalle extends IABalle implements Runnable {
    private static final String TAG = "CatchBalle";
    private static final int COLOR_BALLE = Color.CYAN; // couleur de la balle

    public CatchBalle(int posX, int posY, int radius, int maxWidth, int maxHeight, int directionX, int directionY, int stepX, int stepY) {
        super(posX, posY, radius, COLOR_BALLE, maxWidth, maxHeight, directionX, directionY, stepX, stepY);
        Log.d(TAG, "CatchBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "], directionX = [" + directionX + "], directionY = [" + directionY + "], stepX = [" + stepX + "], stepY = [" + stepY + "]");
    }

    // TODO: 30/10/2017 toDelete
//    public CatchBalle(int posX, int posY, int radius, int directionX, int directionY, int stepX, int stepY) {
//        super(posX, posY, radius, directionX, directionY, stepX, stepY);
//        this.color = COLOR_BALLE;
//    }

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
        int directionX = randomDirection[r.nextInt(randomDirection.length)];
        int directionY = randomDirection[r.nextInt(randomDirection.length)];

        // choisir un pas au hasard
        int stepX = randomStep[r.nextInt(randomStep.length)];
        int stepY = randomStep[r.nextInt(randomStep.length)];

        return new CatchBalle(posX, posY, radius, maxWidth, maxHeight, directionX, directionY, stepX, stepY);
    }
}
