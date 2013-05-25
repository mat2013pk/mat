package com.java.mat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.java.mat.R;

import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;



public class Mapa extends FragmentActivity implements LocationListener{
	
	private GoogleMap mapaGoogle;			//referencja do obiektu mapygoogla
	private FragmentActivity wskNaActivity; //referencja do activity
	private Pinezki pinezki;				//referencja na pinezki
	private Miejsca miejsca;
	private Pinezki znajomi; 				//pinezki z pozycja znajomych
	private Trasa trasa;
	
	//zamenc na liste pinezek ktra bêdzie odœwierzana 
	//trasy wyznaczone lista do wyrysowania
	public Pinezki getZnajomi()
	{
		return znajomi;
	}
	
	public Pinezki getPinezki()
	{
		return pinezki;
	}
	public Miejsca getMiejsca()
	{
		return miejsca;
	}
	public Trasa getTrasa()
	{
		return trasa;
	}

	public GoogleMap getInstancjeMapyGoogle()
	{
		return mapaGoogle;
	}
	

	public void odswiezMape()
	{
		//wyrysowanie obiektow na nowo lub dorysowane aktualnych usuniece nie aktualnych
		pinezki.dodajPinezkiDoMapy();
		miejsca.dodajMiejscaDoMapy();
		znajomi.dodajPinezkiDoMapy();
		trasa.dodajTraseDoMapy();
	}

	
	
	public Mapa(FragmentActivity wskNaActivity)
	{
		this.wskNaActivity = wskNaActivity;
		
	    initMapaGoogle();
	    pinezki = new Pinezki(mapaGoogle); //jeden rodzaj pinezek
	    miejsca = new Miejsca(mapaGoogle);
	    znajomi = new Pinezki(mapaGoogle, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
	    trasa = new Trasa(mapaGoogle);
	}
	
	
	public void wyczyscMape()
	{
		mapaGoogle.clear();
	}
	public Location getMojaPozycja()
	{
		return mapaGoogle.getMyLocation();
	}
	
	private void initMapaGoogle()
	{
		if(isGooglePlay(wskNaActivity)){
			if(mapaGoogle == null) //setup mapy
	    	{
	    		
				mapaGoogle = ((SupportMapFragment)wskNaActivity.getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    		
	    		if(mapaGoogle !=null){
	    			//add some code
	    			mapaGoogle.setMyLocationEnabled(true);
	    			LocationManager lm = (LocationManager)wskNaActivity.getSystemService(LOCATION_SERVICE);
	    			String provider = lm.getBestProvider(new Criteria(), true);
	    			
		    			if(provider == null){
		    				this.onProviderDisabled(provider);
		    			}
	    			
	    			Location loc = lm.getLastKnownLocation(provider);
		    			if(loc != null){
		    				this.onLocationChanged(loc);
		    			}	
	    		}
	    	}
		}
	}
	private boolean isGooglePlay(FragmentActivity wskNaActivity)
    {
    	int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(wskNaActivity);
    	if(status == ConnectionResult.SUCCESS)
    	{
    		return true;
    	}
    	else
    	{
    		((Dialog)GooglePlayServicesUtil.getErrorDialog(status, wskNaActivity, 10)).show();
    	}
    	return false;
    }

	@Override
	public void onLocationChanged(Location loc) {//ustawienie kamery na aktualna pozycje 
		// TODO Auto-generated method stub
		LatLng latlng = new LatLng(loc.getLatitude(),loc.getLongitude());
		mapaGoogle.moveCamera(CameraUpdateFactory.newLatLng(latlng));
		mapaGoogle.animateCamera(CameraUpdateFactory.zoomTo(10));
	}
	@Override
	public void onProviderDisabled(String provider) {//jesli nie znaleziono dostawcy
		// TODO Auto-generated method stub
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub	
	}

}
