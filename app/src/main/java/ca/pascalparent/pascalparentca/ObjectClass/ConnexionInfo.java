/*
 * Copyright (c) 2020. pascalparent.ca pour android
 */

package ca.pascalparent.pascalparentca.ObjectClass;

import java.util.ArrayList;

public class ConnexionInfo {
    public int id =0;
    public String nom = "";
    public String prenom = "";
    public String pseudo = "";
    public String courriel = "";
    public String mdp="";
    public String couleur = "";

    public int getId ( ) {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getNom ( ) {
        return nom;
    }

    public void setNom ( String nom ) {
        this.nom = nom;
    }

    public String getPrenom ( ) {
        return prenom;
    }

    public void setPrenom ( String prenom ) {
        this.prenom = prenom;
    }

    public String getPseudo ( ) {
        return pseudo;
    }

    public void setPseudo ( String pseudo ) {
        this.pseudo = pseudo;
    }

    public String getCourriel ( ) {
        return courriel;
    }

    public void setCourriel ( String courriel ) {
        this.courriel = courriel;
    }

    public String getMdp ( ) {
        return mdp;
    }

    public void setMdp ( String mdp ) {
        this.mdp = mdp;
    }

    public String getCouleur ( ) {
        return couleur;
    }

    public void setCouleur ( String couleur ) {
        this.couleur = couleur;
    }




    public String getPhoto ( ) {
        return photo;
    }

    public void setPhoto ( String photo ) {
        this.photo = photo;
    }

    public String photo = "";

    public ArrayList<ArrayList<String>> getAmis ( ) {
        return amis;
    }
    public ArrayList<String> getAmisPrenomNomPseudo ( ) {
        ArrayList<String> amisPrenomNomPseudo = new ArrayList<String> (  );
        for ( int i = 0 ; i < amis.size() ; i++ ) {
            amisPrenomNomPseudo.add ( amis.get ( i ).get ( 1 )+" " +  amis.get ( i ).get ( 2 ) + " (" + amis.get ( i ).get ( 3 ) + ")" );

        }


        return amisPrenomNomPseudo;
    }

    public void setAmis ( ArrayList<ArrayList<String>> amis ) {
        this.amis = amis;
    }

    //Tableau des amis (ligne = ami, colonne = (id, prenom, nom, pseudo, courriel, photo))
    public ArrayList<ArrayList<String>> amis = null;

    public void afficher(){
        System.out.println ( "TEST : " +id+ prenom + " " + nom );

    }
    public ArrayList<Integer> getListeAmisId(){
        ArrayList<Integer> arr = new ArrayList<> (  );
        for ( int i = 0 ; i < amis.size ( ) ; i++ ) {
            arr.add ( Integer.valueOf (  amis.get ( i ).get ( 0 )) );
        }
        return arr;
    }
}
