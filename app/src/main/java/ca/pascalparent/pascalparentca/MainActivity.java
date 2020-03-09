package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText EtMdp, EtIdentifiant;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtIdentifiant = (EditText)findViewById(R.id.identifiant);
        EtMdp  = (EditText)findViewById(R.id.mdp);
        settings = getApplicationContext().getSharedPreferences("ConnexionPreferences", 0);
         editor = settings.edit();

        ((CheckBox )findViewById ( R.id.checkBoxLog )).setChecked ( settings.getBoolean ( "isSavedLoggedIn" , false) );

    }
    public void Connexion(View view){
        String identifiant = EtIdentifiant.getText().toString();
        String mdp = EtMdp.getText().toString();
        String type = "connexion";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("connexion",identifiant,mdp);


    }


    public void SaveLoggedIn ( View view ) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("ConnexionPreferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean ( "isSavedLoggedIn", ( (CheckBox )findViewById ( R.id.checkBoxLog )).isChecked () );
        editor.commit ();

// Apply the edits!
        editor.apply();
    }
}
