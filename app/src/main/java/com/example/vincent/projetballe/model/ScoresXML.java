package com.example.vincent.projetballe.model;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Cette classe permet de sauvegarder les scores dans un fichier XML
 */
final public class ScoresXML {

    private static String fileName = "scores.xml";
    // contient la liste des joueurs
    private static ArrayList<Joueur> lesJoueurs;


    public static ArrayList<Joueur> getLesJoueurs(Context context) {
        // open the file
        if (lesJoueurs == null) {
            lesJoueurs = new ArrayList<>();
//            parse(context.getResources().openRawResource(R.raw.scores)); // au cas où

            File file = new File(context.getExternalCacheDir() + fileName);
            // créer le fichier s'il n'existe pas
            if (file.exists()) {
                Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + ": creating file : " + file.getAbsolutePath());
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // lire le fichier
            try {
                FileInputStream fis = new FileInputStream(file);
                parse(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + ": lesJoueurs : " + lesJoueurs.toString());

        return lesJoueurs;
    }

    /**
     * Créer le fichier xml
     */
    public static void save(Context context) {
        Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
        File file = new File(context.getExternalCacheDir() + fileName);
        Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + ": filepath=" + file.getAbsolutePath());
        try {
            // créer le fichier
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            xmlSerializer.startTag(null, "root");
            for (Joueur joueur : getLesJoueurs(context)) {
                Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + ": saving " + joueur.toString());
                // joueur
                xmlSerializer.startTag(null, "joueur");

                // nom
                xmlSerializer.startTag(null, "nom");
                xmlSerializer.text(joueur.getNom());
                xmlSerializer.endTag(null, "nom");

                // score
                xmlSerializer.startTag(null, "score");
                xmlSerializer.text(String.valueOf(joueur.getScore()));
                xmlSerializer.endTag(null, "score");

                // latitude
                xmlSerializer.startTag(null, "latitude");
                xmlSerializer.text(String.valueOf(joueur.getLatitude()));
                xmlSerializer.endTag(null, "latitude");

                // longitude
                xmlSerializer.startTag(null, "longitude");
                xmlSerializer.text(String.valueOf(joueur.getLongitude()));
                xmlSerializer.endTag(null, "longitude");

                xmlSerializer.endTag(null, "joueur");
            }

            xmlSerializer.endTag(null, "root");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fos.write(dataWrite.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //**********************************
    //
    // PRIVATE
    //
    //**********************************

    private static void parse(InputStream inputStream) {
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
    private static void readRoot(XmlPullParser parser) throws IOException, XmlPullParserException {
        lesJoueurs = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, null, "root");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals(Joueur.TAG)) {
                lesJoueurs.add(readJoueur(parser));
            } else {
                skip(parser);
            }
        }
    }

    // analyse chaque balise joueur, créer le nom, le score et les autres tag automatiquement
    private static Joueur readJoueur(XmlPullParser parser) throws IOException, XmlPullParserException {
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

    private static String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tag);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tag);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skip Tags You Don't Care About
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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

    ///////
    ///////  DEBUG
    ///////


    public static void debugCreateXml(Context context) {
        Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
        File file = new File(context.getExternalCacheDir() + fileName);
        Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName() + ": filepath=" + file.getAbsolutePath());
        try {
            // créer le fichier
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            xmlSerializer.startTag(null, "root");
            // joueur
            xmlSerializer.startTag(null, "joueur");
            // nom
            xmlSerializer.startTag(null, "nom");
            xmlSerializer.text("Test");
            xmlSerializer.endTag(null, "nom");
            // score
            xmlSerializer.startTag(null, "score");
            xmlSerializer.text(String.valueOf("15"));
            xmlSerializer.endTag(null, "score");
            // latitude
            xmlSerializer.startTag(null, "latitude");
            xmlSerializer.text(String.valueOf("15"));
            xmlSerializer.endTag(null, "latitude");
            // longitude
            xmlSerializer.startTag(null, "longitude");
            xmlSerializer.text(String.valueOf("16"));
            xmlSerializer.endTag(null, "longitude");
            xmlSerializer.endTag(null, "joueur");

            // joueur
            xmlSerializer.startTag(null, "joueur");
            // nom
            xmlSerializer.startTag(null, "nom");
            xmlSerializer.text("Toto");
            xmlSerializer.endTag(null, "nom");
            // score
            xmlSerializer.startTag(null, "score");
            xmlSerializer.text(String.valueOf("15"));
            xmlSerializer.endTag(null, "score");
            // latitude
            xmlSerializer.startTag(null, "latitude");
            xmlSerializer.text(String.valueOf("15"));
            xmlSerializer.endTag(null, "latitude");
            // longitude
            xmlSerializer.startTag(null, "longitude");
            xmlSerializer.text(String.valueOf("16"));
            xmlSerializer.endTag(null, "longitude");
            xmlSerializer.endTag(null, "joueur");

            // joueur
            xmlSerializer.startTag(null, "joueur");
            // nom
            xmlSerializer.startTag(null, "nom");
            xmlSerializer.text("Tata");
            xmlSerializer.endTag(null, "nom");
            // score
            xmlSerializer.startTag(null, "score");
            xmlSerializer.text(String.valueOf("15"));
            xmlSerializer.endTag(null, "score");
            // latitude
            xmlSerializer.startTag(null, "latitude");
            xmlSerializer.text(String.valueOf("15"));
            xmlSerializer.endTag(null, "latitude");
            // longitude
            xmlSerializer.startTag(null, "longitude");
            xmlSerializer.text(String.valueOf("16"));
            xmlSerializer.endTag(null, "longitude");
            xmlSerializer.endTag(null, "joueur");

            xmlSerializer.endTag(null, "root");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fos.write(dataWrite.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

