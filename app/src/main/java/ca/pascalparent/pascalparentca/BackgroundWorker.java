package ca.pascalparent.pascalparentca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.pascalparent.pascalparentca.ObjectClass.ConnexionInfo;
import ca.pascalparent.pascalparentca.ObjectClass.RechercheAmis;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private String identifiant;
    private String mdp;
    private String type;
    private ListView lw;
    private AdapterView<?> arrayAdapter;

    BackgroundWorker (Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        String connexion_url = "https://pascalparent.ca/reseauSocial/android/connexion.php";
        String recherche_url = "https://pascalparent.ca/reseauSocial/android/rechercherAmisAjouter.php";
        String update_url = "https://pascalparent.ca/reseauSocial/android/update.php";
        String supprimerAmitie_url = "https://pascalparent.ca/reseauSocial/android/supprimerAmitie.php";
        if(type.equals("connexion")){
            try {
                identifiant = params[1];
                mdp = params[2];

                String result = queryToServer ( connexion_url , URLEncoder.encode("identifiant","UTF-8")+"="+URLEncoder.encode( identifiant ,"UTF-8")+"&"
                        +URLEncoder.encode("mdp","UTF-8")+"="+URLEncoder.encode(mdp,"UTF-8"));

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("recherche")){
            try {
                String pseudo = params[1];

                String result = queryToServer ( recherche_url , URLEncoder.encode ( "recherche" , "UTF-8" ) + "=" + URLEncoder.encode ( pseudo , "UTF-8" ) +"&"
                        +URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(String.valueOf (  Session.membre.getId ()),"UTF-8"));

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals ( "supprimerAmitie" )){

            try {
                String idAmi = params[1];
                String result = queryToServer ( supprimerAmitie_url , URLEncoder.encode ( "idUtilisateur" , "UTF-8" ) + "=" + URLEncoder.encode ( String.valueOf ( Session.membre.getId () ) , "UTF-8") +"&"
                        +URLEncoder.encode("idAmi","UTF-8")+"="+URLEncoder.encode(idAmi,"UTF-8")) ;

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals ( "update" )){
            try {



                String result = queryToServer ( update_url , URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode( String.valueOf (  Session.membre.getId ()) ,"UTF-8")+"&"
                        +URLEncoder.encode("mdp","UTF-8")+"="+URLEncoder.encode( (  Session.membre.getMdp ()),"UTF-8"));

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals ( "nouvelleAmitie" )){
            String url = "https://pascalparent.ca/reseauSocial/android/nouvelleAmitie.php";
            try {
                queryToServer (url , URLEncoder.encode("idDemandeur","UTF-8")+"="+URLEncoder.encode( String.valueOf (  Session.membre.getId ()) ,"UTF-8")+"&"
                        +URLEncoder.encode("idRecepteur","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8"));
            } catch ( IOException e ) {
                e.printStackTrace ( );
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Informations");
    }

    @Override
    protected void onPostExecute(String result) {
        if(type.equals ( "connexion" )){
            try {
                JSONObject reader = new JSONObject ( result );
                if(((String)reader.get ( "courriel" )).length ()>0){

                    Gson gson =new Gson ();
                    ConnexionInfo info = gson.fromJson ( result, ConnexionInfo.class );
                    info.afficher ();
                    new Session (info);

                    Intent anothercallActivity=new Intent(context,Profil.class);
                    context.startActivity ( anothercallActivity);

                    //Configuration
                    SharedPreferences settings = context.getSharedPreferences("ConnexionPreferences", 0);
                    SharedPreferences.Editor editor = settings.edit();

                    //Sauvegarde dans le mobile
                    if(MainActivity.checkBox.isChecked ()){

                        editor.putString ( "identifiant", identifiant);
                        editor.putString ( "mdp", mdp);
                        editor.commit ();
                        editor.apply();
                    }else{

                        editor.putString ( "identifiant", "");
                        editor.putString ( "mdp", "");
                        editor.commit ();
                        editor.apply();
                    }

                }

            } catch ( JSONException e ) {

                alertDialog.setMessage(result);
                e.printStackTrace ();
                alertDialog.show();
            }
        }else if(type.equals ( "recherche" )){
            Gson gson =new Gson ();
            ConnexionInfo[] amis =gson.fromJson ( result, ConnexionInfo[].class );

            updateLw(amis);

        }else if(type.equals ( "update" )){
            try {
                JSONObject reader = new JSONObject ( result );
                if(((String)reader.get ( "courriel" )).length ()>0){

                    Gson gson =new Gson ();
                    ConnexionInfo info = gson.fromJson ( result, ConnexionInfo.class );
                    info.afficher ();
                    new Session (info);
                    if(this.getListView ()!=null){
                        ListAdapter adapter = this.getListView ( ).getAdapter ( );
                        ((BaseAdapter) adapter).notifyDataSetChanged ();
                        List<String> your_array_list = Session.membre.getAmisPrenomNomPseudo ();
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                context,
                                android.R.layout.simple_list_item_1,
                                your_array_list );

                        Amis.lv.setAdapter(arrayAdapter);

                    }






                }

            } catch ( JSONException e ) {

                alertDialog.setMessage(result);
                e.printStackTrace ();
                alertDialog.show();
            }
        }else if(type.equals ( "supprimerAmitie" )){
            System.out.println ( "BackgroundWorker.java(l.206) : "+ result );
        }











    }

    private void updateLw ( ConnexionInfo[] amis ) {
        final ArrayList<String> list = new ArrayList<String> (  );
        for ( int i = 0 ; i < amis.length ; i++ ) {
            if(Session.membre.getId() != amis[i].getId ()){
                list.add (  amis[i].getPseudo() + " ("+amis[i].getPrenom()+" "+amis[i].getNom()+")");
            }

        }
        final StableArrayAdapter adapter = new StableArrayAdapter(amis, context,
                android.R.layout.simple_list_item_1, list);
        this.getListView ().setAdapter(adapter);
        adapter.notifyDataSetChanged ();




        lw.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            private ConnexionInfo[] amis;

            @Override
            public void onItemClick( AdapterView<?> arg0, View arg1, int position, long arg3) {

                if(!Session.membre.getListeAmisId ().contains ( amis[position].getId () ) ){
                    System.out.println ( "Clicked : " + amis[position].getId () );


                        BackgroundWorker backgroundWorker = new BackgroundWorker(context);

                        backgroundWorker.execute("nouvelleAmitie", String.valueOf (  amis[position].getId ()));
                   backgroundWorker = new BackgroundWorker(context);

                    backgroundWorker.execute("update");
                    arg1.setBackgroundColor (Color.BLACK);


                }

            }
            private AdapterView.OnItemClickListener init(ConnexionInfo[] amis){
                this.amis = amis;
                return this;

            }
        }.init(amis));







    }




    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void setListView ( ListView lw ) {
        this.lw = lw;
    }
    public ListView getListView(){
        return this.lw;
    }
    
    public String queryToServer(String urlConn, String post_data) throws IOException {
        URL url = new URL(urlConn);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

        String result="";
        String line="";
        while((line = bufferedReader.readLine())!= null) {
            result += line+"\r\n";
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;
    }


    public void setArrayAdapter ( AdapterView<?> aa) {
        this.arrayAdapter = aa;
    }
}
/*
* FROM https://www.vogella.com/tutorials/AndroidListView/article.html
*
*/
class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    ConnexionInfo[] amis;

    public StableArrayAdapter(ConnexionInfo[] amis,Context context, int textViewResourceId,
                              List<String> objects) {

        super(context, textViewResourceId, objects);
        this.amis = amis;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        if(Session.membre.getListeAmisId ().contains ( amis[position].getId () ) )
        {
            // do something change color
            row.setBackgroundColor (Color.BLACK); // some color
        }
        else
        {
            // default state

        }
        return row;
    }

}


