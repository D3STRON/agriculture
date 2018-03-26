package com.dk.agriculture;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.Locale;

import static com.dk.agriculture.R.id.english;
import static com.dk.agriculture.R.id.hindi;
import static com.dk.agriculture.R.menu.menu_main;

public class DashboardActivity extends AppCompatActivity {
    TextView mPlaceOrder,mMyAds,mRent,mSoil,mAnimal,mCropDesc,mPlaceOrderDesc,mAdsDesc,mRentDesc,mSoilDesc,mAnimalDesc,mCrop;
    private Locale myLocale;
    private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    LocationManager locationManager;
    LocationListener locationListener;
    private CardView placeAd, myAd,rent,soil,animal,crop;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        placeAd = findViewById(R.id.placeAd);
        myAd = findViewById(R.id.myAd);
        rent = findViewById(R.id.rent);
        soil = findViewById(R.id.soil);
        animal = findViewById(R.id.animal);
        crop = findViewById(R.id.crop);
        mPlaceOrder= findViewById(R.id.placeOrder);
        mPlaceOrderDesc = findViewById(R.id.placeOrderDesc);
        mSoil = findViewById(R.id.Soil);
        mSoilDesc = findViewById(R.id.SoilDesc);
        mAnimal = findViewById(R.id.Animal);
        mAnimalDesc = findViewById(R.id.Animaldesc);
        mMyAds = findViewById(R.id.myAds);
        mAdsDesc = findViewById(R.id.mAdsDesc);
        mCrop = findViewById(R.id.Crop);
        mCropDesc = findViewById(R.id.CropDesc);
        mRent = findViewById(R.id.Rent);
        mRentDesc = findViewById(R.id.RentDesc);
        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor =sharedPreferences.edit();
        loadLocale();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationListener = new LocationListener(){
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString());
                // Toast.makeText(DashboardActivity.this,location.toString() , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

            }
        } else

        {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // ask for permission

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            } else {

                // we have permission!

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                }


            }

        }

        placeAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,AdEquipmentActivity.class));
            }
        });

        myAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,MyAdsActivity.class));

            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rentIntent = new Intent(DashboardActivity.this,RentActivity.class);
                startActivity(rentIntent);
            }
        });

        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soilIntent = new Intent(DashboardActivity.this,TabActivity.class);
                startActivity(soilIntent);
            }
        });
        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,FetchActivity.class));

            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,FarmScheduleActivity.class));

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == english){
            String lang = "en";
            changeLocale(lang);
        }
        if(item.getItemId() == hindi){
            String lang = "hi";
            changeLocale(lang);
        }

        return super.onOptionsItemSelected(item);
    }


    private void changeLocale(String lang) {
        myLocale = new Locale(lang);//Set Selected Locale
        saveLocale(lang);//Save the selected locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
        updateTexts();//Update texts according to locale
    }
    private void updateTexts() {
        mPlaceOrder.setText(R.string.post_an_ad);
        mPlaceOrderDesc.setText(R.string.place_your_ad_here);
        mRent.setText(R.string.rent_equipments);
        mRentDesc.setText(R.string.rent_the_equipments);
        mSoil.setText(R.string.soil_analysis);
        mSoilDesc.setText(R.string.analyse_your_soil);
        mAnimal.setText(R.string.animal_management);
        mAnimalDesc.setText(R.string.manage_your_animals);
        mCrop.setText(R.string.crop_management);
        mCropDesc.setText(R.string.manage_your_crops_here);
        mMyAds.setText(R.string.my_ads);
        mAdsDesc.setText(R.string.my_ads);
    }
    private void saveLocale(String lang) {
        editor.putString(Locale_KeyValue, lang);
        editor.commit();
    }
    public void loadLocale() {
        String language = sharedPreferences.getString(Locale_KeyValue, "");
        changeLocale(language);
    }

}
