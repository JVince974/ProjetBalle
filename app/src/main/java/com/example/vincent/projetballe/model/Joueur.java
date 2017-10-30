package com.example.vincent.projetballe.model;

import android.util.Log;

public class Joueur {

    public static final String TAG = "joueur";
    public static final String TAG_NOM = "nom";
    public static final String TAG_SCORE = "score";
    public static final String TAG_LATITUDE = "latitude";
    public static final String TAG_LONGITUDE = "longitude";

    private String nom;
    private int score;
    private double latitude;
    private double longitude;

    public Joueur(String nom, int score, double latitude, double longitude) {
        this.nom = nom;
        this.score = score;
        this.latitude = latitude;
        this.longitude = longitude;
        Log.d("New", toString());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom + '\'' +
                ", score=" + score +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
