package com.shigaki.sano.safetymap;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncPostHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

    private Activity mainActivity;

    public AsyncPostHttpRequest(Activity activity) {

        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    // このメソッドは必ずオーバーライドする必要があるよ
    // ここが非同期で処理される部分みたいたぶん。
    @Override
    protected String doInBackground(Uri.Builder... builder) {
        String postStr="";
        final String USERNAME = "te14shigaki";
        final String PASSWORD = "Asdfreply6";
        // httpリクエスト投げる処理を書く。
        // ちなみに私はHttpClientを使って書きましたー
        String urlString = "http://edu3.te.kumamoto-nct.ac.jp:8088/~te14shigaki/testsite.html";

        try {

            URL url = new URL(urlString);
            URLConnection uc = url.openConnection();

            final String userPassword = USERNAME+":"+PASSWORD;
            final String encodeAuthorization = Base64.encodeToString(userPassword.getBytes(), Base64.NO_WRAP);
            uc.setRequestProperty("Authorization", "Basic " + encodeAuthorization);

            uc.setDoOutput(true);//POST可能にする

            OutputStream os = uc.getOutputStream();//POST用のOutputStreamを取得

            postStr = "this is output!!";//POSTするデータ
            PrintStream ps = new PrintStream(os);
            ps.print(postStr);//データをPOSTする
            ps.close();

            InputStream is = uc.getInputStream();//POSTした結果を取得
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
            reader.close();
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL format: " + urlString);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Can't connect to " + urlString);
            System.exit(-1);
        }
        return postStr;
    }


    // このメソッドは非同期処理の終わった後に呼び出されます
    @Override
    protected void onPostExecute(String result) {
        // 取得した結果をテキストビューに入れちゃったり
        TextView web = (TextView)mainActivity.findViewById(R.id.test1);
        web.setText(result);
    }
}