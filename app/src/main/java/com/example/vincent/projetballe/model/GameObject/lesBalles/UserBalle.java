package com.example.vincent.projetballe.model.GameObject.lesBalles;

import android.graphics.Color;
import android.util.Log;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 */
public class UserBalle extends Balle {
    private static final String TAG = "UserBalle";
    private static final int COLOR_BALL = Color.RED; // couleur

    private int speed = 5;

    private boolean flashing = false; // connaitre si la balle est en train de clignoter


    public UserBalle(int posX, int posY, int radius, int maxWidth, int maxHeight) {
        super(posX, posY, radius, COLOR_BALL, maxWidth, maxHeight);
        appear();
        Log.d(TAG, "UserBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "]");
    }


    /**
     * Fait clignoter la balle
     * rend la balle invincible
     */
    public void flash() {
        if (!flashing) {
            flashing = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setInvincible(true); // rendre la balle invincible
                    int balleColor = getColor();

                    for (int i = 0; i < 10; i++) {
                        try {
                            setColor(Color.WHITE);
                            Thread.sleep(100);
                            setColor(balleColor);
                            Thread.sleep(100);

                        } catch (InterruptedException e) {
                            Log.e(TAG, "flash() :: le thread a été arrêté brusquement lors de l'animation de changement de couleur de la balle");
                            setColor(balleColor);
                            setInvincible(false);
                            flashing = false;
                            //  e.printStackTrace();
                            return;
                        }
                    }

                    setInvincible(false);
                    flashing = false;
                }
            }).start();
        }
    }


    /**
     * Déplacer la balle de l'utilisateur,
     * empêche la balle de dépasser l'écran
     */
    @Override
    public void move(int x, int y) {
        // empêcher de dépasser le rebord gauche
        if (x <= this.getCurrentRadius())
            x = this.getCurrentRadius();

            // empêcher de dépasser le rebord droit
        else if (x >= this.getMaxWidth() - this.getCurrentRadius())
            x = this.getMaxWidth() - this.getCurrentRadius();

        // empêcher de dépasser le rebord haut
        if (y <= this.getCurrentRadius())
            y = this.getCurrentRadius();

            // empêcher de dépasser le rebord bas
        else if (y >= this.getMaxHeight() - this.getCurrentRadius())
            y = this.getMaxHeight() - this.getCurrentRadius();

        super.move(x, y);
    }


    public int getSpeed() {
        return speed;
    }


}
