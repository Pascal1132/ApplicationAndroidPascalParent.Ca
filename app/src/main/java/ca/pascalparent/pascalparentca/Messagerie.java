package ca.pascalparent.pascalparentca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.OkHttpClient;

public class Messagerie extends AppCompatActivity {
    private WebSocketClient webSocketClient;
    public TextView tv;
    private OkHttpClient client;
    public EditText textInput;
    private boolean erreurConnexion = false;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_messagerie );
        tv = (TextView ) findViewById ( R.id.output );
        textInput = (EditText) findViewById(R.id.editTextMessage);
        createWebSocketClient();
        textInput.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode== KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    clickBoutonEnvoye(v);
                }
                return false;
            }
        });
        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    public void profil ( View view ) {
        webSocketClient.close(0,"Le client s'est déconnecté.");
        Intent anothercallActivity=new Intent (this,Profil.class);
        startActivity ( anothercallActivity);
    }



    public void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://pascalparent.ca:8080");
        }
        catch ( URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                erreurConnexion=true;
                webSocketClient.send("SESSION:"+Session.membre.getId() + ":LIST_CONVO_ID");


            }

            @Override
            public void onMessage(String message) {
                tv.setText(tv.getText()+"\n"+message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                webSocketClient.send("CLOSE:"+reason);

            }

            @Override
            public void onError(Exception ex) {
                erreurConnexion = true;
            }
        };

        webSocketClient.connect();

    }

    public void clickBoutonEnvoye(View view) {
        if(webSocketClient.isOpen()){
            webSocketClient.send("MESSAGE:"+Session.membre.getId()+":"+Session.idAmisConvo+":"+textInput.getText());
            textInput.setText("");
        }else{
            createWebSocketClient();
            if(erreurConnexion){
                new AlertDialog.Builder(Messagerie.this)
                        .setTitle("Erreur réseau")
                        .setMessage("Le serveur n'est pas rejoignable.")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setOnDismissListener(new AlertDialog.OnDismissListener(){

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                erreurConnexion = false;

                            }
                        })
                        .setIcon(R.drawable.ic_erreur_reseau)
                        .show();
            }


        }

    }
}
