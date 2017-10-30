package com.example.vincent.projetballe.model.GameObject.lesBalles;

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
    private int color;  // couleur de la balle
    private int maxWidth, maxHeight; // limite du déplacment = la taille de l'écran

    public Balle(int posX, int posY, int radius, int color, int maxWidth, int maxHeight) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.color = color;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public boolean touched(Balle balle) {
        double distance = Math.sqrt(Math.pow(this.posX - balle.getPosX(), 2) + Math.pow(this.posY - balle.getPosY(), 2));
        return distance < (this.radius + balle.getRadius());
    }

    // // TODO: 30/10/2017 reactiver
//    public boolean touched(Bonus bonus) {
//        int left = (int) bonus.getLeft();
//        int top = (int) bonus.getTop();
//        int right = (int) bonus.getRight();
//        int bottom = (int) bonus.getBottom();
//
//        // si la balle est entre le coté gauche et le coté droit du carré
//        if (posX + radius > left && posX - radius < right) {
//            // si la balle est entre le côté haut et le côté bas du carré
//            if (posY + radius > top && posY - radius < bottom) {
//                return true;
//            }
//        }
//        return false;
//    }


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
}
