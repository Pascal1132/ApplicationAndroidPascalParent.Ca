package ca.pascalparent.pascalparentca;

public class Session {



    private static int id;
    private static String prenom;
    private static String nom;
    private static String courriel;

    public static String getPhoto ( ) {
        return photo;
    }

    public static void setPhoto ( String photo ) {
        Session.photo = photo;
    }

    private static String photo;

    public Session ( ) {
    }

    private static String couleur;



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
}
