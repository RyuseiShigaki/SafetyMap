package com.shigaki.sano.safetymap;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shigaki.sano.safetymap.db.PlaceDBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<JSONObject> {

    String data;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /*
        //HTTP,GET通信の処理(テスト用)
        Uri.Builder builder = new Uri.Builder();
        AsyncPostHttpRequest task = new AsyncPostHttpRequest(this);
        task.execute(builder);
        //通信処理ここまで
        */

        // TextViewを取得
        textView = (TextView)findViewById(R.id.test1);



        Button button_open_map = findViewById(R.id.button_open_map);
        button_open_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        Button add_user_spot = (Button)findViewById(R.id.add_user_spot);
        add_user_spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPlaceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        String urlText = "http://animemap.net/api/table/tokyo.json";
        JsonLoader jsonLoader = new JsonLoader(this, urlText);
        jsonLoader.forceLoad();
        return  jsonLoader;
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        if (data != null) {

            try {
                JSONObject jsonObject = data.getJSONObject("request");
                textView.setText(jsonObject.getString("url"));
            } catch (JSONException e) {
                Log.d("onLoadFinished","JSONのパースに失敗しました。 JSONException=" + e);
            }
        }else{
            Log.d("onLoadFinished", "onLoadFinished error!");
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        // 処理なし
    }


}
