package com.example.vincent.projetballe.model;

import com.example.vincent.projetballe.model.LesBalles.IABalle;
import com.example.vincent.projetballe.model.LesBalles.UserBalle;

import java.util.ArrayList;

/**
 * Cette classe contient la liste des tous les objets du plateau de jeu
 */
abstract public class GameModel {

    // les balles
    public static UserBalle userBalle; // balle de l'utilisateur
    public static ArrayList<IABalle> listIABalles; // balles adverses

    // créer le plateau
    public static void onCreate() {
        listIABalles = new ArrayList<>();
    }

    // détruire le plateau pour éviter des nouveaux affichages
    public static void onDestroy() {
        userBalle = null;
        listIABalles = null;
    }

    /**
     * Méthode qui va créer des balles IA jusqu'à atteindre le nombre demandé
     *
     * @param nbBalles nombre de balles IA à instancier
     */
    public static void createIABalles(int nbBalles) {
//        while (listIABalles.size() < nbBalles) {
//            listIABalles.add(IABalle.randomIABalle(userBalle, userBalle.getRadius()));
//        }
//        // lancer les balles
//        for (IABalle uneBalle : listIABalles) {
//            Thread t = new Thread(uneBalle);
//            listThread.add(new Thread(uneBalle));
//            t.start();
//        }
    }
}
