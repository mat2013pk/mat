package com.java.mat;

import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class GroupFragmentActivity extends Fragment {
	
	InterfejsDlaMapy interfejs;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        interfejs = InterfejsDlaMapy.getInstance();
        
        
        ///testyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
       	Pinezki znojomi = interfejs.getMapInstance().getZnajomi();
    	znojomi.dodajPinezkeDoListy(new LatLng(50.045664,19.967217), "Tomek", "jojoj");
    	znojomi.dodajPinezkeDoListy(new LatLng(50.045664,20.967217), "Mateusz", "dota");
        
    	
    	
        return (LinearLayout)inflater.inflate(R.layout.activity_tab03_grupa, container, false);
    }
    
    
}