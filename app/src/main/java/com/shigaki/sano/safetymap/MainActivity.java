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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shigaki.sano.safetymap.db.PlaceDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ReadJsonFromSql data = new ReadJsonFromSql("localhost","http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/PBL/read.php",
                "TEST_DATA2","te14shigaki");

        data.ReadStart();

        TextView t1 = (TextView)findViewById(R.id.test1);

        try {
            t1.setText(data.json_array.getJSONObject(0).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button button_open_map = findViewById(R.id.button_open_map);
        button_open_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        Button add_user_spot = (Button) findViewById(R.id.add_user_spot);
        add_user_spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPlaceActivity.class);
                startActivity(intent);
            }
        });




    }





}
