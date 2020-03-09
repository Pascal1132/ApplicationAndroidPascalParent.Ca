package ca.pascalparent.pascalparentca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String connexion_url = "https://pascalparent.ca/reseauSocial/android/connexion.php";
        if(type.equals("connexion")){
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(connexion_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("identifiant","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("mdp","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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

        try {
            JSONObject reader = new JSONObject ( result );
            if(((String)reader.get ( "courriel" )).length ()>0){
                Session.setId ( (Integer.parseInt (reader.get ( "id" ).toString ())) );
                Session.setNom ( ((String)reader.get ( "nom" )) );
                Session.setPrenom ( ((String)reader.get ( "prenom" )) );
                Session.setCourriel ( ((String)reader.get ( "courriel" )) );
                Session.setCouleur ( ((String)reader.get ( "couleur" )) );
                Session.setPhoto ( ((String)reader.get ( "photo" )) );
                Intent anothercallActivity=new Intent(context,Profil.class);
                context.startActivity ( anothercallActivity);
            }

        } catch ( JSONException e ) {
            alertDialog.setMessage(result);
            alertDialog.show();
        }










    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
