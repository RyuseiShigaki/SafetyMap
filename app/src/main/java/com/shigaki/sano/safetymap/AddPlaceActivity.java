package com.shigaki.sano.safetymap;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
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

    String namedata = "";
    String expdata = "";
    String latdata;
    String lngdata;

    CharSequence data1;
    CharSequence data2;
    CharSequence data3;
    CharSequence data4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("合志安全マップ");
        setContentView(R.layout.activity_add_place);

        Button add_button = (Button)findViewById(R.id.add_button);

        Button copy_button = (Button)findViewById(R.id.button_copy);

        copy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = cm.getPrimaryClip();
                if (clipData != null) {

                    ClipData.Item item = clipData.getItemAt(0);
                    String copy_data = item.getText().toString();
                    String copy_array[] = copy_data.split(",",0);
                    if(copy_array.length == 4) {
                        EditText add_latitude = (EditText)findViewById(R.id.add_latitude);
                        EditText add_longitude = (EditText) findViewById(R.id.add_longitude);
                        add_latitude.setText(copy_array[1]);
                        add_longitude.setText(copy_array[3]);
                        Toast.makeText(safetymap.getAppContext(),"コピーされた緯度経度を入力しました",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(safetymap.getAppContext(),"「マップを開く画面」から緯度経度をコピーしてください",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(safetymap.getAppContext(),"「マップを開く画面」から緯度経度をコピーしてください",Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText add_name = (EditText) findViewById(R.id.add_name);
                EditText add_explanation = (EditText) findViewById(R.id.add_explanation);
                EditText add_latitude = (EditText)findViewById(R.id.add_latitude);
                EditText add_longitude = (EditText) findViewById(R.id.add_longitude);

                data1 = add_name.getText();
                data2 = add_explanation.getText();
                data3 = add_latitude.getText();
                data4 = add_longitude.getText();

                namedata = data1.toString();
                expdata = data2.toString();
                latdata = data3.toString();
                lngdata = data4.toString();
                if(latdata.length()==0){
                    Toast.makeText(safetymap.getAppContext(),"緯度を入力してください。",Toast.LENGTH_SHORT).show();
                }else if(lngdata.length()==0){
                    Toast.makeText(safetymap.getAppContext(),"経度を入力してください。",Toast.LENGTH_SHORT).show();
                }else if(!((Double.parseDouble(latdata)>=-90)&&(Double.parseDouble(latdata)<=90))||!((Double.parseDouble(lngdata)>=-180)&&(Double.parseDouble(lngdata)<=180))) {
                    Toast.makeText(safetymap.getAppContext(),"緯度・経度が不正です。",Toast.LENGTH_SHORT).show();
                }else if(namedata.length()>50){
                    Toast.makeText(safetymap.getAppContext(),"名前は50文字までです。",Toast.LENGTH_SHORT).show();
                }else if(namedata.length()==0){
                    Toast.makeText(safetymap.getAppContext(),"名前を入力してください。",Toast.LENGTH_SHORT).show();
                }else if(expdata.length()==0){
                    Toast.makeText(safetymap.getAppContext(),"説明を入力してください。",Toast.LENGTH_SHORT).show();
                }else if(expdata.length()>100){
                    Toast.makeText(safetymap.getAppContext(),"説明は100文字までです。",Toast.LENGTH_SHORT).show();
                }else{

                    new AlertDialog.Builder(AddPlaceActivity.this)
                            .setTitle("確認")
                            .setMessage("名前: "+data1+"\n"+"説明: "+data2+"\n"+"緯度: "+data3+"\n"+"経度: "+data4+"\n\n"+"これでよろしいですか？")
                            .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // OK button pressed
                                    WriteJson writer = new WriteJson(namedata, expdata, latdata, lngdata);

                                    writer.rereadVolley();

                                    Toast.makeText(safetymap.getAppContext(), "追加が完了しました。", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(AddPlaceActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("キャンセル", null)
                            .show();
                }
            }
        });
    }

}