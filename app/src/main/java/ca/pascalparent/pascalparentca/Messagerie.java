package ca.pascalparent.pascalparentca;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

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
                erreurConnexion=false;
                tv.setText("");
                webSocketClient.send("SESSION:"+Session.membre.getId() + ":LIST_CONVO_ID");


            }

            @Override
            public void onMessage(String message) {
                String texteDejaPresent = "";
                if(tv.getText().length()>0){
                    texteDejaPresent = tv.getText()+"\n";
                }
                tv.setText(texteDejaPresent+message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {



                switch (code){
                    case 1006:
                        tv.setText("Le serveur n'est pas rejoignable. Code d'erreur:"+code);


                        final Timer timer = new Timer();

                        final TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                webSocketClient.reconnect();
                                if(webSocketClient.isOpen()) {
                                    timer.cancel();
                                    timer.purge();
                                }
                            }
                        };

                        timer.schedule(task, 300);
                        break;
                    case 1000:
                        tv.setText("Votre compte est déjà connecté. \nDéconnecter le avant d'y accéder via cet appareil.");


                        break;
                    default:
                        tv.setText("Kickout par le serveur : "+code);
                        break;
                }






            }

            @Override
            public void onClosing(int code, String reason, boolean remote) {
                webSocketClient.send("CLOSING:"+code);
                super.onClosing(code, reason, remote);

            }

            @Override
            public void onError(Exception ex) {

                erreurConnexion=true;

            }
        };




        webSocketClient.connect();

    }

    public void clickBoutonEnvoye(View view) {
        if(webSocketClient.isOpen()){
            webSocketClient.send("MESSAGE:"+Session.membre.getId()+":"+Session.idAmisConvo+":"+textInput.getText());

            // Faire réapparaitre le clavier
            textInput.setText("");
            textInput.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(textInput, InputMethodManager.SHOW_IMPLICIT);
        }else{

                new AlertDialog.Builder(Messagerie.this)
                        .setTitle("Erreur réseau")
                        .setMessage("Le serveur n'est pas rejoignable.")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setOnDismissListener(new AlertDialog.OnDismissListener(){

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                //createWebSocketClient();

                            }
                        })
                        .setIcon(R.drawable.ic_erreur_reseau)
                        .show();



        }

    }
}
