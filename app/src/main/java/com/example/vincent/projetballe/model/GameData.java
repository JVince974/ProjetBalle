package com.example.vincent.projetballe.model;


import com.example.vincent.projetballe.model.LesBalles.CatchBall;
import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

import java.util.ArrayList;


/**
 * Toutes les données du jeu sont sauvegardés dans cette classes
 * les balles, le score, la taille de l'écran, etc...
 */
abstract public class GameData {

    // taille de l'écran
    public static int viewWidth;
    public static int viewHeight;

    public static int score = 0;

    // les balles du jeu
    public static UserBalle userBalle;
    public static CatchBall catchBall;
    public static ArrayList<IABalle> listIABalles;


    public static void clear() {
        userBalle = null;
        catchBall = null;
        listIABalles = null;
    }

}
