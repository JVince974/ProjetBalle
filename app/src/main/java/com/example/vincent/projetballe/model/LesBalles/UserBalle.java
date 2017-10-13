package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;
import android.view.View;

import com.example.vincent.projetballe.view.GameView;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 * Classe Singleton, il n'y a qu'une seule balle utilisateur
 */
public class UserBalle extends Balle {

    private static int COLOR = Color.RED;
    private static UserBalle instance;

    private int step = 5; // pas de la balle

    // constructeur privé
    private UserBalle(View view) {
        // positionne la balle au milieu de l'écran à l'instance
        super(view.getWidth() / 2, view.getHeight() / 2, (int) (view.getWidth() * 4.63 / 100));
        this.color = COLOR;
    }


    public static void setIntance(View view) {
        instance = new UserBalle(view);
    }

    public static UserBalle getInstance() {
        return instance;
    }

    /**
     * Déplacer la balle
     */
    public void move(int x, int y) {
        int newX = this.posX - x * this.step;
        int newY = this.posY + y * this.step;

        // empêcher de dépasser le rebord gauche
        if (newX <= this.radius)
            newX = this.radius;

        // empêcher de dépasser le rebord droit
        if (newX >= GameView.viewWidth - this.radius)
            newX = GameView.viewWidth - this.radius;

        // empêcher de dépasser le rebord haut
        if (newY <= this.radius)
            newY = this.radius;

        // empêcher de dépasser le rebord bas
        if (newY >= GameView.viewHeight - this.radius)
            newY = GameView.viewHeight - this.radius;

        this.posX = newX;
        this.posY = newY;
    }
}
