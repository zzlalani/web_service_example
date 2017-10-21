package com.zeeshanlalani.customlistexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.zeeshanlalani.customlistexample.Adaptors.CustomAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView)findViewById(R.id.list_view);
        new getData().execute();
    }

    private void getData() {

    }

    private class getData extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        List<Person> list = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.androidhive.info/contacts/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                con.setReadTimeout(10000);
                con.setConnectTimeout(10000);

                InputStream in = con.getInputStream();
                con.connect();

                BufferedReader br = null;
                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                } else {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }

                JSONObject resp = bufferToJson(br);

                // Getting JSON Array node
                JSONArray contacts = resp.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    Person p = new Person(id, name);
                    list.add(i, p);
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();

            list_view.setAdapter(new CustomAdaptor(MainActivity.this, list));

        }

        JSONObject bufferToJson (BufferedReader br) {
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject json = new JSONObject(sb.toString());
                return json;
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}
