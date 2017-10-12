package com.example.vincent.projetballe.model.LesBalles;

import android.graphics.Color;
import android.view.View;

/**
 * Balle de l'utilisateur, elle se place par défaut au milieu du jeu
 */
public class UserBalle extends Balle {

    // positionne la balle au milieu de l'écran
    public UserBalle(View view) {
        super(view.getWidth() / 2, view.getHeight() / 2, (int) (view.getWidth() * 4.63 / 100), Color.RED);
    }

    
}
