package com.zeeshanlalani.customlistexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AddStudents extends AppCompatActivity {

    EditText txtFirstName, txtLastName;
    Button addStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        addStudent = (Button) findViewById(R.id.addStudent);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = txtFirstName.getText().toString();
                String lastName = txtLastName.getText().toString();
                new PostData(firstName,lastName).execute();

            }
        });
    }

    private class PostData extends AsyncTask<Void, Void, Void> {

        String firstName, lastName;

        public PostData( String _firstName, String _lastName ) {
            firstName = _firstName;
            lastName = _lastName;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;
            try {
                url = new URL("http://10.0.2.2:3000/api/student/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                con.setDoInput(true);
                con.setInstanceFollowRedirects(false);

                con.setReadTimeout(10000);
                con.setConnectTimeout(10000);
                String param = "firstName="+firstName+"&lastName="+lastName;

                OutputStreamWriter writer = new OutputStreamWriter(
                        con.getOutputStream());
                writer.write(param);
                writer.close();

                con.connect();

                BufferedReader br = null;
                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                } else {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
