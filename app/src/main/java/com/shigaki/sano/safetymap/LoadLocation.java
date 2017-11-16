package com.shigaki.sano.safetymap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by shigashan on 17/11/15.
 */

public class LoadLocation extends AppCompatActivity implements LocationListener {
    double lat;
    double lon;

    private CallBackTask callbacktask;

    public void loadStart() {

       LocationManager mLocationManager =
                 (LocationManager) safetymap.getAppContext().getSystemService(this.LOCATION_SERVICE);

        // Criteriaオブジェクトを生成
        Criteria criteria = new Criteria();

        // Accuracyを指定(低精度)
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        // PowerRequirementを指定(低消費電力)
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // ロケーションプロバイダの取得
        String provider = mLocationManager.getBestProvider(criteria, true);

        // 取得したロケーションプロバイダを表示
        // TextView tv_provider = (TextView) findViewById(R.id.Provider);
        // tv_provider.setText("Provider: " + provider);

        // LocationListenerを登録
        if (provider != null) {
            mLocationManager.requestLocationUpdates(provider,0, 0, this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        // 緯度の表示

        Log.i("locatioon","Latitude:"+location.getLatitude());

        // 経度の表示

        Log.i("locatiooon","Latitude:"+location.getLongitude());

        lat = location.getLatitude();
        lon = location.getLongitude();
        callbacktask.CallBack(lat,lon);


    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }

    public static class CallBackTask {
        public void CallBack(double lat,double lng) {
        }
    }



}
