package com.example.vincent.projetballe.model.LesBalles;


import android.graphics.Color;

import com.example.vincent.projetballe.view.GameView;

import java.util.Random;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses propres coordonées grace à un thread
 */
public class IABalle extends Balle implements Runnable {

    private final static int COLOR = Color.BLACK; // couleur de la balle

    public static int speed = 2; // vitesse de toutes les balles

    private int stepX, stepY; // le pas de la balle
    private int directionX, directionY; // la direction de la balle
    private Thread thread; // le thread associé à la balle


    /**
     * Méthode protégé, utiliser newIABalle pour obtenir une balle
     *
     * @see #newIABalle(UserBalle, int)
     */
    protected IABalle(int x, int y, int radius) {
        super(x, y, radius);
        this.color = COLOR;
        Random r = new Random();
        int[] randomDirection = new int[]{-1, 1};
        int[] randomStep = new int[]{1, 2, 3, 4, 5};
        // choisir une direction au hasard
        // si x est négatif la balle se déplace a gauche, sinon droite
        // si y est négatif la balle se déplace en haut, sinon bas
        this.directionX = randomDirection[r.nextInt(randomDirection.length)];
        this.directionY = randomDirection[r.nextInt(randomDirection.length)];
        // choisir un pas au hasard
        this.stepX = randomStep[r.nextInt(randomStep.length)];
        this.stepY = randomStep[r.nextInt(randomStep.length)];
        // associé son processus pour le déplacement
        this.thread = new Thread(this);
    }

    // les balles ia apparaissent aléatoire en fonction de la position de l'user balle
    public static IABalle newIABalle(UserBalle userBalle, int radius) {
        // générer des coordonées aléatoire pour la balle
        Random r = new Random();
        int x = radius + r.nextInt((GameView.viewWidth - radius) - radius);
        int y = radius + r.nextInt((GameView.viewHeight + radius) - radius);
        return new IABalle(x, y, radius);
    }

    @Override
    public void run() {
        while (true) {
            // déplacer les balles avec leur pas
            this.posX += this.stepX * this.directionX * speed;
            this.posY -= this.stepY * this.directionY * speed;

            // empêcher de dépasser le rebord gauche et rediriger vers la droite
            if (this.posX <= this.radius) {
                this.posX = this.radius;
                this.directionX = -this.directionX;
            }

            // empêcher de dépasser le rebord droit et rediriger vers la gauche
            if (this.posX >= GameView.viewWidth - this.radius) {
                this.posX = GameView.viewWidth - this.radius;
                this.directionX = -this.directionX;
            }

            // empêcher de dépasser le rebord haut et et rediriger vers le bas
            if (this.posY <= this.radius) {
                this.posY = this.radius;
                this.directionY = -this.directionY;
            }

            // empêcher de dépasser le rebord bas et rediriger vers le haut
            if (this.posY >= GameView.viewHeight - this.radius) {
                this.posY = GameView.viewHeight - this.radius;
                this.directionY = -this.directionY;
            }


            // réduire le taux de rafraichissment
            try {
                Thread.sleep(30);  // milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // lancer le thread
    public void start() {
        this.thread.start();
    }

    // mettre le thread en pause
    public void pause() {
        try {
            this.thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
