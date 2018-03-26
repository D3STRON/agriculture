package com.dk.agriculture;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity {
    ArrayList<MarketAddModel> sellerlist = new ArrayList<>();
    ListView sellers;
    CustomAdapter customAdapter;
    SQLiteHelper db = new SQLiteHelper(this);
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        sellers= findViewById(R.id.sellers);
        DatabaseReference marketbase= FirebaseDatabase.getInstance().getReference().child("market").child(getIntent().getStringExtra("equipment"));
        //Toast.makeText(this, getIntent().getStringExtra("equipment"), Toast.LENGTH_SHORT).show();
        fab= findViewById(R.id.fab);
        customAdapter= new CustomAdapter(getApplicationContext(),sellerlist);
        sellers.setAdapter(customAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sellerlist",sellerlist);
                Toast.makeText(MarketActivity.this, sellerlist.size()+"", Toast.LENGTH_SHORT).show();
                intent.putExtra("bundle",bundle);

                startActivity(intent);
            }
        });

        marketbase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot fire : dataSnapshot.getChildren())
                {
                    MarketAddModel temp = fire.getValue(MarketAddModel.class);
                     if(!temp.getRentername().equals(db.getAllValues().get("name").trim())){
                       // Toast.makeText(MarketActivity.this, db.getAllValues().get("name"), Toast.LENGTH_LONG).show();
                    sellerlist.add(temp);
                    }
                }
                putDistance(sellerlist,19,73);
                //Toast.makeText(MarketActivity.this, getIntent().getStringExtra("equipment"), Toast.LENGTH_SHORT).show();
                sort(sellerlist,0,sellerlist.size()-1);
                customAdapter= new CustomAdapter(getApplicationContext(),sellerlist);
                sellers.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void putDistance(ArrayList<MarketAddModel> sellerList, double lat, double lon){
        for(int i=0;i<sellerList.size();i++)
        {
            double dist= distance(lat,lon,Double.parseDouble(sellerList.get(i).getLatitude()),Double.parseDouble(sellerList.get(i).getLongitude()));
            sellerList.get(i).setDistance(Double.toString(dist));
            //Toast.makeText(MarketActivity.this, sellerList.get(i).distance, Toast.LENGTH_SHORT).show();
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    static void sort(ArrayList<MarketAddModel> sellerList, int low, int high) {// merge sort
        if (low < high) {
            ArrayList<MarketAddModel> temp = new ArrayList<>();
            int mid = low + (high - low) / 2;
            int a = low, b = mid + 1;
            sort(sellerList, low, mid);
            sort(sellerList, mid + 1, high);
            while (a <= mid || b <= high) {
                if (a <= mid && b <= high) {
                    if (Double.parseDouble(sellerList.get(a).getDistance()) <= Double.parseDouble(sellerList.get(b).getDistance())) {
                        temp.add(sellerList.get(a));
                        a++;
                    } else {
                        temp.add(sellerList.get(b));
                        b++;
                    }
                } else if (a <= mid && b > high) {
                    temp.add(sellerList.get(a));
                    a++;
                } else if (b <= high && a > mid) {
                    temp.add(sellerList.get(b));
                    b++;
                }
            }
            for (int i = 0; i < temp.size(); i++) {
                sellerList.set(i + low, temp.get(i));
            }
        }
    }

    public class CustomAdapter extends BaseAdapter implements ListAdapter{
        ArrayList<MarketAddModel> sellerlist =new ArrayList<>();
        Context context;
        CustomAdapter(Context context, ArrayList<MarketAddModel> sellerlist){
            this.context=context;
            this.sellerlist=sellerlist;
        }
        @Override
        public int getCount() {
            return sellerlist.size();
        }

        @Override
        public Object getItem(int i) {
            return sellerlist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row2, null);
            }
            Button msgBtn= view.findViewById(R.id.msgBtn);
            msgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String messageToSend = "I want to rent your "+sellerlist.get(i).getEquipment()+"\nEquipment description: " +
                            sellerlist.get(i).getEquipmentdesc();
                    String number = sellerlist.get(i).getRenternumber();

                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
                    Toast.makeText(MarketActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();
                }
            });
            TextView Equipment,EquipmentDesc,MaxDays,PricePerDay,Renter,Number,Distance;
            Equipment = view.findViewById(R.id.equipmentVal);
            EquipmentDesc = view.findViewById(R.id.equipmentdescVal);
            MaxDays = view.findViewById(R.id.maxdaysVal);
            PricePerDay= view.findViewById(R.id.pricePerDayVal);
            Renter = view.findViewById(R.id.renterNameVal);
            Number = view.findViewById(R.id.numberVal);
            Distance = view.findViewById(R.id.distanceVal);
            Equipment.setText(sellerlist.get(i).getEquipment());
            EquipmentDesc.setText(sellerlist.get(i).getEquipmentdesc());
            MaxDays.setText(sellerlist.get(i).getMaxdays());
            PricePerDay.setText(sellerlist.get(i).getPriceperday());
            Renter.setText(sellerlist.get(i).getRentername());
            Number.setText(sellerlist.get(i).getRenternumber());
            Distance.setText(sellerlist.get(i).getDistance());
            return view;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
}