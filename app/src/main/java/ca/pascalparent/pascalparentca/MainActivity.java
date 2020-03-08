package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText EtMdp, EtIdentifiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtIdentifiant = (EditText)findViewById(R.id.identifiant);
        EtMdp  = (EditText)findViewById(R.id.mdp);
    }
    public void Connexion(View view){
        String identifiant = EtIdentifiant.getText().toString();
        String mdp = EtMdp.getText().toString();
        String type = "connexion";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("connexion",identifiant,mdp);


    }

}
