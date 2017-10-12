package com.example.vincent.projetballe.model.LesBalles;


import android.graphics.Color;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses coordonées
 */
public class IABalle extends Balle implements Runnable {

    // ia color = black
    protected static int color = Color.BLACK;

    public IABalle(UserBalle userBalle, int radius) {
        super(userBalle.getX() + 75, userBalle.getY() + 865, radius, color);
    }

    @Override
    public void run() {

    }
}
