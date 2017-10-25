package com.example.vincent.projetballe.model;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.example.vincent.projetballe.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Cette classe permet de sauvegarder les scores dans un fichier XML
 */
final public class ScoresXML {

    private static String fileData = "scores.xml";
    // contient la liste des joueurs
    private static ArrayList<Joueur> lesJoueurs;


    public static ArrayList<Joueur> getLesJoueurs(Context context) {
        if (lesJoueurs == null)
            parse(context.getResources().openRawResource(R.raw.scores));
        return lesJoueurs;
    }


    public static void save(Context context) {
        Log.v(ScoresXML.class.getSimpleName(), new Exception().getStackTrace()[0].getMethodName());
        try {
//            FileOutputStream fos = context.openFileOutput(fileData, context.MODE_PRIVATE);
        } catch (Exception e) {

        }

//        XmlSerializer xmlSerializer = Xml.newSerializer();
//        StringWriter writer = new StringWriter();
//
//        xmlSerializer.setOutput(writer);
//        // start DOCUMENT
//        xmlSerializer.startDocument("UTF-8", true);
//        // open tag: <record>
//        xmlSerializer.startTag("", Study.RECORD);
//        // open tag: <study>
//        xmlSerializer.startTag("", Study.STUDY);
//        xmlSerializer.attribute("", Study.ID, String.valueOf(study.mId));
//
//        // open tag: <topic>
//        xmlSerializer.startTag("", Study.TOPIC);
//        xmlSerializer.text(study.mTopic);
//        // close tag: </topic>
//        xmlSerializer.endTag("", Study.TOPIC);
//
//        // open tag: <content>
//        xmlSerializer.startTag("", Study.CONTENT);
//        xmlSerializer.text(study.mContent);
//        // close tag: </content>
//        xmlSerializer.endTag("", Study.CONTENT);
//
//        // open tag: <author>
//        xmlSerializer.startTag("", Study.AUTHOR);
//        xmlSerializer.text(study.mAuthor);
//        // close tag: </author>
//        xmlSerializer.endTag("", Study.AUTHOR);
//
//        // open tag: <date>
//        xmlSerializer.startTag("", Study.DATE);
//        xmlSerializer.text(study.mDate);
//        // close tag: </date>
//        xmlSerializer.endTag("", Study.DATE);
//
//        // close tag: </study>
//        xmlSerializer.endTag("", Study.STUDY);
//        // close tag: </record>
//        xmlSerializer.endTag("", Study.RECORD);
//
//        // end DOCUMENT
//        xmlSerializer.endDocument();
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


}

