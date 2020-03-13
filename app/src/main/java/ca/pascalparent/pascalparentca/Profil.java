package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Profil extends AppCompatActivity {


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profil );
        TextView newText = ( TextView ) findViewById ( R.id.titre );
        newText.setText ( Session.membre.getPrenom () + " "+Session.membre.getNom () );
        ImageView photoProfil = (ImageView) findViewById ( R.id.photoProfil);
        Picasso.get ().load ( "https://pascalparent.ca/reseauSocial/data/utilisateurs/photo_profil/"+Session.membre.getPhoto () ).into ( photoProfil );
        ImageView fond = (ImageView) findViewById (R.id.FondColore);
        //fond.setBackgroundColor ( Color.parseColor ( Session.getCouleur ()) );


    }
    public void deconnexion( View view){
        Intent anothercallActivity=new Intent (this,MainActivity.class);
        startActivity ( anothercallActivity);
    }
    public void modification(View view){
        Intent anothercallActivity=new Intent (this,Modification.class);
        startActivity ( anothercallActivity);
    }
    public void messagerie(View view){
        Intent anothercallActivity=new Intent (this, Messagerie.class);
        startActivity ( anothercallActivity);
    }

    public void amis ( View view ) {
        Intent anothercallActivity=new Intent (this,Amis.class);
        startActivity ( anothercallActivity);
    }
}
