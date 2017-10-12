package com.example.vincent.projetballe.model.LesBalles;


import android.graphics.Color;
import android.view.View;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses coordonées
 */
public class IABalle extends Balle implements Runnable {

    // ia color = black
    protected static int color = Color.BLACK;

    public IABalle(UserBalle userBalle, View view, int radius) {
        super(1, 1, radius, color);
    }

    @Override
    public void run() {

    }
}
