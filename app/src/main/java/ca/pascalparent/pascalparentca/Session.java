/*
 * Copyright (c) 2020. pascalparent.ca pour android
 */

package ca.pascalparent.pascalparentca;

import java.util.ArrayList;

import ca.pascalparent.pascalparentca.ObjectClass.ConnexionInfo;

public class Session {

    public static ConnexionInfo membre;
    public Session ( ConnexionInfo info ) {
        Session.membre = info;
    }


}
