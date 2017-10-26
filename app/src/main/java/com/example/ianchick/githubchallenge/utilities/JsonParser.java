package com.example.ianchick.githubchallenge.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ianchick on 10/23/17.
 */

public class JsonParser {

    public static JSONObject getJsonObject(String json) throws JSONException {
        return new JSONObject(json);
    }

    public static JSONArray getJsonArray(String json) throws JSONException {
        return new JSONArray(json);
    }
}
