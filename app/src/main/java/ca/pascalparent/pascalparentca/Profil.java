package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        newText.setText ( Session.getPrenom () + " "+Session.getNom () );
        ImageView photoProfil = (ImageView) findViewById ( R.id.photoProfil);
        Picasso.get ().load ( "https://pascalparent.ca/reseauSocial/data/utilisateurs/photo_profil/"+Session.getPhoto () ).into ( photoProfil );
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
        Intent anothercallActivity=new Intent (this,Messagerie.class);
        startActivity ( anothercallActivity);
    }
}
