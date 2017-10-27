package com.example.ianchick.githubchallenge.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ianchick.githubchallenge.activities.ShowDiffActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

    private Context context;

    public static String getRequest(String url, Context context) throws ExecutionException, InterruptedException {
        return new HttpGetRequest(context).execute(url).get();
    }

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
            String token = readJsonFile("secrets.json").getString("token");
            connection.setRequestProperty("token", token);

            connection.connect();
            responseCode = connection.getResponseCode();
            Log.v("http", "Response Code: " + responseCode);

            // Successful connection
            if (responseCode == 200) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    if (context instanceof ShowDiffActivity) {
                        stringBuilder.append(inputLine + "\n");
                    } else {
                        stringBuilder.append(inputLine);
                    }
                }

                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
            } else {
                // Return response code
                result = String.valueOf(responseCode);
            }
            connection.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

    /**
     * Private
     */

    /**
     * Read simple JSON Object file from Assets folder. Used for GitHub authentication token.
     *
     * @param fileName
     * @return
     * @throws JSONException
     */
    private JSONObject readJsonFile(String fileName) throws JSONException {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return JsonParser.getJsonObject(json);
    }

    /**
     * Private constructor to be used in getRequest call in order to start a new AsyncTask.
     * Needed for passing in context to read Assets for authentication token.
     *
     * @param context
     */
    private HttpGetRequest(Context context) {
        this.context = context;
    }

}
