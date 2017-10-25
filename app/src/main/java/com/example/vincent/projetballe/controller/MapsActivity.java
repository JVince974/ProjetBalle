package com.example.vincent.projetballe.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.model.Joueur;
import com.example.vincent.projetballe.model.ScoresXML;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        position = intent.getIntExtra(ScoresActivity.MY_INTENT_EXTRA_DISPLAY_PLAYER_POSITION, 0);
        Log.v(getClass().getSimpleName(), "DisplayPlayerId : " + position);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Joueur> lesJoueurs = ScoresXML.getLesJoueurs(this);

        for (int i = 0; i < lesJoueurs.size(); i++) {
            Joueur unJoueur = lesJoueurs.get(i);
            LatLng latLng = new LatLng(unJoueur.getLatitude(), unJoueur.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(unJoueur.getNom() + ", Score :" + unJoueur.getScore())
                    .snippet("latitude : " + unJoueur.getLatitude() + " , longitude : " + unJoueur.getLongitude())
            );
            // bouger la caméra sur le score actuel
            if (i == position) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                marker.showInfoWindow();
            }

        }
    }
}
