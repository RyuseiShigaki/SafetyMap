package com.shigaki.sano.safetymap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class DeleteSpotActivity extends AppCompatActivity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    ReadJson spot_data = new ReadJson();

    DeleteSpot del;

    String PASSWORD = "te14shigashan"; //削除画面起動用パスワード

    String delete_name[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_spot);

        new AlertDialog.Builder(DeleteSpotActivity.this)
                .setCancelable(false)
                .setTitle("注意！！")
                .setMessage("管理人用メニューです！！あなたが管理人でないなら戻るを押してください！！")
                .setPositiveButton("私は管理人です", null)
                .setNegativeButton("戻る", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 戻る button pressed
                        Intent intent = new Intent(DeleteSpotActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).show();


        spot_data.setOnCallBack(new ReadJson.CallBackTask() {
            @Override
            public void CallBack(JSONArray result) {
                super.CallBack(result);

                Button spot_button[] = new Button[result.length()];
                delete_name = new String[result.length()];

                LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(WC, WC);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearid);

                layout_params.setMargins(0, 0, 0, 10);

                for (int i = 0; i < result.length(); i++) {

                    spot_button[i] = new Button(safetymap.getAppContext());
                    spot_button[i].setLayoutParams(layout_params);
                    spot_button[i].setBackgroundColor(Color.rgb(135, 206, 250));
                    spot_button[i].setId(i);
                    try {
                        spot_button[i].setText(result.getJSONObject(i).getString("name"));
                        delete_name[i] = result.getJSONObject(i).getString("name");
                    } catch (JSONException e) {
                        Log.i("delete_spot", "例外発生:" + e);
                    }
                    linearLayout.addView(spot_button[i], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WC));
                }

                    for (int i = 0; i < result.length(); i++) {


                        spot_button[i].setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {

                                del = new DeleteSpot(delete_name[view.getId()]);

                                new AlertDialog.Builder(DeleteSpotActivity.this)
                                        .setCancelable(false)
                                        .setTitle("注意！！")
                                        .setMessage("本当に削除しますか？")
                                        .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // はい button pressed
                                                del.rereadVolley();
                                                Intent intent = new Intent(DeleteSpotActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("戻る", null).show();

                                del.setOnCallBack(new DeleteSpot.CallBackTask(){
                                    @Override
                                    public void CallBack(String response){
                                        Toast.makeText(safetymap.getAppContext(),"削除が完了しました。",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DeleteSpotActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                        });
                    }



                //callback処理ここまで
            }


        });

        final EditText passwordinput = (EditText)findViewById(R.id.passWordInput);

        Button button_submit = (Button)findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PASSWORD.equals(passwordinput.getText().toString())){
                    Toast.makeText(safetymap.getAppContext(),"削除画面起動",Toast.LENGTH_SHORT).show();
                    spot_data.rereadVolley();
                }else{
                    Toast.makeText(safetymap.getAppContext(),"パスワードが違います",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
