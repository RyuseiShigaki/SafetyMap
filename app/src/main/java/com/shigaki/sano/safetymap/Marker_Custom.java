package com.shigaki.sano.safetymap;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by ubuntu on 17/10/16.
 */

public class Marker_Custom{


    Marker marker; //GoogleMapのマーカー情報
    private String explanation; //マーカーの説明
    private int total_collect; //「正しい」の合計
    private int total_wrong; //「間違い」の合計

    Marker_Custom(Marker m,String exp,int collect,int wrong){
        marker = m;
        explanation = exp;
        total_collect = collect;
        total_wrong = wrong;
    }
    String getExplanation(){
        return explanation;
    }
    int getTotal_collect(){
        return total_collect;
    }
    int getTotal_wrong(){
        return total_wrong;
    }

}
