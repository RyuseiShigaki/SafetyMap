package com.shigaki.sano.safetymap;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.IntegerRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker mKosen;
    private Marker mPos2;
    private Marker mPos3;

    public JSONObject spotData;

    HashMap<String,String> marker_explanation = new HashMap<>();
    HashMap<String,Integer> marker_total_collect = new HashMap<>();
    HashMap<String,Integer> marker_total_wrong = new HashMap<>();

    private double mylatitude;
    private double mylongitude;
    private Location myLocate;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //doJsonTask();

        while(spotData==null){}

        setContentView(R.layout.activity_maps);

        TextView marker_title = (TextView)findViewById(R.id.marker_title);
        TextView marker_explanation = (TextView)findViewById(R.id.marker_explanation);
        TextView total_collect = (TextView)findViewById(R.id.total_collect);
        TextView total_wrong = (TextView)findViewById(R.id.total_wrong);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            spotData.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        // 各マーカーの座標
        LatLng kosen = new LatLng(32.876904, 130.7479490);
        LatLng pos2 = new LatLng(32.88,130.75);
        LatLng pos3 = new LatLng(32.89,130.76);

        //現在地からの距離

        //マーカーの追加
        mKosen = mMap.addMarker(new MarkerOptions().position(kosen).title("熊本高専").snippet("現在地からの距離:5kmぐらい"));
        mKosen.setTag(0);
        marker_explanation.put(mKosen.getId(),"たのしいがっこうだょ！");
        marker_total_collect.put(mKosen.getId(),3);
        marker_total_wrong.put(mKosen.getId(),1);
        mPos2 = mMap.addMarker(new MarkerOptions().position(pos2).title("佐野のトイレ1号").snippet("現在地からの距離:5kmぐらい"));
        mPos2.setTag(0);
        marker_explanation.put(mPos2.getId(),"くさいよ！");
        marker_total_collect.put(mPos2.getId(),100);
        marker_total_wrong.put(mPos2.getId(),4);
        mPos3 = mMap.addMarker(new MarkerOptions().position(pos3).title("ともきの女子トイレ").snippet("現在地からの距離:5kmぐらい"));
        mPos3.setTag(0);
        marker_explanation.put(mPos3.getId(),"超くさいよ！");
        marker_total_collect.put(mPos3.getId(),5000);
        marker_total_wrong.put(mPos3.getId(),3);

        //動作不安定のためコメントアウト中
        /*//現在地取得の許可が降りているかを確認
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

            //現在地の緯度、経度を取得
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    new LocationListener(){
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.v("Main",String.format("Current Locatin is %s,%s",
                                    String.valueOf(location.getLatitude()),
                                    String.valueOf(location.getLongitude())));
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Log.v("Main","onProviderDisabled");
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Log.v("Main","onProviderEnabled");
                        }

                        @Override
                        public void onStatusChanged(String provider,
                                                    int status, Bundle extras) {
                            Log.v("Main","onStatusChanged");
                        }
                    }
            );
            this.myLocate = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            this.mylatitude = this.myLocate.getLatitude();
            this.mylongitude = this.myLocate.getLongitude();

            mMap.setMyLocationEnabled(true);
        } else {
            //不許可時の処理を記す
            // Show rationale and request permission.
        }*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        //センターカメラの移動
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kosen, 17));

        mMap.setOnInfoWindowClickListener(this);

    }

    float getDistance(LatLng target){
        float[] results = new float[1];
        Location.distanceBetween(this.mylatitude,this.mylongitude,target.latitude,target.longitude,results);
        return results[0];
    }



    @Override
    public void onInfoWindowClick(Marker marker){
        showFragmentDialog(TEST_DIALOG,marker);
    }

    /**フラグメントダイアログを表示する。*/
    final int TEST_DIALOG = 0;
    public void showFragmentDialog(int id,Marker marker)
    {
        switch(id){
            case TEST_DIALOG:
                DialogFragment dialogFragment = MapsDialogFragment.newInstance(marker.getTitle(),marker_explanation.get(marker.getId()),
                        marker_total_collect.get(marker.getId()),marker_total_wrong.get(marker.getId()));
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
        }
    }

    /**OKボタンが押されたことを感知する。*/
    public void onTestDialogOKClick()
    {
        Log.i("MainActivity : ", "OK clicked.");
    }

}