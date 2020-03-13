/*
 * Copyright (c) 2020. pascalparent.ca pour android
 */

package ca.pascalparent.pascalparentca;

import android.content.pm.PackageInfo;

import java.util.ArrayList;

import ca.pascalparent.pascalparentca.ObjectClass.ConnexionInfo;

public class Session {

    public static int idAmisConvo = -1;
    public static ConnexionInfo membre;


    



    public Session ( ConnexionInfo info ) {
        Session.membre = info;
    }
}
