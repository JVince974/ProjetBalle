package com.example.vincent.projetballe.model.GameObject.lesBalles;


import android.graphics.Color;
import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

import java.util.Random;

/**
 * Ces balles entourent la balle utilisateur un tape sur l'écran les fais partir
 */
public class ShieldBalle extends IABalle {

    private static final String TAG = "ShieldBalle";
    private static final int COLOR_BALL = Color.BLUE;

    public static final int SHIELD_LEFT = 0;
    public static final int SHIELD_RIGHT = 1;
    public static final int SHIELD_UP = 2;
    public static final int SHIELD_DOWN = 3;

    // attributs
    private int distXFromUserBalle, distYFromUserBalle; // distance avec la balle utilisateur
    private boolean defending = true; // a false la balle devient autonome et va se déplacer toute seule jusqu'à ce qu'elle se détruisent


    public ShieldBalle(GameActivity gameActivity, int posX, int posY, int radius, int maxWidth, int maxHeight, int directionX, int directionY, int stepX, int stepY, int distXFromUserBalle, int distYFromUserBalle) {
        super(gameActivity, posX, posY, radius, COLOR_BALL, maxWidth, maxHeight, directionX, directionY, stepX, stepY);
        this.distXFromUserBalle = distXFromUserBalle;
        this.distYFromUserBalle = distYFromUserBalle;
        Log.d(TAG, "ShieldBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "], directionX = [" + directionX + "], directionY = [" + directionY + "], stepX = [" + stepX + "], stepY = [" + stepY + "], distXFromUserBalle = [" + distXFromUserBalle + "], distYFromUserBalle = [" + distYFromUserBalle + "]");
    }


    /**
     * Déplacement automatique de la balle
     * Gère les collisions contre le mur
     */
    @Override
    synchronized public void run() {
        appear();
        UserBalle userBalle = getGameActivity().getUserBalle();

        while (true) {
            while (isRunning()) {

                // mode défense
                if (defending) {
                    setPosX(userBalle.getPosX() + distXFromUserBalle);
                    setPosY(userBalle.getPosY() + distYFromUserBalle);

                    // réduire le taux de rafraichissment
                    try {
                        Thread.sleep(10);  // milliseconds
                    } catch (InterruptedException e) {
                        onInterrupt();
                    }
                }

                // mode attaque
                else {

                    // la balle bouge automatiquement
                    animateMove();

                    // réduire le taux de rafraichissment
                    try {
                        Thread.sleep(30);  // milliseconds
                    } catch (InterruptedException e) {
                        onInterrupt();
                        return;
                    }
                }
            }


            // waiting
            try {
                wait();  // milliseconds
            } catch (InterruptedException e) {
                onInterrupt();
                return;
            }

        }
    }


    /**
     * Vous devez fournir la position de la balle
     * Génére une balle avec une vitesse aléatoire
     * choisit sa direction
     * choisit son pas
     *
     * @param radius le rayon de la balle
     */
    public static ShieldBalle randomShieldBalle(GameActivity gameActivity, int radius, int posX, int posY) {
        Random r = new Random();
        UserBalle userBalle = gameActivity.getUserBalle();

        int maxWidth = gameActivity.getViewWidth();
        int maxHeight = gameActivity.getViewHeight();

        // si x est négatif la balle se déplace a gauche, sinon droite
        // si y est négatif la balle se déplace en haut, sinon bas
        int directionX = RANDOM_DIRECTION[r.nextInt(RANDOM_DIRECTION.length)];
        int directionY = RANDOM_DIRECTION[r.nextInt(RANDOM_DIRECTION.length)];

        // choisir un pas au hasard
        int stepX = RANDOM_STEP[r.nextInt(RANDOM_STEP.length)];
        int stepY = RANDOM_STEP[r.nextInt(RANDOM_STEP.length)];

        int distXFromUserBalle = posX - userBalle.getPosX();
        int distYFromUserBalle = posY - userBalle.getPosY();

        return new ShieldBalle(gameActivity, posX, posY, radius, maxWidth, maxHeight, directionX, directionY, stepX, stepY, distXFromUserBalle, distYFromUserBalle);
    }


    public boolean isDefending() {
        return defending;
    }


    public void setDefending(boolean defending) {
        this.defending = defending;
    }
}
