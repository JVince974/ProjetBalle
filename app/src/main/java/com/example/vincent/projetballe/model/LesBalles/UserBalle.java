package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;
import android.view.View;

import com.example.vincent.projetballe.view.GameView;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 */
public class UserBalle extends Balle {

    private static int color = Color.RED;

    // positionne la balle au milieu de l'écran
    public UserBalle(View view) {
        super(view.getWidth() / 2, view.getHeight() / 2, (int) (view.getWidth() * 4.63 / 100), color);
    }

    public void setX(int x) {
        int newX = this.x - x * speed;
        // ne doit pas depasser le rebord gauche
        if (newX <= radius) {
            newX = radius;
        }
        // ne doit pas depasser le rebord droit
        if (newX >= GameView.viewWidth - radius) {
            newX = GameView.viewWidth - radius;
        }
        this.x = newX;
    }

    public void setY(int y) {
        int newY = this.y + y * speed;
        // ne doit pas depasser le rebord haut
        if (newY <= radius) {
            newY = radius;
        }
        // ne doit pas depasser le rebord bas
        if (newY >= GameView.viewHeight - radius) {
            newY = GameView.viewHeight - radius;
        }
        this.y = newY;
    }

}
