package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Modification extends AppCompatActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_modification );
    }

    public void profil ( View view ) {
        Intent anothercallActivity=new Intent (this,Profil.class);
        startActivity ( anothercallActivity);
    }
}
