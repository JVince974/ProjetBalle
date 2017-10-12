package com.example.vincent.projetballe.model.LesBalles;


/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses coordonées
 */
public class IABalle extends Balle implements Runnable {

    public IABalle(int x, int y, int radius, int color) {
        super(x, y, radius, color);
    }

    @Override
    public void run() {

    }
}
