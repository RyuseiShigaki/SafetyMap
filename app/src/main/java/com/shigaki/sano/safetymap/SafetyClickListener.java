package com.shigaki.sano.safetymap;

import android.view.View;

/**
 * Created by shigashan on 17/11/19.
 */

public class SafetyClickListener implements View.OnClickListener {

    double latitude[];
    double longitude[];
    int number;

    public SafetyClickListener(double[] lat,double[] lng,int num){
        this.latitude = lat;
        this.longitude = lng;
        this.number = num;
    }

    @Override
    public void onClick(View view){
    }

}
