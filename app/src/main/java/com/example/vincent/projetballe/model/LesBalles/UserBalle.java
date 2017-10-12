package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;
import android.view.View;

import com.example.vincent.projetballe.view.GameView;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 */
public class UserBalle extends Balle {

    private static int COLOR = Color.RED;

    public UserBalle(View view) {
        // positionne la balle au milieu de l'écran à l'instance
        super(view.getWidth() / 2, view.getHeight() / 2, (int) (view.getWidth() * 4.63 / 100));
        this.color = COLOR;
    }

    @Override
    public void move(int x, int y) {
        int newX = this.posX - x * this.speed;
        int newY = this.posY + y * this.speed;

        // empêcher de dépasser le rebord gauche
        if (newX <= radius)
            newX = radius;

        // empêcher de dépasser le rebord droit
        if (newX >= GameView.viewWidth - radius)
            newX = GameView.viewWidth - radius;

        // empêcher de dépasser le rebord haut
        if (newY <= radius)
            newY = radius;

        // empêcher de dépasser le rebord bas
        if (newY >= GameView.viewHeight - radius)
            newY = GameView.viewHeight - radius;

        this.posX = newX;
        this.posY = newY;
    }
}
