package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;

import com.example.vincent.projetballe.model.GameData;

import java.util.Random;


public class CatchBall extends IABalle implements Runnable {

    private final static int COLOR = Color.CYAN; // couleur de la balle


    public CatchBall(int posX, int posY, int radius, int directionX, int directionY, int stepX, int stepY) {
        super(posX, posY, radius, directionX, directionY, stepX, stepY);
        this.color = COLOR;
    }

    /**
     * Génére une balle avec une vitesse et position aléatoire
     *
     * @param radius le rayon de la balle
     * @return
     */
    public static CatchBall RandomBalle(int radius) {
        Random r = new Random();
        int[] randomDirection = new int[]{-1, 1};
        int[] randomStep = new int[]{1, 2, 3, 4, 5};

        // générer des coordonées aléatoire pour la balle
        // ne doit pas dépasser la taille de l'écran
        int posX = radius + r.nextInt((GameData.viewWidth - radius) - radius);
        int posY = radius + r.nextInt((GameData.viewHeight + radius) - radius);

        // choisir une direction au hasard
        // si x est négatif la balle se déplace a gauche, sinon droite
        // si y est négatif la balle se déplace en haut, sinon bas
        int directionX = randomDirection[r.nextInt(randomDirection.length)];
        int directionY = randomDirection[r.nextInt(randomDirection.length)];

        // choisir un pas au hasard
        int stepX = randomStep[r.nextInt(randomStep.length)];
        int stepY = randomStep[r.nextInt(randomStep.length)];
        return new CatchBall(posX, posY, radius, directionX, directionY, stepX, stepY);
    }
}
