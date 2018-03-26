package com.dk.agriculture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Tab3 extends Fragment {
    String[] values=new String[]{"Boric Acid", "Acephate", "Propoxur","DDT"};
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3,container,false);
        perform(v);
        return v;
    }
    public void perform(View v) {
        lv = (ListView) v.findViewById(R.id.pesticidesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(TabActivity.mPesticidesListener);
    }
}
