package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AjouterAmi extends AppCompatActivity {

    private EditText et;
    private ListView lw;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_ajouter_ami );
        et = (( EditText )findViewById ( R.id.searchBar ));
        lw = ((ListView)findViewById ( R.id.ListeAmis ));
        et.setOnKeyListener(new View.OnKeyListener () {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    rechercher ();
                    return true;
                }
                return false;
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
        backgroundWorker.setListView(lw);
        backgroundWorker.execute("recherche",et.getText ().toString ());


    }
}
