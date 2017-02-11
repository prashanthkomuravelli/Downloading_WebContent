package com.hp.guesscricketer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection connection;
            try {
                url=new URL(strings[0]);
                connection= (HttpURLConnection) url.openConnection();
                InputStream in=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=reader.read();

                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task=new DownloadTask();
        String result;
        try {
            result=task.execute("https://www.cricruns.com/teams/squad/India/player").get();
            Log.i("content",result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
