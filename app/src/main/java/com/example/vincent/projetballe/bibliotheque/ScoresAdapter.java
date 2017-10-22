package com.example.vincent.projetballe.bibliotheque;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Cette classe permet d'afficher les scores de façon personnalisé
 */
public class ScoresAdapter extends ArrayAdapter {

    private Context context;


    public ScoresAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }
}
