package com.example.vincent.projetballe.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.bibliotheque.CustomScoresAdapter;
import com.example.vincent.projetballe.model.Joueur;
import com.example.vincent.projetballe.model.ScoresXML;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final static String DISPLAY_PLAYER_POSITION = "DISPLAY_PLAYER_POSITION";

    private ListView mListViewScores;
    private ArrayList<Joueur> lesJoueurs;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListViewScores = (ListView) findViewById(R.id.listview_scores_adapter);
        // parser le fichier des scores
        ScoresXML.parse(getResources().openRawResource(R.raw.scores));
        lesJoueurs = ScoresXML.getLesJoueurs();
        // afficher dans la listView
        CustomScoresAdapter adapter = new CustomScoresAdapter(this, lesJoueurs);
        mListViewScores.setAdapter(adapter);

        mListViewScores.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v(Thread.currentThread().getStackTrace()[1].getMethodName(), lesJoueurs.get(position).toString());
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(DISPLAY_PLAYER_POSITION, "" + position);
        startActivity(intent);

    }
}
