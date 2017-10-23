package com.example.vincent.projetballe.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.model.Joueur;
import com.example.vincent.projetballe.model.ScoresXMLPullParserHandler;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    private ListView mListViewScores;
    private ScoresXMLPullParserHandler mScoresXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListViewScores = (ListView) findViewById(R.id.listview_scores_adapter);
        // parser le fichier des scores
        mScoresXML = new ScoresXMLPullParserHandler(getResources().openRawResource(R.raw.scores));

        ArrayList<Joueur> listJoueur = mScoresXML.getListJoueurs();
        for (Joueur joueur : listJoueur) {
            Log.v("Joueur", joueur.toString());
        }
    }

}
