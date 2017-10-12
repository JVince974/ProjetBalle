package com.example.vincent.projetballe.model;

import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

import java.util.ArrayList;

/**
 * Cette classe contient la liste des tous les objets du plateau de jeu
 */
abstract public class GameModel {

    // les balles
    public static UserBalle userBalle;
    public static ArrayList<IABalle> listIABalles;

    public static void onCreate() {
        listIABalles = new ArrayList<>();
    }

    public static void onDestroy() {
        userBalle = null;
        listIABalles = null;
    }

//    public static void setUserBalle(UserBalle userBalle) {
//        GameModel.userBalle = userBalle;
//    }

//    public static UserBalle getUserBalle() {
//        return userBalle;
//    }

    public static void addIABalle(IABalle balle) {
        listIABalles.add(balle);
    }

    public static ArrayList<IABalle> getIABalles() {
        return listIABalles;
    }


    /**
     * Méthode qui va créer des balles IA jusqu'à atteindre le nombre demandé
     *
     * @param nbBalles nombre de balles IA à instancier
     */
    public static void createIABalles(int nbBalles) {
//        while (listIABalles.size() < nbBalles) {
//            listIABalles.add(new IABalle());
//        }
    }
}
