package com.java.mat;

import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MapFragmentActivity extends Fragment {
	private Mapa map;
		

    public void onHiddenChanged(boolean hidden)
    {
    	    	 
    	//map.getInstancjeMapyGoogle().setOnMarkerClickListener(this); //dodane lisnera na pinezki do mapy wyznaczenie trasy do pinezki
        //map.getInstancjeMapyGoogle().setOnMapClickListener(this); //wlaczenie lisnera na mape np dodanie w³asnej pinezki
    	
   	
    	if(!hidden) map.rysujWszystko();
    	super.onHiddenChanged(hidden);
    }
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	if (container == null) {
            return null;
        }
    	
    	View tmp = inflater.inflate(R.layout.activity_tab01_mapa, container, false);
        Fragment frag = getFragmentManager().findFragmentById(R.id.mapa);
        Log.d("userDebuggggg","fragment z mapa oncreate");
        map = new Mapa(frag);
        InterfejsDlaMapy.getInstance().setMapa(map);
        	
        //zaczytanie z serwera danych tuuuuuuuuuuuuuuuu
        //sprawdzic autoryzacje maila i pobrac pinezki
    	Pinezki pinezki = map.getPinezki();
    	pinezki.dodajPinezkeDoListy(new LatLng(50.061664,19.937217), "looool");
		pinezki.dodajPinezkeDoListy(new LatLng(51.061664,19.937217), "looool2");
		pinezki.dodajPinezkeDoListy(new LatLng(53.061664,19.937217), "looool2","cosik");
	
        
        return (LinearLayout)tmp;
    }
    
    
}