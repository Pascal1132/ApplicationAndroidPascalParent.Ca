package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.javiersantos.appupdater.AppUpdater;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText EtMdp, EtIdentifiant;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtIdentifiant = (EditText)findViewById(R.id.identifiant);
        EtMdp  = (EditText)findViewById(R.id.mdp);
        settings = getApplicationContext().getSharedPreferences("ConnexionPreferences", 0);
         editor = settings.edit();
        checkBox = ((CheckBox )findViewById ( R.id.checkBoxLog ));
        checkBox.setChecked ( settings.getBoolean ( "isSavedLoggedIn" , false) );

        EtMdp.setText ( settings.getString ( "mdp", "" ) );
        EtIdentifiant.setText ( settings.getString ( "identifiant", "" ) );




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
        if(!checkBox.isChecked ()){
            editor.clear ();
        }
    }

    public void nouvelleMiseAjour ( View view ) {
        String url = "https://github.com/Pascal1132/ApplicationAndroidPascalParent.Ca/raw/master/app/build/outputs/apk/debug/app-debug.apk";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);


    }
}
