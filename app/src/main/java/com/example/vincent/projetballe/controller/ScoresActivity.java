package com.example.vincent.projetballe.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.bibliotheque.CustomScoresAdapter;
import com.example.vincent.projetballe.model.Joueur;
import com.example.vincent.projetballe.model.ScoresXML;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public final static String DISPLAY_PLAYER_POSITION = "DISPLAY_PLAYER_POSITION";

    private ListView mListViewScores;
    private ArrayList<Joueur> lesJoueurs;
    private CustomScoresAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListViewScores = (ListView) findViewById(R.id.listview_scores_adapter);
        lesJoueurs = ScoresXML.getLesJoueurs(this);
        // afficher dans la listView
        mAdapter = new CustomScoresAdapter(this, lesJoueurs);
        mListViewScores.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();  // alert la listView en cas de mise a jour de l'adapter
        mListViewScores.setOnItemClickListener(this);
        mListViewScores.setOnItemLongClickListener(this);
    }


    /******************************
     *      LISTVIEW ONCLICK
     ******************************/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v(new Exception().getStackTrace()[0].getMethodName(), "" + position);
        showScorePositionOnMapsActivity(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.v(new Exception().getStackTrace()[0].getMethodName(), "" + position);
        final String[] listActions = {
                getResources().getString(R.string.alert_dialog_show_player_position),
                getResources().getString(R.string.alert_dialog_delete_current_item),
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(listActions, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.v("DialogInterface", "" + which + " : " + listActions[which]);
                switch (which) {
                    case 0:
                        showScorePositionOnMapsActivity(position);
                        break;
                    case 1:

                        break;
                    default:
                        Log.e("DialogInterface", "" + which + " : " + listActions[which] + " : No onClickAction");
                        break;
                }
            }
        });
        builder.create().show();

        return true;
    }

    private void showScorePositionOnMapsActivity(int position) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(DISPLAY_PLAYER_POSITION, position);
        startActivity(intent);
    }

    /******************************
     *      MENU ITEMS
     ******************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // supprimer tous les scores
        if (id == R.id.action_delete_all_scores) {
            lesJoueurs.clear();
            mAdapter.notifyDataSetChanged();
            mListViewScores.invalidate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
