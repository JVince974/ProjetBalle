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

    private boolean canDie=true;

    public UserBalle(int posX, int posY, int radius, int maxWidth, int maxHeight) {
        super(posX, posY, radius, COLOR_BALL, maxWidth, maxHeight);
        Log.d(TAG, "UserBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "]");
    }


    /**
     * Déplacer la balle de l'utilisateur,
     * empêche la balle de dépasser l'écran
     */
    @Override
    public void move(int x, int y) {
        // empêcher de dépasser le rebord gauche
        if (x <= this.getRadius())
            x = this.getRadius();

            // empêcher de dépasser le rebord droit
        else if (x >= this.getMaxWidth() - this.getRadius())
            x = this.getMaxWidth() - this.getRadius();

        // empêcher de dépasser le rebord haut
        if (y <= this.getRadius())
            y = this.getRadius();

            // empêcher de dépasser le rebord bas
        else if (y >= this.getMaxHeight() - this.getRadius())
            y = this.getMaxHeight() - this.getRadius();

        super.move(x, y);
    }


    public int getSpeed() {
        return speed;
    }

    public boolean getCanDie(){return canDie;}

    public void setCanDie(boolean canDieChange){this.canDie = canDieChange;}



}
