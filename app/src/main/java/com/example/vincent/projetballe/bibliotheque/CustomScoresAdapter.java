package com.example.vincent.projetballe.bibliotheque;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vincent.projetballe.R;
import com.example.vincent.projetballe.model.Joueur;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet d'afficher les scores de façon personnalisé
 */
public class CustomScoresAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Joueur> lesJoueurs;

    public CustomScoresAdapter(@NonNull Context context, @NonNull List<Joueur> lesJoueurs) {
        super(context, R.layout.row_scores_adapter, lesJoueurs);
        this.context = context;
        this.lesJoueurs = (ArrayList<Joueur>) lesJoueurs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // parcourir chaque joueur
        Joueur unJoueur = lesJoueurs.get(position);
        // récupérer la row personnalisé pour la listView
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customRow = inflater.inflate(R.layout.row_scores_adapter, parent, false);
        // Récupérer les widgets de la custom row
        TextView firstLine = (TextView) customRow.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) customRow.findViewById(R.id.secondLine);
        firstLine.setText(unJoueur.getNom());
        secondLine.setText("" + unJoueur.getScore());
        return customRow;
    }

}
