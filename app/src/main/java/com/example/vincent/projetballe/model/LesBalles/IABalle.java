package com.example.vincent.projetballe.model.LesBalles;


import android.graphics.Color;

import com.example.vincent.projetballe.view.GameView;

import java.util.Random;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses propres coordonées grace à un thread
 */
public class IABalle extends Balle implements Runnable {

    private final static int COLOR = Color.BLACK; // couleur de la balle

    // variables de direction de la balle
    private final static int NORTH_WEST = 0;
    private final static int NORTH_EAST = 1;
    private final static int SOUTH_EAST = 2;
    private final static int SOUTH_WEST = 3;

    private int direction; // la balle se dirige dans une direction au hasard
    private Thread t; // le thread associé à la balle

    /**
     * Méthode privé, utiliser randomIABalle pour obtenir une balle
     */
    private IABalle(int x, int y, int radius) {
        super(x, y, radius);
        this.color = COLOR;
        this.direction = (int) (Math.random() * 4); // choisir une direction aléatoire
        this.t = new Thread(this);  // attaché son thread
    }


    // les balles ia apparaissent aléatoire en fonction de la position de l'user balle
    public static IABalle randomIABalle(UserBalle userBalle, int radius) {
        // générer des coordonées aléatoire pour la balle
        Random r = new Random();
        int x = radius + r.nextInt((GameView.viewWidth - radius) - radius);
        int y = radius + r.nextInt((GameView.viewHeight + radius) - radius);
        return new IABalle(x, y, radius);
    }

    @Override
    public void run() {
//        while (true) {

//            Random r = new Random();
//            int posX = (int) radius + r.nextInt((GameView.viewWidth - radius) - radius);
//            int posY = (int) radius + r.nextInt((GameView.viewHeight + radius) - radius);
//            this.posX = posX;
//            this.posY = posY;


//        }
    }

    // lancer le thread
    public void start() {
        this.t.start();
    }

}
