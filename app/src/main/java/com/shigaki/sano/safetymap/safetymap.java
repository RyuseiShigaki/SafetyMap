package com.shigaki.sano.safetymap;

import android.app.Application;
import android.content.Context;

/**
 * Created by shigashan on 17/11/14.
 */

public class safetymap extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        safetymap.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return safetymap.context;
    }
}