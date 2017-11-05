package com.example.vincent.projetballe.model.GameObject.lesBalles;

import android.util.Log;

import com.example.vincent.projetballe.model.GameObject.lesBonus.BonusMalus;

public abstract class Balle {

    private static final String TAG = "Balle";
    //    private int[] colors;
//    colors[0] = Color.BLACK;  // TODO: 02/11/2017 juste un mémo
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

    // coordonnées du centre de la balle
    private int posX, posY;
    private int defaultRadius, currentRadius; // rayon de la balle
    private int color;  // couleur de la balle
    private int maxWidth, maxHeight; // limite du déplacement = la taille de l'écran

    private boolean animating = false;


    public Balle(int posX, int posY, int defaultRadius, int color, int maxWidth, int maxHeight) {
        this.posX = posX;
        this.posY = posY;
        this.defaultRadius = defaultRadius;
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
        return distance < this.currentRadius + balle.getCurrentRadius();
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
        if (posX + defaultRadius > left && posX - defaultRadius < right) {
            // si la balle est entre le côté haut et le côté bas du carré
            if (posY + defaultRadius > top && posY - defaultRadius < bottom) {
                return true;
            }
        }
        return false;
    }


    /**
     * Effet d'animation d'apparition de la balle
     */
    public void appear() {
        animating = true;
        try {
            for (int i = 0; i <= defaultRadius; i++) {
                currentRadius = i;
                Thread.sleep(2);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "appear() :: le thread a été arrêté brusquement lors de l'animation d'apparition de la balle", e);
        } finally {
            currentRadius = defaultRadius;
            animating = false;
        }
    }


    /**
     * Effet d'animation disparition de la balle
     */
    public void disappear() {
        animating = true;
        try {
            while (currentRadius > 0) {
                currentRadius--;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "disappear() :: le thread a été arrêté brusquement lors de l'animation de disparition de la balle", e);
        } finally {
            currentRadius = 0;
            animating = false;
        }
    }


    /**
     * Change le rayon de la balle de manière animée
     * Fais grandir ou rétrécir la balle selon le rayon demandé
     */
    public void animateRadius(final int radius) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Balle balle = Balle.this;
                try {
                    if (balle.currentRadius < radius) {
                        while (balle.currentRadius < radius) { // faire grossir la balle au rayon demandé
                            currentRadius++;
                            Thread.sleep(2);
                        }
                    } else {
                        while (balle.currentRadius > radius) { // sinon faire rétrécir au rayon demandé
                            currentRadius--;
                            Thread.sleep(2);
                        }
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "animateRadius() :: le thread a été arrêté brusquement lors de l'animation de la balle", e);
                } finally {
                    balle.currentRadius = radius;
                }
            }
        }).start();
    }


    /**
     * Déplace la balle à une position
     */
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


    public int getDefaultRadius() {
        return defaultRadius;
    }


    public void setDefaultRadius(int defaultRadius) {
        this.defaultRadius = defaultRadius;
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


    public boolean isAnimating() {
        return animating;
    }


    public void setAnimating(boolean animating) {
        this.animating = animating;
    }
}
