package com.example.vincent.projetballe.model.LesBalles;

import android.util.Log;

abstract public class Balle {

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
    protected int posX;
    protected int posY;
    protected int radius; // rayon de la balle
    protected int color;  // couleur de la balle

    public Balle(int posX, int posY, int radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        Log.v("NewBalle", toString());
    }


    public boolean touched(Balle balle) {
        double distance = Math.sqrt(Math.pow(this.getPosX() - balle.getPosX(), 2) + Math.pow(this.getPosY() - balle.getPosY(), 2));
        return distance < (this.getRadius() + balle.getRadius());
    }


    @Override
    public String toString() {
        return "Balle{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", radius=" + radius +
                ", color=" + color +
                '}';
    }


    /*******************
     * GETTER AND SETTER
     ********************/
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int x) {
        this.posX = x;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int y) {
        this.posY = y;
    }

}
