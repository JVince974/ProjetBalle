package com.example.vincent.projetballe.model.LesBalles;


import android.graphics.Color;

import com.example.vincent.projetballe.model.GameData;

import java.util.Random;

/**
 * Balle adverse a esquiver qui se déplace automatiquement
 * Elle change elle même ses propres coordonées grace à un thread
 */
public class IABalle extends Balle implements Runnable {
    private final static int COLOR = Color.BLACK; // couleur

    // vitesse de toutes les balles
    public static int speed = 2;

    protected int directionX, directionY; // la direction de la balle
    protected int stepX, stepY; // le pas de la balle
    protected Thread thread; // le thread associé à la balle

    private boolean running = true;


    public IABalle(int posX, int posY, int radius, int directionX, int directionY, int stepX, int stepY) {
        super(posX, posY, radius);
        this.color = COLOR;
        this.directionX = directionX;
        this.directionY = directionY;
        this.stepX = stepX;
        this.stepY = stepY;
        this.thread = new Thread(this);
    }

    /**
     * Génére une balle avec une vitesse et position aléatoire
     *
     * @param radius le rayon de la balle
     * @return
     */
    public static IABalle RandomBalle(int radius) {
        Random r = new Random();
        int[] randomDirection = new int[]{-1, 1};
        int[] randomStep = new int[]{1, 2, 3, 4, 5};

        // générer des coordonées aléatoire pour la balle
        // ne doit pas dépasser la taille de l'écran
        int posX = radius + r.nextInt((GameData.viewWidth - radius) - radius);
        int posY = radius + r.nextInt((GameData.viewHeight + radius) - radius);

        // choisir une direction au hasard
        // si x est négatif la balle se déplace a gauche, sinon droite
        // si y est négatif la balle se déplace en haut, sinon bas
        int directionX = randomDirection[r.nextInt(randomDirection.length)];
        int directionY = randomDirection[r.nextInt(randomDirection.length)];

        // choisir un pas au hasard
        int stepX = randomStep[r.nextInt(randomStep.length)];
        int stepY = randomStep[r.nextInt(randomStep.length)];
        return new IABalle(posX, posY, radius, directionX, directionY, stepX, stepY);
    }

    /**
     * Déplacement automatique de la balle
     */
    @Override
    public synchronized void run() {
        while (true) {
            while (running) {
                // déplacer les balles avec leur pas
                this.posX += this.stepX * this.directionX * speed;
                this.posY -= this.stepY * this.directionY * speed;

                // empêcher de dépasser le rebord gauche et rediriger vers la droite
                if (this.posX <= this.radius) {
                    this.posX = this.radius;
                    this.directionX = -this.directionX;
                }

                // empêcher de dépasser le rebord droit et rediriger vers la gauche
                if (this.posX >= GameData.viewWidth - this.radius) {
                    this.posX = GameData.viewWidth - this.radius;
                    this.directionX = -this.directionX;
                }

                // empêcher de dépasser le rebord haut et et rediriger vers le bas
                if (this.posY <= this.radius) {
                    this.posY = this.radius;
                    this.directionY = -this.directionY;
                }

                // empêcher de dépasser le rebord bas et rediriger vers le haut
                if (this.posY >= GameData.viewHeight - this.radius) {
                    this.posY = GameData.viewHeight - this.radius;
                    this.directionY = -this.directionY;
                }

                // réduire le taux de rafraichissment
                try {
                    Thread.sleep(30);  // milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            // mettre en pause quand la variable running est à false
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // lancer le thread
    public void start() {
        thread.start();
    }

    // mettre le thread en pause
    public void pause() {
        running = false;
    }

    public synchronized void resume() {
        running = true;
        notify();
    }

    public void stop() {

    }

}
