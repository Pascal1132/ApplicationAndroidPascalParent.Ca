/*
 * Copyright (c) 2020. pascalparent.ca pour android
 */

package ca.pascalparent.pascalparentca;

import java.util.ArrayList;

public class Session {


    public Session ( ) {
    }

    private static int id;
    private static String prenom;
    private static String nom;
    private static String pseudo;
    private static String courriel;
    private static String couleur;
    private static String photo;
    private static ArrayList<Integer> amis;

    public static String getPhoto ( ) {
        return photo;
    }

    public static void setPhoto ( String photo ) {
        Session.photo = photo;
    }



    public static void SessionInit (int id, String prenom, String nom, String pseudo, String courriel, String photo, String couleur, ArrayList<Integer> amis) {
        Session.id = id;
        Session.prenom = prenom;
        Session.nom = nom;
        Session.pseudo = pseudo;
        Session.courriel = courriel;
        Session.photo = photo;
        Session.couleur = couleur;
        Session.amis = amis;
    }





    public static int getId ( ) {
        return id;
    }

    public static void setId ( int id ) {
        Session.id = id;
    }

    public static String getPrenom ( ) {
        return prenom;
    }

    public static void setPrenom ( String prenom ) {
        Session.prenom = prenom;
    }

    public static String getNom ( ) {
        return nom;
    }

    public static void setNom ( String nom ) {
        Session.nom = nom;
    }

    public static String getCourriel ( ) {
        return courriel;
    }

    public static void setCourriel ( String courriel ) {
        Session.courriel = courriel;
    }

    public static String getCouleur ( ) {
        return couleur;
    }

    public static void setCouleur ( String couleur ) {
        Session.couleur = couleur;
    }

    public static ArrayList<Integer> getAmis ( ) {
        return amis;
    }

    public static void setAmis ( ArrayList<Integer> amis ) {
        Session.amis = amis;
    }
}
