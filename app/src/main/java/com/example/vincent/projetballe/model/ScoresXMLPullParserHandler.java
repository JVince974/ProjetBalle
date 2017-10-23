package com.example.vincent.projetballe.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Cette classe permet de sauvegarder les scores dans un fichier XML
 */
public class ScoresXMLPullParserHandler {

    // contient la liste des joueurs
    private ArrayList<Joueur> listJoueurs;

    public ScoresXMLPullParserHandler(InputStream inputStream) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            readRoot(parser);

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

    }

    // remplir la liste des joueurs
    private void readRoot(XmlPullParser parser) throws IOException, XmlPullParserException {
        listJoueurs = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, null, "root");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals(Joueur.TAG)) {
                listJoueurs.add(readJoueur(parser));
            } else {
                skip(parser);
            }
        }
    }

    // analyse chaque balise joueur, créer le nom, le score et les autres tag automatiquement
    private Joueur readJoueur(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, Joueur.TAG);
        String nom = null;
        int score = 0;
        double latitude = 0;
        double longitude = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals(Joueur.TAG_NOM)) {
                nom = readTag(parser, Joueur.TAG_NOM);
            } else if (tag.equals(Joueur.TAG_SCORE)) {
                score = Integer.parseInt(readTag(parser, Joueur.TAG_SCORE));
            } else if (tag.equals(Joueur.TAG_LATITUDE)) {
                latitude = Double.parseDouble(readTag(parser, Joueur.TAG_LATITUDE));
            } else if (tag.equals(Joueur.TAG_LONGITUDE)) {
                longitude = Double.parseDouble(readTag(parser, Joueur.TAG_LONGITUDE));
            } else {
                skip(parser);
            }
        }
        return new Joueur(nom, score, latitude, longitude);
    }

    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tag);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tag);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skip Tags You Don't Care About
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


    public ArrayList<Joueur> getListJoueurs() {
        return listJoueurs;
    }
}

