package com.example.vincent.projetballe.model.GameObject.lesBonus;

import android.graphics.Color;
import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

import java.util.Random;

public class Bonus extends BonusMalus {
    // bonus
    public static final int BONUS_STOP_IA_BALLS = 0;
    public static final int BONUS_YOU_CAN_EAT_OTHERS_BALLS = 1;
    public static final int BONUS_INVINCIBILITY = 2;
    // attention ne pas se tromper dans le nombre de bonus pour avoir qu'ils apparaissent tous
    public static final int NUMBER_OF_BONUS = 3;
    private static final String TAG = "Bonus";
    private static final int COLOR_BONUS = Color.GREEN;

    public Bonus(GameActivity gameActivity, float left, float top, float right, float bottom, long duration, int which) {
        super(gameActivity, left, top, right, bottom, COLOR_BONUS, duration, which);
        Log.d(TAG, "Bonus() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "], duration = [" + duration + "], which = [" + which + "]");
    }


    // bonus au hasard
    public static Bonus randomBonus(GameActivity gameActivity) {
        Random r = new Random();
        int left, top, right, bottom;
        int which;

        int maxWidth = gameActivity.getViewWidth();
        int maxHeight = gameActivity.getViewHeight();

        // générer des coordonées aléatoire pour le bonus
        // ne doit pas dépasser la taille de l'écran
        left = r.nextInt(maxWidth - LONGUEUR_COTE);
        top = r.nextInt(maxHeight - LONGUEUR_COTE);
        right = left + LONGUEUR_COTE;
        bottom = top + LONGUEUR_COTE;

        // quel bonus activer
        which = r.nextInt(NUMBER_OF_BONUS);

        return new Bonus(gameActivity, left, top, right, bottom, DURATION, which);
    }

    // fonction deboggage
    public static Bonus debugWhichBonus(GameActivity gameActivity, int which) {
        Bonus bonus = randomBonus(gameActivity);
        bonus.setWhich(which);
        return bonus;
    }

    // Déclenche le bonus
    @Override
    synchronized public void run() {
        switch (this.getWhich()) {
            case BONUS_STOP_IA_BALLS:


                break;


            case BONUS_YOU_CAN_EAT_OTHERS_BALLS:


                break;


            case BONUS_INVINCIBILITY:


                break;

            default:
                Log.e(TAG, "run: pas d'action défini pour ce bonus : " + getWhich());
                break;
        }
    }

    public void setBonusStopIaBalls() {

    }

    public void setBonusYouCanEatOthersBalls() {

    }


    // TODO: 30/10/2017 toDelete
////    public static void playBonus(Bonus bonus) {
////        Log.d(TAG, "playBonus() called with: bonus = [" + bonus + "]");
////        if (bonus.getType() == TYPE_BONUS) {
////            switch (bonus.getValue()) {
////
////                case BONUS_STOP_IA_BALLS:
////                    setStopIaBalles();
////                    break;
////
////                case YOU_CAN_EAT_OTHERS_BALLS:
////                    setYouCanEatOtherBall();
////                    break;
////
////                default:
////                    Log.e(TAG, "playBonus: Bonus non défini : " + bonus.getValue());
////                    break;
////            }
////        } else if (bonus.getType() == TYPE_MALUS) {
////            switch (bonus.getValue()) {
////
////                case DIVISION:
////                    setDivision();
////                    break;
////
////
////                default:
////                    Log.e(TAG, "playBonus: Malus non défini : " + bonus.getValue());
////                    break;
////            }
////        } else {
////            Log.e(TAG, "playBonus: TYPE doit être doit être égale à -1 ou 1, valeur définie : " + bonus.getType());
////        }
////    }
//
//    // Créer un chronomètre et lancer les bonus/malus
//    @Override
//    public void run() {
//        Log.d(TAG, "run() called");
//
//        GameData.gameActivity.displayCronometer(5000);
//
//        if (this.getType() == TYPE_BONUS) {
//            switch (this.getValue()) {
//                case BONUS_STOP_IA_BALLS:
//                    setStopIaBalles();
//                    break;
//
//                case YOU_CAN_EAT_OTHERS_BALLS:
//                    setYouCanEatOtherBall();
//                    break;
//
//                default:
//                    Log.e(TAG, "playBonus: Bonus non défini : " + this.getValue());
//                    break;
//            }
//        } else if (this.getType() == TYPE_MALUS) {
//            switch (this.getValue()) {
//
//                case DIVISION:
//                    setDivision();
//                    break;
//
//
//                default:
//                    Log.e(TAG, "playBonus: Malus non défini : " + this.getValue());
//                    break;
//            }
//        } else {
//            Log.e(TAG, "playBonus: TYPE doit être doit être égale à -1 ou 1, valeur définie : " + this.getType());
//        }
//    }
//
//    // Bonus method
//
//    private void setStopIaBalles() {
//        Log.d(TAG, "setStopIaBalles() called");
//        gameThread.getGameActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(gameThread.getGameActivity(), "stopping balls", Toast.LENGTH_SHORT).show();
//            }
//        });
//        for (IABalle iaBalle : gameThread.getIABalleArrayList()) { // mettre en pause les balles
//            iaBalle.pause();
//        }
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        for (IABalle iaBalle : gameThread.getIABalleArrayList()) { // relancer les balles
//            iaBalle.resume();
//        }
//    }
//
//    private void setYouCanEatOtherBall() {
//        Log.d(TAG, "setYouCanEatOtherBall() called");
//    }
//
//
//    // malus method
//
//    private void setDivision() {
//        Log.d(TAG, "setDivision() called");
//        gameThread.getGameActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(gameThread.getGameActivity(), "more balls", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ArrayList<IABalle> mIABalleArrayList = gameThread.getIABalleArrayList();
//        UserBalle mUserBalle = gameThread.getUserBalle();
//
//        while (mIABalleArrayList.size() < 9) {
//            IABalle iaBalle = IABalle.RandomBalle(mUserBalle.getRadius());
//            mIABalleArrayList.add(iaBalle);
//            iaBalle.start();
//        }
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 3; i < mIABalleArrayList.size(); i++) { // arreter toutes les balles
//            IABalle iaBalle = mIABalleArrayList.get(i);
//            iaBalle.stop();
//            iaBalle.join();
//        }
//
//        mIABalleArrayList.subList(3, 9).clear(); // détruire
//
//    }
//
//



    /*
    * FONCTION DEBOGGAGE
     */

    public void setBonusInvincibility() {

    }

}
