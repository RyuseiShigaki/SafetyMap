package com.shigaki.sano.safetymap;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


class SendJsonToPhp extends Application{

    String name,explanation;
    double latitude,longitude;

    SendJsonToPhp(String n,String ex,double lat,double longi){
        this.name = n;
        this.explanation = ex;
        this.latitude = lat;
        this.longitude = longi;
    }

    public void sendStart() {

        //queue
        RequestQueue postQueue = Volley.newRequestQueue(getApplicationContext());

        //サーバーのアドレス任意
        String POST_URL="edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/PBL/edit.php";

        StringRequest stringReq=new StringRequest(Request.Method.POST,POST_URL,

                //通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("volley","通信に成功しました。");
                    }
                },

                //通信失敗
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("volley","通信に失敗しました。");
                    }
                }){

            //送信するデータを設定
            @Override
            protected Map<String,String> getParams(){

                //putの引数に送信したいデータを入力
                Map<String,String> params = new HashMap<String,String>();
                params.put("Name",name);
                params.put("Explanation",explanation);
                params.put("latitude",String.valueOf(latitude));
                params.put("longiude",String.valueOf(longitude));
                return params;
            }
        };

        postQueue.add(stringReq);

    }

}