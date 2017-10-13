package com.example.vincent.projetballe.model;

import com.example.vincent.projetballe.model.LesBalles.CatchBall;
import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

import java.util.ArrayList;

/**
 * Cette classe contient la liste des tous les objets du plateau de jeu
 */
abstract public class GameModel {

    // les balles
//    public static UserBalle userBalle; // balle de l'utilisateur
    public static CatchBall catchBall; // balle a attraper
    public static ArrayList<IABalle> listIABalles; // balles adverses

    // créer les tableaux pour sauvegarder les balles
    public static void onCreate() {
        listIABalles = new ArrayList<>();
    }

    // détruire le plateau pour la partie suivante
    public static void onDestroy() {
        for (IABalle uneBalle : listIABalles) {

        }
//        userBalle = null;
        catchBall = null;
        listIABalles = null;
    }

    /**
     * Méthode qui va créer des balles IA jusqu'à atteindre le nombre demandé
     *
     * @param nbBalles nombre de balles IA à instancier
     */
    public static void randomIABalles(int nbBalles) {
        while (listIABalles.size() < nbBalles) {
            listIABalles.add(IABalle.newIABalle(UserBalle.getInstance(), UserBalle.getInstance().getRadius()));
        }
        // lancer les balles
        for (IABalle uneBalle : listIABalles) {
            uneBalle.start();
        }
    }
}
