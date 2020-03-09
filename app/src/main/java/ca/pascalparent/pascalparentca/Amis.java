package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Amis extends AppCompatActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_amis );
    }

    public void profil ( View view ) {
        Intent anothercallActivity=new Intent (this,Profil.class);
        startActivity ( anothercallActivity);
    }

    public void ajouterAmis ( View view ) {
        Intent anothercallActivity=new Intent (this,AjouterAmi.class);
        startActivity ( anothercallActivity);
    }
}
