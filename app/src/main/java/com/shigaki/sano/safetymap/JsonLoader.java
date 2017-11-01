package com.shigaki.sano.safetymap;

import android.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by com.swift_studying. on 15/10/24.
 */
public class JsonLoader extends AsyncTaskLoader<JSONObject> {
    private String urlText;

    public JsonLoader(Context context, String urlText){
        super(context);
        this.urlText = urlText;
    }

    @Override
    public JSONObject loadInBackground(){
        HttpURLConnection connection = null;

        try{
            final String USERNAME = "te14shigaki";
            final String PASSWORD = "Asdfreply6";
            URL url = new URL(urlText);
            connection = (HttpURLConnection)url.openConnection();
            final String userPassword = USERNAME+":"+PASSWORD;
            final String encodeAuthorization = Base64.encodeToString(userPassword.getBytes(), Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", "Basic " + encodeAuthorization);
            connection.setRequestMethod("GET");
            connection.connect();
        }
        catch (MalformedURLException exception){
            // 処理なし
            Log.d("loadInBackground","MalformedURLException例外");
        }
        catch (IOException exception){
            // 処理なし
            Log.d("loadInBackground","IO例外!!!!!"+exception.getMessage());
        }

        try{
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1){
                if (length > 0){
                    outputStream.write(buffer, 0, length);
                }
            }

            JSONObject json = new JSONObject(new String(outputStream.toByteArray()));
            return json;
        }
        catch (IOException exception){
            // 処理なし
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}