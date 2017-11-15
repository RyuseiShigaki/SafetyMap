package com.shigaki.sano.safetymap;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shigashan on 17/11/14.
 */

public class WriteJson{

    String name = "";
    String explanation = "";
    JSONObject json_data = null;
    String latitude;
    String longitude;

    private CallBackTask callbacktask;

    WriteJson(String n,String exp,String lat,String lng){
        this.name = n;
        this.explanation = exp;
        this.latitude = lat;
        this.longitude = lng;
    }

    public void rereadVolley() {

        //サーバーのアドレス任意
        String POST_URL="http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/PBL/connection_write.php";

        //queue
        RequestQueue getQueue= Volley.newRequestQueue(safetymap.getAppContext());



        //Volleyによる通信開始　（GETかPOST、サーバーのURL、受信メゾット、エラーメゾット）
        StringRequest mRequest = new StringRequest(Request.Method.POST,POST_URL,

                // 通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //今回は処理なし
                        Log.i("success","通信成功！返答:"+response);
                    }
                },

                // 通信失敗
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("erroooor","通信に失敗しました。"+error);
                    }
                }
        ){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("name",name);
                params.put("explanation",explanation);
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;
            }
        };

        getQueue.add(mRequest);

    }

    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }

    public static class CallBackTask {
        public void CallBack() {
        }
    }


}