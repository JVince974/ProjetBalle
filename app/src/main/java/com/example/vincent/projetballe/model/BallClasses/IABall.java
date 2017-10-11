package com.example.vincent.projetballe.model.BallClasses;

import com.example.vincent.projetballe.model.Ball;


/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses coordonées
 */
public class IABall extends Ball implements Runnable {

    public IABall(int x, int y, int radius, int color) {
        super(x, y, radius, color);
    }

    @Override
    public void run() {

    }
}
