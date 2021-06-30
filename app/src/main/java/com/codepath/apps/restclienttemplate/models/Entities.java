package com.codepath.apps.restclienttemplate.models;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entities {
    public String mediaUrl;
    public static final String TAG = "Entities";

    public Entities() {}

    public static Entities fromJson(JSONObject jsonObject) throws JSONException{
        Entities entity = new Entities();
        Log.i(TAG, String.valueOf(jsonObject));
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("media");
            Log.i(TAG, String.valueOf(jsonArray));
        } catch (JSONException e) {
            jsonArray = null;
            Log.i(TAG, String.valueOf(jsonArray));
        }
        try {
            entity.mediaUrl = jsonArray.getJSONObject(0).getString("media_url_https");
            Log.i(TAG, String.valueOf(entity.mediaUrl));
        } catch (NullPointerException e) {
            entity.mediaUrl = null;
            Log.i(TAG, String.valueOf(entity.mediaUrl));
        }
        return entity;

    }
}
