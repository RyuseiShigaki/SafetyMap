package com.shigaki.sano.safetymap;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadJson{

    JSONArray data;
    private CallBackTask callbacktask;

    public void rereadVolley() {

            //サーバーのアドレス任意
            String GET_URL="http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/PBL/connection.php";

            //queue
            RequestQueue getQueue= Volley.newRequestQueue(safetymap.getAppContext());

            //Volleyによる通信開始　（GETかPOST、サーバーのURL、受信メゾット、エラーメゾット）
            JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET,GET_URL,

                    // 通信成功
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //リストを更新する
                            try {
                                JSONArray res_data= response.getJSONArray("JSON_DATA");
                                Log.i("readsuccess!!!",res_data.getJSONObject(0).getString("name"));
                                setData(res_data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    // 通信失敗
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("erroooor","通信に失敗しました。"+error);
                        }
                    }
            );

            getQueue.add(mRequest);

    }

    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }

    public void setData(JSONArray res_data){
        this.data = res_data;
        callbacktask.CallBack(this.data);
    }

    public static class CallBackTask {
        public void CallBack(JSONArray result) {
        }
    }


}