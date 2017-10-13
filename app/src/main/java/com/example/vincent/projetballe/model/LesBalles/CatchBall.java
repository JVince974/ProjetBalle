package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;

import com.example.vincent.projetballe.view.GameView;

import java.util.Random;

/**
 * Created by Vincent on 12/10/2017.
 */

public class CatchBall extends IABalle {

    private final static int COLOR = Color.CYAN; // couleur de la balle

    protected CatchBall(int x, int y, int radius) {
        super(x, y, radius);
        this.color = COLOR;
    }

    // la balle apparait aléatoire en fonction de la position de l'user balle
    public static CatchBall newCatchBalle(UserBalle userBalle, int radius) {
        // générer des coordonées aléatoire pour la balle
        Random r = new Random();
        int x = radius + r.nextInt((GameView.viewWidth - radius) - radius);
        int y = radius + r.nextInt((GameView.viewHeight + radius) - radius);
        return new CatchBall(x, y, radius);
    }
}
