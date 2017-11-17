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
 * Created by ubuntu on 17/11/17.
 */

public class DeleteSpot {

        String delete_name;
        private CallBackTask callbacktask;

        DeleteSpot(String n){
            this.delete_name = n;
        }

        public void rereadVolley() {

            //サーバーのアドレス任意
            String POST_URL="http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/PBL/connection_delete.php";

            //queue
            RequestQueue getQueue= Volley.newRequestQueue(safetymap.getAppContext());

            //Volleyによる通信開始　（GETかPOST、サーバーのURL、受信メゾット、エラーメゾット）
            StringRequest mRequest = new StringRequest(Request.Method.POST,POST_URL,

                    // 通信成功
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            callbacktask.CallBack(response);
                            Log.i("delete","通信結果:"+response);
                        }
                    },

                    // 通信失敗
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("delete_error","通信に失敗しました。"+error);
                        }
                    }
            ){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("title",delete_name);
                    Log.i("testtstt",delete_name);
                    return params;
                }
            };

            getQueue.add(mRequest);

        }

        public void setOnCallBack(CallBackTask _cbj) {
            callbacktask = _cbj;
        }

        public static class CallBackTask {
            public void CallBack(String result) {
            }
        }

}
