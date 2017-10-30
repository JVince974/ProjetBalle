package com.example.vincent.projetballe.model.GameObject.lesBonus;

/**
 * Cette classe gère les bonus
 */
public class Bonus extends Thread {
//    private static final String TAG = "Bonus";
//
//    private static final int TYPE_MALUS = -1;
//    private static final int TYPE_BONUS = 1;
//    private static final int[] RANDOM_TYPE = new int[]{TYPE_MALUS, TYPE_BONUS};
//
//    // bonus
//    private static final int NUMBER_OF_BONUS = 2;
//    private static final int STOP_IA_BALLES = 0;
//    private static final int YOU_CAN_EAT_OTHER_BALL = 1;
////    private static final int e
//
//    // malus
//    private static final int NUMBER_OF_MALUS = 1;
//    private static final int DIVISION = 0;
//
//
//    private static final int LONGUEUR_COTE = 80; // la longuer d'un cote
//    private static final int COLOR = Color.GREEN;
//
//    private GameThread gameThread;
//    private float left, top, right, bottom;
//    private int type, value; // bonus ou malus
//    private int color;
//
//
////    public static void playBonus(Bonus bonus) {
////        Log.d(TAG, "playBonus() called with: bonus = [" + bonus + "]");
////        if (bonus.getType() == TYPE_BONUS) {
////            switch (bonus.getValue()) {
////
////                case STOP_IA_BALLES:
////                    setStopIaBalles();
////                    break;
////
////                case YOU_CAN_EAT_OTHER_BALL:
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
//                case STOP_IA_BALLES:
//                    setStopIaBalles();
//                    break;
//
//                case YOU_CAN_EAT_OTHER_BALL:
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
//    public Bonus(GameThread gameThread, float left, float top, float right, float bottom, int type, int value) {
//        this.gameThread = gameThread;
//        this.left = left;
//        this.top = top;
//        this.right = right;
//        this.bottom = bottom;
//        this.type = type;
//        this.value = value;
//        this.color = COLOR;
//        Log.d(TAG, toString());
//    }
//
//    public static Bonus randomBonus(GameThread gameThread) {
//        Random r = new Random();
//        int left, top, right, bottom;
//        int type, value;
//
//        // générer des coordonées aléatoire pour le bonus
//        // ne doit pas dépasser la taille de l'écran
//        left = r.nextInt((GameData.viewWidth - LONGUEUR_COTE));
//        top = r.nextInt((GameData.viewHeight - LONGUEUR_COTE));
//        right = left + LONGUEUR_COTE;
//        bottom = top + LONGUEUR_COTE;
//
//        type = RANDOM_TYPE[r.nextInt(RANDOM_TYPE.length)];
//        if (type == TYPE_BONUS)
//            value = r.nextInt(NUMBER_OF_BONUS);
//        else
//            value = r.nextInt(NUMBER_OF_MALUS);
//
////        return new Bonus(left, top, right, bottom, type, value);
//        return new Bonus(gameThread, left, top, right, bottom, TYPE_MALUS, DIVISION);
//    }
//
//    @Override
//    public String toString() {
//        return "Bonus{" +
//                "left=" + left +
//                ", top=" + top +
//                ", right=" + right +
//                ", bottom=" + bottom +
//                ", type=" + type +
//                ", value=" + value +
//                ", color=" + color +
//                '}';
//    }
//
//    //
//    // GETTER AND SETTER
//    //
//
//    public float getLeft() {
//        return left;
//    }
//
//    public void setLeft(float left) {
//        this.left = left;
//    }
//
//    public float getTop() {
//        return top;
//    }
//
//    public void setTop(float top) {
//        this.top = top;
//    }
//
//    public float getRight() {
//        return right;
//    }
//
//    public void setRight(float right) {
//        this.right = right;
//    }
//
//    public float getBottom() {
//        return bottom;
//    }
//
//    public void setBottom(float bottom) {
//        this.bottom = bottom;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//
//    public int getColor() {
//        return color;
//    }
//
//    public void setColor(int color) {
//        this.color = color;
//    }
}
