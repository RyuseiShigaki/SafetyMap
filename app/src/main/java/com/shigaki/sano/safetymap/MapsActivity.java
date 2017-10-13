package com.shigaki.sano.safetymap;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
        mKosen = mMap.addMarker(new MarkerOptions().position(kosen).title("熊本高専"));
        mKosen.setTag(0);
        mPos2 = mMap.addMarker(new MarkerOptions().position(pos2).title("佐野のトイレ1号"));
        mPos2.setTag(0);
        mPos3 = mMap.addMarker(new MarkerOptions().position(pos3).title("ともきの女子トイレ"));
        mPos3.setTag(0);

        //センターカメラの移動
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kosen, 17));

        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker){
        Toast.makeText(this, "ここは"+marker.getTitle()+"です",
                Toast.LENGTH_SHORT).show();
        showFragmentDialog(TEST_DIALOG);
    }

    /**フラグメントダイアログを表示する。*/
    final int TEST_DIALOG = 0;
    public void showFragmentDialog(int id)
    {
        switch(id){
            case TEST_DIALOG:
                DialogFragment dialogFragment = MapsDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
        }
    }

    /**OKボタンが押されたことを感知する。*/
    public void onTestDialogOKClick()
    {
        Log.i("MainActivity : ", "OK clicked.");
    }

}