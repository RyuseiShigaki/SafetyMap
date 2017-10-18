package com.shigaki.sano.safetymap;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shigaki.sano.safetymap.db.PlaceDBHelper;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button_open_map = (Button)findViewById(R.id.button_open_map);
        button_open_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doAddEntry( SQLiteDatabase db, int id, String name, String explanation, double latitude, double longitude ){
        // 挿入するデータはContentValuesに格納
        long i;
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("explanation",explanation);
        values.put("latitude",latitude);
        values.put("longitude",longitude);
        i = db.insert("place_table",null,values);
        db.close();
        Toast.makeText(this,""+i+"",Toast.LENGTH_SHORT).show();
    }


}
