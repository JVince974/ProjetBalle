package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;

import com.example.vincent.projetballe.model.GameData;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 * Classe Singleton, il n'y a qu'une seule balle utilisateur
 */
public class UserBalle extends Balle {
    public final static int COLOR = Color.RED; // couleur

    private int speed = 7;

    // constructeur
    public UserBalle(int posX, int posY, int radius) {
        super(posX, posY, radius);
        this.color = COLOR;
    }

    /**
     * Déplacer la balle de l'utilisateur,
     * empêche la balle de dépasser l'écran
     */
    public void move(int x, int y) {
        int newX = this.posX - x * this.speed;
        int newY = this.posY + y * this.speed;

        // empêcher de dépasser le rebord gauche
        if (newX <= this.radius)
            newX = this.radius;

        // empêcher de dépasser le rebord droit
        if (newX >= GameData.viewWidth - this.radius)
            newX = GameData.viewWidth - this.radius;

        // empêcher de dépasser le rebord haut
        if (newY <= this.radius)
            newY = this.radius;

        // empêcher de dépasser le rebord bas
        if (newY >= GameData.viewHeight - this.radius)
            newY = GameData.viewHeight - this.radius;

        this.posX = newX;
        this.posY = newY;
    }
}
