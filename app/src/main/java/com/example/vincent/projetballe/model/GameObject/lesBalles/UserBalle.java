package com.example.vincent.projetballe.model.GameObject.lesBalles;

import android.graphics.Color;
import android.util.Log;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 * Classe Singleton, il n'y a qu'une seule balle utilisateur
 */
public class UserBalle extends Balle {
    private static final String TAG = "UserBalle";
    private static final int COLOR_BALL = Color.RED; // couleur

    private int speed = 5;

    public UserBalle(int posX, int posY, int radius, int maxWidth, int maxHeight) {
        super(posX, posY, radius, COLOR_BALL, maxWidth, maxHeight);
        Log.d(TAG, "UserBalle() called with: posX = [" + posX + "], posY = [" + posY + "], radius = [" + radius + "], maxWidth = [" + maxWidth + "], maxHeight = [" + maxHeight + "]");
    }

    // TODO: 30/10/2017 toDelete
    //    // constructeur
//    public UserBalle(int posX, int posY, int getRadius()) {
//        super(posX, posY, getRadius());
//        this.color = COLOR_BALL;
//        Log.d(TAG, super.toString());
//    }

    /**
     * Déplacer la balle de l'utilisateur,
     * empêche la balle de dépasser l'écran
     */
//    public void move(int x, int y) {
//
//        // empêcher de dépasser le rebord gauche
//        if (x <= this.getRadius())
//            x = this.getRadius();
//
//        // empêcher de dépasser le rebord droit
//        if (x >= maxX - this.getRadius())
//            x = maxX - this.getRadius();
//
//        // empêcher de dépasser le rebord haut
//        if (y <= this.getRadius())
//            y = this.getRadius();
//
//        // empêcher de dépasser le rebord bas
//        if (y >= maxY - this.getRadius())
//            y = maxY - this.getRadius();
//
//
////        int newX = this.posX - x * this.SPEED;
////        int newY = this.posY + y * this.SPEED;
////
////        // empêcher de dépasser le rebord gauche
////        if (newX <= this.getRadius())
////            newX = this.getRadius();
////
////        // empêcher de dépasser le rebord droit
////        if (newX >= maxX - this.getRadius())
////            newX = maxX - this.getRadius();
////
////        // empêcher de dépasser le rebord haut
////        if (newY <= this.getRadius())
////            newY = this.getRadius();
////
////        // empêcher de dépasser le rebord bas
////        if (newY >= maxY - this.getRadius())
////            newY = maxY - this.getRadius();
////
////        this.posX = newX;
////        this.posY = newY;
//    }

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
}
