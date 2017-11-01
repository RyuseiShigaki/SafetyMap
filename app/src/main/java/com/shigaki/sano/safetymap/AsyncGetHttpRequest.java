package com.shigaki.sano.safetymap;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncGetHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

    private Activity mainActivity;

    public AsyncGetHttpRequest(Activity activity) {

        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    // このメソッドは必ずオーバーライドする必要があるよ
    // ここが非同期で処理される部分みたいたぶん。
    @Override
    protected String doInBackground(Uri.Builder... builder) {
        // httpリクエスト投げる処理を書く。
        // ちなみに私はHttpClientを使って書きましたー
        String data="";
        final String USERNAME = "te14shigaki";
        final String PASSWORD = "Asdfreply6";
        HttpURLConnection http = null;
        InputStream in = null;
        try {
            // URLにHTTP接続
            URL url = new URL("http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/testsite.html");
            http = (HttpURLConnection) url.openConnection();
            final String userPassword = USERNAME+":"+PASSWORD;
            final String encodeAuthorization = Base64.encodeToString(userPassword.getBytes(), Base64.NO_WRAP);
            http.setRequestProperty("Authorization", "Basic " + encodeAuthorization);
            http.setRequestMethod("GET");
            http.connect();
            // データを取得
            in = http.getInputStream();

            // HTMLソースを読み出す
            String src = new String();
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read(line);
                if (size <= 0)
                    break;
                src += new String(line);
            }
            data = src;
        } catch (Exception e) {
            data = e.toString();
        } finally {
            try {
                if (http != null)
                    http.disconnect();
                if (in != null)
                    in.close();
            } catch (Exception e) {
            }
        }
        return data;
    }


    // このメソッドは非同期処理の終わった後に呼び出されます
    @Override
    protected void onPostExecute(String result) {
        // 取得した結果をテキストビューに入れちゃったり
        TextView web = (TextView)mainActivity.findViewById(R.id.test1);
        web.setText(result);
    }
}