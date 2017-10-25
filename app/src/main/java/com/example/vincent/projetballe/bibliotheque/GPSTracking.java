package com.example.vincent.projetballe.bibliotheque;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Cette classe permet de récupérer la localisation du joueur
 * Assurez-vous d'avoir la permission de localisation
 */
final public class GPSTracking {

    private Context context;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;

    private double latitude;
    private double longitude;

    public GPSTracking(Context context) {
        this.context = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // Update UI with the most recent location.
                refreshLocation(locationResult.getLastLocation());
            }
        };
    }

    public void start() {
        try {
            // Initialize UI with the last known location.
            mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    refreshLocation(location);
                }
            });

            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // rafraichir la location
    private void refreshLocation(Location lastLocation) {
        if (lastLocation != null) {
            Log.v(new Exception().getStackTrace()[0].getMethodName(), toString());
            this.latitude = lastLocation.getLatitude();
            this.longitude = lastLocation.getLongitude();
        } else {
            Log.v(getClass().getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + " : lastLocation is null");
        }
    }


    public void stop() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    @Override
    public String toString() {
        return "GPSTracking{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


}
