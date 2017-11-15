package com.shigaki.sano.safetymap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddPlaceActivity extends AppCompatActivity {

    String namedata;
    String expdata;
    String latdata;
    String lngdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Button add_button = (Button)findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText add_name = (EditText) findViewById(R.id.add_name);
                EditText add_explanation = (EditText) findViewById(R.id.add_explanation);
                EditText add_latitude = (EditText)findViewById(R.id.add_latitude);
                EditText add_longitude = (EditText) findViewById(R.id.add_longitude);

                CharSequence data1 = add_name.getText();
                CharSequence data2 = add_explanation.getText();
                CharSequence data3 = add_latitude.getText();
                CharSequence data4 = add_longitude.getText();

                namedata = data1.toString();
                expdata = data2.toString();
                latdata = data3.toString();
                lngdata = data4.toString();

                WriteJson writer = new WriteJson(namedata,expdata,latdata,lngdata);

                writer.rereadVolley();

                Toast.makeText(safetymap.getAppContext(),"追加が完了しました。",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddPlaceActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

}