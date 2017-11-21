package com.shigaki.sano.safetymap;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import android.view.KeyEvent;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    HashMap<String,String> marker_explanation = new HashMap<>();
    HashMap<String,Integer> marker_total_collect = new HashMap<>();
    HashMap<String,Integer> marker_total_wrong = new HashMap<>();

    LatLng default_pos = new LatLng(0,0);

    Marker m_copied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

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

        ReadJson json1 = new ReadJson();

        mMap = googleMap;
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYANm);

        m_copied = mMap.addMarker(new MarkerOptions().position(default_pos).icon(icon));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                m_copied.setPosition(point);
                Toast.makeText(getApplicationContext(), "タップ位置をコピーしました\n緯度：" + point.latitude + "\n経度:" + point.longitude, Toast.LENGTH_LONG).show();
                ClipData.Item item = new ClipData.Item("緯度：," + point.latitude + ",経度:," + point.longitude);
                //MIMETYPEの作成
                String[] mimeType = new String[1];
                mimeType[0] = ClipDescription.MIMETYPE_TEXT_PLAIN;
                //クリップボードに格納するClipDataオブジェクトの作成
                ClipData cd = new ClipData(new ClipDescription("text_data", mimeType), item);
                //クリップボードにデータを格納
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cm.setPrimaryClip(cd);

            }
        });


        // 各マーカーの座標
        LatLng koshi = new LatLng(32.886034, 130.789461);

        json1.setOnCallBack(new ReadJson.CallBackTask() {
            @Override
            public void CallBack(JSONArray result) {
                super.CallBack(result);
                // resultにはdoInBackgroundの返り値が入ります。
                // ここからAsyncTask処理後の処理を記述します。

                ArrayList<Marker> spot_data = new ArrayList<Marker>();

                Marker m;
                LatLng l = null;
                String t = "";
                String ex = "";
                int collect = 0;
                int wrong = 0;

                for(int i=0;i<result.length();i++){
                    try {
                        l = new LatLng(result.getJSONObject(i).getDouble("latitude"),result.getJSONObject(i).getDouble("longitude"));
                        t = result.getJSONObject(i).getString("name");
                        ex = result.getJSONObject(i).getString("explanation");
                        collect = result.getJSONObject(i).getInt("true_number");
                        wrong = result.getJSONObject(i).getInt("false_number");
                    }catch(JSONException e){
                        Log.i("addmarker","例外発生"+e);
                    }
                    m = mMap.addMarker(new MarkerOptions().position(l).title(t));
                    marker_explanation.put(m.getId(),ex);
                    marker_total_collect.put(m.getId(),collect);
                    marker_total_wrong.put(m.getId(),wrong);

                    spot_data.add(m);

                }

            }
        });

        json1.rereadVolley();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        //センターカメラの移動
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(koshi, 12));

        mMap.setOnInfoWindowClickListener(this);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンの処理
            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


}