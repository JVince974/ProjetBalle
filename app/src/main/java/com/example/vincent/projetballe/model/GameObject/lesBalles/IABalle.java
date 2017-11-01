package com.example.vincent.projetballe.model.GameObject.lesBalles;


import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses propres coordonées grace à un thread
 */
public abstract class IABalle extends Balle implements Runnable {

    // mouvement de la balle = x +  step * direction
    // -1 = gauche, 1 = droite
    // -1 = haut, 1 = bas
    final static int[] randomDirection = new int[]{-1, 1};
    final static int[] randomStep = new int[]{1, 2, 3};
    private static final String TAG = "IABalle";

    private GameActivity mGameActivity;
    private int directionX, directionY; // la direction de la balle
    private int stepX, stepY; // le pas de la balle
    private Thread thread; // le thread associé à la balle

    private boolean running = true;


    public IABalle(GameActivity gameActivity, int posX, int posY, int radius, int color, int maxWidth, int maxHeight, int directionX, int directionY, int stepX, int stepY) {
        super(posX, posY, radius, color, maxWidth, maxHeight);
        this.mGameActivity = gameActivity;
        this.directionX = directionX;
        this.directionY = directionY;
        this.stepX = stepX;
        this.stepY = stepY;
        this.thread = new Thread(this);
    }


    /**
     * Déplacement automatique de la balle
     * Gère les collisions contre le mur
     */
    @Override
    synchronized public void run() {
        appear();
        while (true) {
            while (running) {
                int posX = getPosX();
                int posY = getPosY();
                int radius = getCurrentRadius();
                int maxWidth = getMaxWidth();
                int maxHeight = getMaxHeight();

                // déplacer les balles avec leur pas
                posX += this.stepX * this.directionX * mGameActivity.getIaBallSpeed();
                posY -= this.stepY * this.directionY * mGameActivity.getIaBallSpeed();

                // empêcher de dépasser le rebord gauche et rediriger vers la droite
                if (posX <= radius) {
                    posX = radius;
                    this.directionX = Math.abs(this.directionX);
                }

                // empêcher de dépasser le rebord droit et rediriger vers la gauche
                else if (posX >= maxWidth - radius) {
                    posX = maxWidth - radius;
                    this.directionX = -Math.abs(this.directionX);
                }

                // empêcher de dépasser le rebord haut et et rediriger vers le bas
                if (posY <= radius) {
                    posY = radius;
                    this.directionY = -Math.abs(this.directionY);
                }

                // empêcher de dépasser le rebord bas et rediriger vers le haut
                else if (posY >= maxHeight - radius) {
                    posY = maxHeight - radius;
                    this.directionY = Math.abs(this.directionY);
                }

                // déplacer la balle
                move(posX, posY);

                // réduire le taux de rafraichissment
                try {
                    Thread.sleep(30);  // milliseconds
                } catch (InterruptedException e) {
                    Log.d(TAG, "arret du thread de la balle ia");
                    disappear();
                    return;
                }

            }
            try {
                wait();
            } catch (InterruptedException e) {
                Log.d(TAG, "arret du thread de la balle ia");
                disappear();
                return;
            }
        }
    }

    /*
     * GESTION DES THREADS
     */

    public void start() {
        thread.start();
    }

    public void pause() {
        running = false;
    }

    synchronized public void resume() {
        running = true;
        notify();
    }

    public void stop() {
        thread.interrupt();
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
