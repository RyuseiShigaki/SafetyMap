package com.shigaki.sano.safetymap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.Comparator;

import static android.R.attr.width;
import static com.shigaki.sano.safetymap.R.attr.height;

public class SortDistanceActivity extends AppCompatActivity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    Distance distance[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_distance);

        ReadJson spot_data = new ReadJson();

        spot_data.setOnCallBack(new ReadJson.CallBackTask() {
            @Override
            public void CallBack(JSONArray result) {
                super.CallBack(result);
                // resultにはdoInBackgroundの返り値が入ります。
                // ここからAsyncTask処理後の処理を記述します。

                distance = new Distance[result.length()];
                Button spot_button[] = new Button[result.length()];

                LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams( WC, WC );
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearid);

                layout_params.setMargins( 0, 0, 0, 10);

                double latitude = 0;
                double longitude = 0;
                float[] results = new float[3];


                for(int i=0;i<result.length();i++){
                    try {

                        latitude = result.getJSONObject(i).getDouble("latitude");
                        longitude = result.getJSONObject(i).getDouble("longitude");

                        Location.distanceBetween(latitude,longitude,32.876904,130.7479490,results);

                        distance[i] = new Distance(result.getJSONObject(i).getString("name"),results[0]);

                        Log.i("distance_success",distance[i].name+distance[i].distance);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Arrays.sort(distance, new Comparator<Distance>() {
                    @Override
                    public int compare(Distance o1, Distance o2) {
                        return (int)o1.distance - (int)o2.distance;
                    }
                });

                for(int i=0;i<result.length();i++){
                    Log.i("sorted",distance[i].name+distance[i].distance);
                }

                for(int i=0;i<result.length();i++) {

                    spot_button[i] = new Button(safetymap.getAppContext());
                    spot_button[i].setLayoutParams(layout_params);
                    spot_button[i].setBackgroundColor(Color.rgb(157, 204, 224));
                    spot_button[i].setId(i);
                    spot_button[i].setText(distance[i].name+" 距離:"+(int)distance[i].distance+"m");

                    linearLayout.addView(spot_button[i], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WC));
                }

                for(int i=0;i<result.length();i++){
                    spot_button[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String dest_name = distance[view.getId()].name;
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                            intent.setData(Uri.parse("http://maps.google.com/maps?saddr="+"熊本高等専門学校"+"&daddr="+dest_name+"&dirflg="+"w"));
                            startActivity(intent);
                        }
                    });
                }



            //callback処理ここまで
            }
        });

        spot_data.rereadVolley();



    }

    class Distance {
        String name;
        double distance = 0;

        Distance(String n, double d) {
            this.name = n;
            this.distance = d;
        }
    }
}

// distance[0].distance = 12.32