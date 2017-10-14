package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;


public class CatchBall extends IABalle {

    private final static int COLOR = Color.CYAN; // couleur de la balle


    public CatchBall(int posX, int posY, int radius, int directionX, int directionY, int stepX, int stepY) {
        super(posX, posY, radius, directionX, directionY, stepX, stepY);
    }
}
