package com.example.ianchick.githubchallenge.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ianchick on 10/23/17.
 */

public class HttpGetRequest extends AsyncTask<String, Void, String> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 30000;
    private static final int CONNECTION_TIMEOUT = 30000;

    @Override
    protected String doInBackground(String... strings) {
        String stringUrl = strings[0];
        String result;
        String inputLine;
        int responseCode;

        try {
            URL url = new URL(stringUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            // Authentication
            String token = "c2209faeeb9a8e41bebf8b8442b69d43aa20b4c1";
            connection.setRequestProperty("token", token);

            connection.connect();

            responseCode = connection.getResponseCode();
            Log.v("http", "Response Code: " + responseCode);

            if (responseCode == 200) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
            } else {
                result = String.valueOf(responseCode);
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

    public static String getRequest(String url) throws ExecutionException, InterruptedException {
        HttpGetRequest getRequest = new HttpGetRequest();
        String result = getRequest.execute(url).get();
        return result;
    }
}
