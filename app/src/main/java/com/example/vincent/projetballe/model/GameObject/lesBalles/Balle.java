package com.example.vincent.projetballe.model.GameObject.lesBalles;

import android.graphics.Color;
import android.util.Log;

import com.example.vincent.projetballe.model.GameObject.lesBonus.BonusMalus;

public abstract class Balle {
    private static final String TAG = "Balle";
    //    private int[] colors;
//    colors[0] = Color.BLACK;
//    colors[1] = Color.BLUE;
//    colors[2] = Color.CYAN;
//    colors[3] = Color.DKGRAY;
//    colors[4] = Color.GREEN;
//    colors[5] = Color.GRAY;
//    colors[6] = Color.LTGRAY;
//    colors[7] = Color.MAGENTA;
//    colors[8] = Color.RED;
//    colors[9] = Color.YELLOW;
//    colors[10] = Color.WHITE;

    // coordonnées de la balle
    private int posX, posY;
    private int radius; // rayon de la balle
    private int currentRadius;
    private int color;  // couleur de la balle
    private int maxWidth, maxHeight; // limite du déplacment = la taille de l'écran

    private boolean invincible = false;


    public Balle(int posX, int posY, int radius, int color, int maxWidth, int maxHeight) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.currentRadius = 0;
        this.color = color;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    /**
     * Vérifie si une balle a touché une autre balle
     */
    public boolean touched(Balle balle) {
        double distance = Math.sqrt(Math.pow(this.posX - balle.getPosX(), 2) + Math.pow(this.posY - balle.getPosY(), 2));
        return distance < (this.radius + balle.getCurrentRadius());
    }

    /**
     * Vérifie si une balle a touché un bonus ou un malus
     */
    public boolean touched(BonusMalus bonusMalus) {
        int left = (int) bonusMalus.getLeft();
        int top = (int) bonusMalus.getTop();
        int right = (int) bonusMalus.getRight();
        int bottom = (int) bonusMalus.getBottom();

        // si la balle est entre le coté gauche et le coté droit du carré
        if (posX + radius > left && posX - radius < right) {
            // si la balle est entre le côté haut et le côté bas du carré
            if (posY + radius > top && posY - radius < bottom) {
                return true;
            }
        }
        return false;
    }


    /**
     * Fait clignoter la balle
     */
    public void flash() {
        // TODO: 31/10/2017 Implémenter cette méthode
        new Thread(new Runnable() {
            @Override
            public void run() {
                int balleColor = color;
                for (int i = 0; ; i++) {
                    try {
                        color = Color.WHITE;
                        Thread.sleep(10);
                        color = balleColor;
                    } catch (InterruptedException e) {
                        Log.e(TAG, "flash() :: le thread a été arrêté brusquement lors de l'animation de changement de couleur de la balle");
                        color = balleColor;
                        return;
                        // e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Effet d'animation d'apparition de la balle
     */
    public void appear() {
        invincible = true;
        for (int i = 0; i <= radius; i++) {
            try {
                currentRadius = i;
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Log.e(TAG, "appear() :: le thread a été arrêté brusquement lors de l'animation d'apparition de la balle");
                invincible = false;
                return;
                // e.printStackTrace();
            }
        }
        invincible = false;
    }

    /**
     * Effet d'animation disparition de la balle
     */
    public void disappear() {
        invincible = true;
        while (currentRadius > 0) {
            try {
                currentRadius--;
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Log.e(TAG, "disappear() :: le thread a été arrêté brusquement lors de l'animation de disparition de la balle");
                invincible = false;
                return;
                // e.printStackTrace();
            }
        }
        invincible = false;
    }


    public void move(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    /*******************
     * GETTER AND SETTER
     ********************/
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCurrentRadius() {
        return currentRadius;
    }

    public void setCurrentRadius(int currentRadius) {
        this.currentRadius = currentRadius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
}
