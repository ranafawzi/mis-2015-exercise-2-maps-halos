package com.example.mmbuw.hellomaps;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

public class MapsActivity extends Activity implements GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    EditText message;

    Set locationMessage = new HashSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        message = (EditText) findViewById(R.id.message);

        Context context = getApplicationContext();
        CharSequence text = "Application Started";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,text,duration);
        toast.show();

        setUpMapIfNeeded();

        mMap.setMyLocationEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){

            @Override
            public void onMapLongClick(LatLng latLng){
                mMap.addMarker(new MarkerOptions().position(latLng).title(message.getText().toString()));

                locationMessage.add(message.getText().toString());

                SharedPreferences prefs = getSharedPreferences("Weimar",Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();

                editor.putStringSet("message",locationMessage);

                Boolean flag = editor.commit();

                if(flag == true){

                    Context context = getApplicationContext();
                    CharSequence text = "Saved at Shared Preference";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                }

                CircleOptions circle = new CircleOptions();
                circle.center(latLng).fillColor(0x40ff0000).radius(10000).strokeColor(Color.GREEN).strokeWidth(3);
                mMap.addCircle(circle);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
