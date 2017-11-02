package com.example.vincent.projetballe.model.GameObject.lesBonus;


import android.util.Log;

import com.example.vincent.projetballe.controller.GameActivity;

import java.util.Random;


/**
 * Cette classe permet de gérer les bonus et les malus
 * elle est représenté par un carré vert sur le jeu
 */
public abstract class BonusMalus implements Runnable {
    public static final long DURATION = 5000; // durée général d'un bonus = 5sec
    private static final String TAG = "BonusMalus";
    private static final int TYPE_MALUS = -1;
    private static final int TYPE_BONUS = 1;
    private static final int[] RANDOM_TYPE = new int[]{TYPE_MALUS, TYPE_BONUS};
    public static int LONGUEUR_COTE = 80; // la longueur d'un cote du carré


    private GameActivity mGameActivity;
    private float left, top, right, bottom; // coordonnées du carré
    private float longueur, largeur; // longueur = right - left, largeur = bottom - top
    private int color;
    private long duration; // durée du bonus
    private int which; // quel bonus a déclencher
    private Thread thread;

    private boolean running = true; // pour mettre le thread en pause

    public BonusMalus(GameActivity gameActivity, float left, float top, float right, float bottom, int color, long duration, int which) {
        mGameActivity = gameActivity;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        this.duration = duration;
        this.which = which;
        this.thread = new Thread(this);
        this.longueur = right - left;
        this.largeur = bottom - top;
    }


    public static BonusMalus randomBonusMalus(GameActivity gameActivity) {
        Random r = new Random();
        int type = RANDOM_TYPE[r.nextInt(RANDOM_TYPE.length)];
        if (type == TYPE_BONUS) {
            return Bonus.randomBonus(gameActivity);
        } else if (type == TYPE_MALUS) {
            return Malus.randomMalus(gameActivity);
        } else {
            Log.e(TAG, "randomBonusMalus: cette méthode doit générer un bonus [" + TYPE_BONUS + "] ou un malus [" + TYPE_MALUS + "], valuer reçu : " + type);
            return null;
        }
    }

     /*
     * GESTION DES THREADS
     */

    public void start() {
        thread.start();
    }

    public void pause() {
        running = false;
    }

    synchronized public void resume() {
        running = true;
        notify();
    }

    public void stop() {
        thread.interrupt();
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void move(float left, float top) {
        this.left = left;
        this.top = top;
        this.right = left + longueur;
        this.bottom = top + largeur;
    }


    /*
     * GETTERS AND SETTERS
     */

    public GameActivity getGameActivity() {
        return mGameActivity;
    }

    public void setGameActivity(GameActivity gameActivity) {
        mGameActivity = gameActivity;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getWhich() {
        return which;
    }

    public void setWhich(int which) {
        this.which = which;
    }

}
