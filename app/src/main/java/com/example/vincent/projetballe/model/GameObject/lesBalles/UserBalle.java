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

    public UserBalle(int posX, int posY, int radius, int maxWidth, int maxHeight) {
        super(posX, posY, radius, COLOR_BALL, maxWidth, maxHeight);
        appear();
        Log.d(TAG, "UserBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "]");
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
