package com.shigaki.sano.safetymap;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker mKosen;
    private Marker mPos2;
    private Marker mPos3;


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
        mMap = googleMap;

        // 各マーカーの座標
        LatLng kosen = new LatLng(32.876904, 130.7479490);
        LatLng pos2 = new LatLng(32.88,130.75);
        LatLng pos3 = new LatLng(32.89,130.76);

        //マーカーの追加
        mKosen = mMap.addMarker(new MarkerOptions().position(kosen).title("Marker in Kosen"));
        mKosen.setTag(0);
        mPos2 = mMap.addMarker(new MarkerOptions().position(pos2).title("Marker in pos2"));
        mPos2.setTag(0);
        mPos3 = mMap.addMarker(new MarkerOptions().position(pos3).title("Marker in pos3"));
        mPos3.setTag(0);

        //センターカメラの移動
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kosen, 17));

        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker){

        //PopupWindow popupWin;

                /*ポップアップに表示するレイアウトの設定*/
        //LinearLayout popLayout
          //      = (LinearLayout)getLayoutInflater().inflate(
           //     R.layout.popup_window, null);
        //TextView popupText
         //       = (TextView)popLayout.findViewById(R.id.popup_text);
        //popupText.setText("Popup Text");

                /*ポップアップの作成*/
        //popupWin = new PopupWindow(this);
        //ここらへん調子悪いです（泣）

    }



}
