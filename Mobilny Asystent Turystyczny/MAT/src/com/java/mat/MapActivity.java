package com.java.mat;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;




public class MapActivity extends FragmentActivity implements OnMarkerClickListener,  OnMapClickListener/* implements LocationListener */{

	Mapa mojaMapa;
	private static ConnectionAllert connectionAllert;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mojaMapa = new Mapa(this); 						//obiekt naszej mapy
        connectionAllert = new ConnectionAllert(this);
        debug();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
    
    private void debug()
    {
    	//to zmienic w��czy� w ca�o klas pinezka i mapa referencja do funkcji wykonanej dla lisnera
    	mojaMapa.getInstancjeMapyGoogle().setOnMarkerClickListener(this); //dodane lisnera na pinezki do mapy
    	mojaMapa.getInstancjeMapyGoogle().setOnMapClickListener(this); //wlaczenie lisnera na mape
    	
    	Pinezki pinezki = mojaMapa.getPinezki();
    	pinezki.dodajPinezkeDoListy(new LatLng(50.061664,19.937217), "looool");
		pinezki.dodajPinezkeDoListy(new LatLng(51.061664,19.937217), "looool2");
		pinezki.dodajPinezkeDoListy(new LatLng(53.061664,19.937217), "looool2","cosik");
	    Miejsca miejsca = mojaMapa.getMiejsca();
		miejsca.getPlaces(new LatLng(50.061664,19.937217), "restaurant", 500);
    	
    	
    	Pinezki znojomi = mojaMapa.getZnajomi();
    	znojomi.dodajPinezkeDoListy(new LatLng(50.045664,19.967217), "Tomek", "jojoj");
    	znojomi.dodajPinezkeDoListy(new LatLng(50.045664,20.967217), "Mateusz", "dota");
    	
    	mojaMapa.getMiejsca().getPlaces(new LatLng(53.061664,19.937217), "restaurant", 10000);
    	
    	mojaMapa.odswiezMape();
    	Log.d("userDebugggg","ilosc miejsc"+mojaMapa.getMiejsca().getIloscMiejsc());
    	//trasa
    	//mojaMapa.getTrasa().wyznaczTrase(znojomi.getPinezka(0), znojomi.getPinezka(1), Trasa.Mode.driving);
    	
    }
    
    public static void showConnectionAllert()
    {
		connectionAllert.showConnectionAllert();
    }
 
    
 /********************************debug lisnery na mape i pinezki*******************************//////   
	@Override
	public boolean onMarkerClick(final Marker marker) { //tu jakas funcjonalnosc
		 marker.showInfoWindow();
		 //////////////////////tu crashhhhhhhhhhhhhhhhhhhh pozycja 
		 mojaMapa.getTrasa().wyznaczTrase(marker.getPosition(), new LatLng(mojaMapa.getMojaPozycja().getLatitude(), mojaMapa.getMojaPozycja().getLongitude()), Trasa.Mode.driving);
		 mojaMapa.odswiezMape();
		 Log.d(marker.getTitle(),"userDeb");
		return true;
	}
	@Override
	public void onMapClick(LatLng arg0) { 
		// TODO Auto-generated method stub
		
		//pinezki.wyczyscListePinezek();
		//mojaMapa.getMiejsca().getPlaces(new LatLng(53.061664,19.937217), "restaurant", 10000);
		mojaMapa.odswiezMape();
		Log.d("userDebugggg","ilosc miejsc"+mojaMapa.getMiejsca().getIloscMiejsc());
		//Log.d("mapa click "+mojaMapa.getPinezki().getIloscPinezek()+"ostatnia pinezka: "+mojaMapa.getPinezki().getPinezka(mojaMapa.getPinezki().getIloscPinezek()-1).getTitle(),"userDeb");
	}
    
  }
