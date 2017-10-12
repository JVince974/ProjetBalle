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
    private final static int color = Color.BLACK;

    private final static int NORTH_WEST = 0;
    private final static int NORTH_EAST = 1;
    private final static int SUD_EAST = 2;
    private final static int SUD_WEST = 3;

    private int direction;

    private IABalle(int x, int y, int radius) {
        super(x, y, radius, color);
        direction = (int) (Math.random() * 4); // choisir une direction aléatoire
    }

    // les balles ia apparaissent aléatoire en fonction de la position de l'user balle
    public static IABalle createIABalle(UserBalle userBalle, int radius) {
        Random r = new Random();
        int x = (int) radius + r.nextInt((GameView.viewWidth - radius) - radius);
        int y = (int) radius + r.nextInt((GameView.viewHeight + radius) - radius);
        return new IABalle(x, y, radius);
    }

    @Override
    public void run() {
        while (true) {

//            Random r = new Random();
//            int x = (int) radius + r.nextInt((GameView.viewWidth - radius) - radius);
//            int y = (int) radius + r.nextInt((GameView.viewHeight + radius) - radius);
//            this.x = x;
//            this.y = y;


        }
    }
}
