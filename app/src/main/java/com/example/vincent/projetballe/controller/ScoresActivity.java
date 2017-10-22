package com.example.vincent.projetballe.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.model.ScoresXML;

public class ScoresActivity extends AppCompatActivity {

    private ListView mListViewScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListViewScores = (ListView) findViewById(R.id.listview_scores_adapter);
//        ScoresAdapter scoresAdapter = new ScoresAdapter(getBaseContext())

        //test pour écrire un fichier
        ScoresXML.main();
    }

}
