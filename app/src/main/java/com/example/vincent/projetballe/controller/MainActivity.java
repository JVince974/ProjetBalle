package com.example.vincent.projetballe.controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import com.example.vincent.projetballe.bibliotheque.GPSTracking;
import com.example.vincent.projetballe.model.Joueur;
import com.example.vincent.projetballe.model.ScoresXML;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    private final int START_FOR_RESULT_SCORE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // demander acces a la localisation au demarrage
        checkPermissions();
        checkGPSEnabled();
        debug();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }


    /**
     * Lorsque la partie se termine, le score est récupéré et traité ici
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == START_FOR_RESULT_SCORE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(getClass().getSimpleName(), "starting GPS");
                final GPSTracking gpsTracking = new GPSTracking(MainActivity.this);
                gpsTracking.start();
                final int score = data.getIntExtra(GameActivity.MY_INTENT_EXTRA_SCORE, 0);
                Log.d(getClass().getSimpleName(), "Score = " + score);

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
                                Log.d(getClass().getSimpleName(), "Pseudo = \"" + nom + "\"");
//                                // vérification du champs text
                                if (nom.trim().length() > 0) {
                                    Log.d(getClass().getSimpleName(), "Saving score...");
                                    // Créer un joueur et sauvegarder dans la liste des joueurs
                                    double latitude = gpsTracking.getLatitude();
                                    double longitude = gpsTracking.getLongitude();
                                    Joueur joueur = new Joueur(nom, score, latitude, longitude);
                                    ArrayList<Joueur> lesJoueurs = ScoresXML.getLesJoueurs(MainActivity.this);
                                    lesJoueurs.add(joueur);
                                    ScoresXML.save(MainActivity.this);
                                    Log.d(getClass().getSimpleName(), "Save done...");
                                    dialog.dismiss();
                                } else {
                                    edtPseudoInput.setError(getResources().getString(R.string.edit_text_required_pseudo));
                                }
                            }
                        });
                    }
                });
                dialogPseudoInput.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d(getClass().getSimpleName(), "Stoping GPS");
                        gpsTracking.stop();
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


    public void checkGPSEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, getResources().getString(R.string.ask_for_gps), Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    public void checkPermissions() {
        // demander la localisation
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        // demander l'écriture en storage externe
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        // demander la lecture en storage interne
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                // Si l'utilisateur refuse, fermer l'application
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getResources().getString(R.string.request_permission_location), Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            // Si l'utilisateur refuse, fermer l'application
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getResources().getString(R.string.request_permission_read_external_storage), Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            // Si l'utilisateur refuse, fermer l'application
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getResources().getString(R.string.request_permission_write_external_storage), Toast.LENGTH_LONG).show();
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


    private void debug() {
        Log.d(TAG, "debug() called");
        debugGame();
//        ScoresXML.save(this);
//        ScoresXML.debugCreateXml(this);
//        GPSTracking gpsTracking = new GPSTracking(this);
//        gpsTracking.start();
    }
}
