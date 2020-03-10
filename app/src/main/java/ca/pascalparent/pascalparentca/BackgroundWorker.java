package ca.pascalparent.pascalparentca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private String identifiant;
    private String mdp;
    private String type;
    private ListView lw;

    BackgroundWorker (Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        String connexion_url = "https://pascalparent.ca/reseauSocial/android/connexion.php";
        String recherche_url = "https://pascalparent.ca/reseauSocial/android/rechercherAmisAjouter.php";
        if(type.equals("connexion")){
            try {
                identifiant = params[1];
                mdp = params[2];
                URL url = new URL(connexion_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("identifiant","UTF-8")+"="+URLEncoder.encode( identifiant ,"UTF-8")+"&"
                        +URLEncoder.encode("mdp","UTF-8")+"="+URLEncoder.encode(mdp,"UTF-8");
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("recherche")){
            try {
                String pseudo = params[1];

                URL url = new URL(recherche_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("recherche","UTF-8")+"="+URLEncoder.encode( pseudo ,"UTF-8");
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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

                    ArrayList<Integer> amis = new ArrayList<Integer> ( );
                    String[] amisStrTab= reader.getString ( "id" ).split ( ";" );
                    for ( int i = 0 ; i < amisStrTab.length ; i++ ) {
                        amis.add ( Integer.valueOf ( amisStrTab[i] ) );
                    }



                    Session.SessionInit (Integer.parseInt (reader.get ( "id" ).toString ()),
                            (String)reader.get ( "prenom" ),(String)reader.get ( "nom" ),
                            (String)reader.get ( "courriel" ),(String)reader.get ( "pseudo" ),
                            (String)reader.get ( "photo" ), (String)reader.get ( "couleur" ),amis);

                    Intent anothercallActivity=new Intent(context,Profil.class);
                    context.startActivity ( anothercallActivity);
                    SharedPreferences settings = context.getSharedPreferences("ConnexionPreferences", 0);
                    SharedPreferences.Editor editor = settings.edit();
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

                alertDialog.setMessage(result+"\n\nERREUR JSON : "+e.getMessage ());
                alertDialog.show();
            }
        }else if(type.equals ( "recherche" )){
            JSONObject json = null;
            String msg = "";
            ArrayList<Integer> identifiants = new ArrayList<Integer> (  );
            JSONObject reader;
            try {
                String[] res = result.split ( "\\n" );

                for ( int i = 0; i<res.length ; i++ ){
                    reader =new JSONObject ( res[i] );
                    int id = Integer.valueOf ( reader.getString ( "id" ));
                    if(Session.getId () != id && !Session.getAmis ().contains ( id )){

                    msg+=reader.getString ( "pseudo" )+" : ("+reader.getString ( "prenom" )+" "+reader.getString ( "nom" )+") \n";
                    identifiants.add ( id);
                    }
                }





            } catch ( JSONException e ) {
                e.printStackTrace ( );
            }
            updateLw(msg, identifiants);

        }











    }

    private void updateLw ( String msg , final ArrayList<Integer> identifiants) {
        String[] values = msg.split ( "\n" );
        final ArrayList<String> list = new ArrayList<String> (  );
        for (int i = 0; i<values.length; i++){
            list.add ( values[i] );
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(context,
                android.R.layout.simple_list_item_1, list);
        this.getListView ().setAdapter(adapter);
        adapter.notifyDataSetChanged ();
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick( AdapterView<?> arg0, View arg1, int position, long arg3) {
                String[] tempChaineCoupee= lw.getItemAtPosition(position).toString ().split(":" );
                System.out.println ( "Clicked : " + tempChaineCoupee[0].trim () + identifiants.get ( position )  );
            }
        });
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



}
/*
* FROM https://www.vogella.com/tutorials/AndroidListView/article.html
*
*/
class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
        super(context, textViewResourceId, objects);
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

}
