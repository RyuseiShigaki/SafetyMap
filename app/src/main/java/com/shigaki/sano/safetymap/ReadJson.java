package com.shigaki.sano.safetymap;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.loopj.android.http.*;

import static android.R.id.progress;
import static android.content.ContentValues.TAG;

class ReadJson {

    AsyncHttpClient client = new AsyncHttpClient();

    void readStart() {
        // 注意：onStart -> onSuccess -> onFinishの順番で呼ばれる
        client.get("http://www.yahoo.co.jp", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                progress.dismiss();
                Log.i(TAG, "onSuccess");
                Log.v(TAG, responseh);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish");
                progress.dismiss();
            }

            @Override
            public void onStart() {
                Log.i(TAG, "onStart");
                progress.show();
            }

            @Override
            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

        });
    }


}