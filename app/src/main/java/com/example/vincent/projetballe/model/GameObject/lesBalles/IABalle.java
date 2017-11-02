package com.example.vincent.projetballe.model.GameObject.lesBalles;


import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses propres coordonées grace à un thread
 */
public abstract class IABalle extends Balle implements Runnable {
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = -1;
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
                if (posX < radius) {
                    posX = radius;
                    directionX = DIRECTION_RIGHT;
                }

                // empêcher de dépasser le rebord droit et rediriger vers la gauche
                else if (posX > maxWidth - radius) {
                    posX = maxWidth - radius;
                    directionX = DIRECTION_LEFT;
                }

                // empêcher de dépasser le rebord haut et et rediriger vers le bas
                if (posY < radius) {
                    posY = radius;
                    Log.d(TAG, "posY <= radius : oldDirectionY = " + directionY); // TODO: 02/11/2017 todelete
                    directionY = DIRECTION_DOWN;
                    Log.d(TAG, "posY <= radius : newDirectionY = " + directionY); // TODO: 02/11/2017 todelete
                }

                // empêcher de dépasser le rebord bas et rediriger vers le haut
                else if (posY > maxHeight - radius) {
                    posY = maxHeight - radius;
                    Log.d(TAG, "posY >= maxHeight - radius : oldDirectionY = " + directionY); // TODO: 02/11/2017 todelete
                    directionY = DIRECTION_UP;
                    Log.d(TAG, "posY >= maxHeight - radius : newDirectionY = " + directionY); // TODO: 02/11/2017 todelete
                }

                // déplacer la balle
                move(posX, posY);
                // TODO: 02/11/2017 essayer de changer la direction X Y ici

                // réduire le taux de rafraichissment
                try {
                    Thread.sleep(30);  // milliseconds
                } catch (InterruptedException e) {
                    Log.d(TAG, "arret du thread de la balle ia");
                    e.printStackTrace();
                    disappear();
                    return;
                }

            }
            try {
                wait();
            } catch (InterruptedException e) {
                Log.d(TAG, "arret du thread de la balle ia");
                e.printStackTrace();
                disappear();
                return;
            }
        }
    }


    /**
     * Permutte les coordonnées de la balle avec une autre balle
     */
    public void switchBalle(IABalle otherBalle) {
        int posX = otherBalle.getPosX();
        int posY = otherBalle.getPosY();
        int directionX = otherBalle.getDirectionX();
        int directionY = otherBalle.getDirectionY();
        int stepX = otherBalle.getStepX();
        int stepY = otherBalle.getDirectionY();

        // Changer la direction de l'autre balle
//        otherBalle.setPosX(getPosX());
//        otherBalle.setPosY(getPosY());
//        otherBalle.setDirectionX(directionX);
//        otherBalle.setDirectionY(directionY);
//        otherBalle.setStepX(stepX);
//        otherBalle.setStepY(stepY);
        otherBalle.setSwitchDirection(getPosX(), getPosY(), getDirectionX(), getDirectionY(), getStepX(), getStepY());

        // changer les coordonnées de cette balles
//        setPosX(posX);
//        setPosY(posY);
//        setDirectionX(directionX);
//        setDirectionY(directionY);
//        setStepX(stepX);
//        setStepY(stepY);
        this.setSwitchDirection(posX, posY, directionX, directionY, stepX, stepY);
    }


    public void setSwitchDirection(int posX, int posY, int directionX, int directionY, int stepX, int stepY) {
        setPosX(posX);
        setPosY(posY);
        setDirectionX(directionX);
        setDirectionY(directionY);
        setStepX(stepX);
        setStepY(stepY);
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


    //
    // GETTERS AND SETTERS
    //


    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }
}
