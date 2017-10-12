package com.example.vincent.projetballe.model.LesBalles;


import android.graphics.Color;

import com.example.vincent.projetballe.view.GameView;

import java.util.Random;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses coordonées
 */
public class IABalle extends Balle implements Runnable {

    // ia color = black
    protected static int color = Color.BLACK;

    private IABalle(int x, int y, int radius) {
        super(x, y, radius, color);
    }

    // les balles ia apparaissent aléatoire en fonction de la position de l'user balle
    public static IABalle createIABalle(UserBalle userBalle, int radius) {
        Random r = new Random();
        int x = (int) radius + r.nextInt((GameView.viewWidth - radius) - radius);
        int y = (int) radius + r.nextInt((GameView.viewWidth + radius) - radius);
        return new IABalle(x, y, radius);
    }

    @Override
    public void run() {

    }
}
