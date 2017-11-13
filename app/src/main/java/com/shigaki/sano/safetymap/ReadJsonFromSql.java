package com.shigaki.sano.safetymap;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by ubuntu on 17/11/08.
 */

public class ReadJsonFromSql {

    String getSERVER;
    String getURL;
    String getDB;
    String getID;

    public InputStream is = null;
    public JSONArray json_array = null;
    public DefaultHttpClient httpClient;



    ReadJsonFromSql(String getSERVER,String getURL,String getDB,String getID){
        this.getSERVER = getSERVER;
        this.getURL = getURL;
        this.getDB = getDB;
        this.getID = getID;
    }

    public void ReadStart(){

        // スキーム登録：スキーム(httpやhttpsなんか)を登録します。ポート3306番で接続
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(),8808));

        // HTTPパラメータ設定：プロトコルやエンコードを指定します。
        HttpParams httpParams;
        httpParams= new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "UTF_8");

        // HTTPクライアント生成：httpを利用するためのクライアント生成（ブラウザみたいな感じです）
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schReg),httpParams);

        // HTTP Request送信
        HttpResponse response = null;
        try{
            // URIを設定：先ほど作成したphpファイルにアクセスするための情報を設定。
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.path(getURL); //先ほど作成したphpのURLを設定
            uriBuilder.appendQueryParameter("SERVER",getSERVER); // サーバー名を設定
            uriBuilder.appendQueryParameter("ID",getID); // UserIDを設定
            uriBuilder.appendQueryParameter("db_name",getDB); // Database名を設定
            //↓このresponse変数がphpで出力したJSONデータを受け取る。
            response = httpClient.execute(new HttpHost(getSERVER),new HttpGet(uriBuilder.build().toString()));
        }catch(Exception e){
            Log.e("Errrer","接続エラー");
            return;
        }

        // レスポンスコードを確認：きちんとデータが返ってきたか一応確認
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
            Log.e("Error", String.valueOf(response.getStatusLine().getStatusCode()));
            return;
        }

        // レスポンスを取得してStringに変換
        StringBuilder json = new StringBuilder();
        try{
            HttpEntity entity = response.getEntity();
            InputStream input = entity.getContent();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while((line = bufReader.readLine()) != null){
                json.append(line);
            }
        }catch(IOException e){
            Log.e("Errrer","バッファ読み込み失敗");
            return;
        }

        // JSON解析：json_arrayにデータを格納。これでMySQLデータ読み込み完了です。
        try{
            JSONObject json_data = new JSONObject(json.toString());
            json_array = json_data.getJSONArray("response");
        }catch(JSONException e){
            Log.e("Errrer","JSONデータが不正");
            return;
        }
        return;

    }


}
