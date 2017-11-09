package com.shigaki.sano.safetymap;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by ubuntu on 17/11/08.
 */

public class ReadJson extends AsyncTask<Void, Void, String> {

    private CallBackTask callbacktask;

    private Activity m_Activity;

    private static final String url = "http://nvtrlab.jp/test.php";

    //クライアント設定
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(url);

    List<NameValuePair> nameValuePair;
    HttpResponse response;
    HttpResponse res = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }

    @Override
    protected String doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // doInBackground後処理
        callbacktask.CallBack(result);
    }

    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }

    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(String result) {
        }
    }

}