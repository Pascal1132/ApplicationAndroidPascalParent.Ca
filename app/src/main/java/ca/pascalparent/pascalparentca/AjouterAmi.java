package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class AjouterAmi extends AppCompatActivity {

    private EditText et;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ajouter_ami );
        et = (( EditText )findViewById ( R.id.searchBar ));
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                rechercher ();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }
    public void annuler ( View view ) {
        Intent anothercallActivity=new Intent (this,Amis.class);
        startActivity ( anothercallActivity);
    }
    public void rechercher(){
        EditText et = ( EditText )findViewById ( R.id.searchBar );
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("recherche",et.getText ().toString ());

    }
}
