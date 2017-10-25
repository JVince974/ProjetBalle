package com.example.vincent.projetballe.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vincent.projetballe.R;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // demander acces a la localisation au demarrage
        checkPermissions();
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
        startActivity(intent);
        finish();
    }


    private void debugScores() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    private void debugGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}