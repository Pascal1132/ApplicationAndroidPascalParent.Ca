package ca.pascalparent.pascalparentca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Amis extends AppCompatActivity {
    public static boolean dialogAfficher =false;
    public static ListView lv;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
    creationPage ();





    }

    public void creationPage(){
        setContentView ( R.layout.activity_amis );
        Amis.lv =  findViewById(R.id.ListeAmis);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);

        backgroundWorker.execute("update");



        List<String> your_array_list = Session.membre.getAmisPrenomNomPseudo ();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        Amis.lv.setAdapter(arrayAdapter);


        Amis.lv.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            public void onItemClick( final AdapterView<?> parent, final View view,
                                     final int position, long id) {

                parent.setOnItemLongClickListener ( null );
                final ArrayList<String> amis = Session.membre.getAmis ( ).get ( position );


                ImageView image = new ImageView(Amis.this);
                image.requestLayout ( );
                image.setAdjustViewBounds ( true );
                image.setScaleType ( ImageView.ScaleType.FIT_CENTER );

                Picasso.get ().load ( "https://pascalparent.ca/reseauSocial/data/utilisateurs/photo_profil/"+amis.get ( 5 ) ).into ( image );

                new AlertDialog.Builder(Amis.this)
                        .setView ( image )
                        .setTitle("Profil de l'ami ("+amis.get ( 3 )+")")
                        .setMessage("Prenom : "+ amis.get ( 1 )+"\r\nNom : "+amis.get ( 2))
                        .setIcon( R.drawable.ic_ami)
                        .setPositiveButton("Envoyer un message", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                Intent anothercallActivity=new Intent (Amis.this,Messagerie.class);
                                startActivity ( anothercallActivity);
                            }
                        })
                        .setNegativeButton ( "Supprimer l'amitié" , new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick ( DialogInterface dialog , int which ) {
                                new AlertDialog.Builder(Amis.this)

                                        .setTitle("Supprimer l'amitié?")
                                        .setMessage("Yo big t'es tu sûr que tu veux kill le friendship de ton bro ?")
                                        .setIcon( R.drawable.ic_ami)
                                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {

                                                BackgroundWorker backgroundWorker = new BackgroundWorker(parent.getContext ());

                                                backgroundWorker.execute("supprimerAmitie", Session.membre.getListeAmisId ().get ( position ).toString () );
                                                backgroundWorker.setArrayAdapter(parent);
                                                backgroundWorker = new BackgroundWorker(Amis.this);

                                                backgroundWorker.execute("update");
                                                backgroundWorker.setListView ( Amis.lv );





                                            }
                                        })
                                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dlg, int sumthin) {
                                                dlg.dismiss();

                                            }
                                        })



                                        .show();
                            }
                        } )
                        .setOnDismissListener ( new AlertDialog.OnDismissListener(){

                            @Override
                            public void onDismiss ( DialogInterface dialog ) {
                                Amis.dialogAfficher = false;
                            }
                        })


                        .show();
                Amis.dialogAfficher=true;

            }
        });
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
