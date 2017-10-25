package com.example.vincent.projetballe.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vincent.projetballe.R;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    private final int START_FOR_RESULT_SCORE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // demander acces a la localisation au demarrage
        checkPermissions();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
        if (requestCode == START_FOR_RESULT_SCORE) {
            if (resultCode == Activity.RESULT_OK) {
                int score = data.getIntExtra(GameActivity.MY_INTENT_EXTRA_SCORE, 0);
                Log.v(getClass().getSimpleName(), "Score = " + score);

                // créer le dialog pour sauvegarde le score de l'utlisateur
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder
                        .setView(inflater.inflate(R.layout.dialog_input_score, null))
                        .setPositiveButton(R.string.save, null)
                        .setNegativeButton(R.string.cancel, null)
                        .setCancelable(false)
                ;

                final AlertDialog dialogPseudoInput = builder.create();
                dialogPseudoInput.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {

                        Button btnSave = dialogPseudoInput.getButton(AlertDialog.BUTTON_POSITIVE);
                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText edtPseudoInput = (EditText) dialogPseudoInput.findViewById(R.id.edt_pseudo);
                                String nom = edtPseudoInput.getText().toString();
                                Log.v(getClass().getSimpleName(), "Pseudo = \"" + nom + "\"");
//                                // vérification du champs text
                                if (nom.trim().length() > 0) {
                                    Toast.makeText(getBaseContext(), nom + " is > 0", Toast.LENGTH_LONG).show();
                                    // Créer un joueur et sauvegarder


                                    Log.v(getClass().getSimpleName(), "Saving score...");
                                } else {
                                    edtPseudoInput.setError("test");
                                }
                            }
                        });
                    }
                });
                dialogPseudoInput.show();

            }
            Snackbar.make(findViewById(R.id.layout_main), R.string.game_over, Snackbar.LENGTH_LONG).show();
        }

    }

    /******************************
     *      MENU ITEMS
     ******************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // options
//        if (id == R.id.action_settings) {
//            Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
//            return true;
//        }
        // scores
        if (id == R.id.action_scores) {
            Intent intent = new Intent(this, ScoresActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /******************************
     *      PERMISSION GPS
     ******************************/
    public void checkPermissions() {
        // demander la localisation
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                // Si l'utilisateur refuse, fermer l'application
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getResources().getString(R.string.request_location), Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    /******************************
     *      LANCER LE JEU
     ******************************/
    public void startGame(View view) {
        // Lancer GameActivity
        Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent, START_FOR_RESULT_SCORE);
    }


    /// DEBUG

    private void debugScores() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    private void debugGame() {
        // Lancer GameActivity
        Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent, START_FOR_RESULT_SCORE);
    }
}
