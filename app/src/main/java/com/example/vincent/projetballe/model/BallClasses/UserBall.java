package com.example.vincent.projetballe.model.BallClasses;

import android.graphics.Color;
import android.view.View;

import com.example.vincent.projetballe.model.Ball;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 */
public class UserBall extends Ball {

    public UserBall(View v) {
        // positionne la balle au milieu de l'écran
        super(v.getWidth() / 2, v.getHeight() / 2, (int) (v.getWidth() * 4.63 / 100), Color.RED);
    }

}
