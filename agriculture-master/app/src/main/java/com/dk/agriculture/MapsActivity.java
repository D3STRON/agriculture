package com.dk.agriculture;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<MarketAddModel> marketAddModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Bundle bundle= getIntent().getBundleExtra("bundle");
        marketAddModels= (ArrayList<MarketAddModel>) bundle.getSerializable("sellerlist");
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(19.0, 73.0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        ArrayList<LatLng> sellers= new ArrayList<>();
        for(int i=0;i<marketAddModels.size();i++){
            double lat = Double.parseDouble(marketAddModels.get(i).getLatitude());
            double lon = Double.parseDouble(marketAddModels.get(i).getLongitude());
            LatLng newpos = new LatLng(lat, lon);
            String name = marketAddModels.get(i).getRentername();
            mMap.addMarker(new MarkerOptions().position(newpos).title(name));
        }
        //sellers.add(new LatLng())

    }
}
