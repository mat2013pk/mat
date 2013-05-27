package com.java.mat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;



public class Mapa extends FragmentActivity implements LocationListener{
	
	private GoogleMap mapaGoogle;			//referencja do obiektu mapygoogla
	private Fragment wskNaFragment; //referencja do activity
	private Pinezki pinezki;				//referencja na pinezki
	private Miejsca miejsca;
	private Pinezki znajomi; 				//pinezki z pozycja znajomych
	private Trasa trasa;
	private InterfejsDlaMapy ustawienia;
	
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
	

	public void rysujWszystko()
	{
		mapaGoogle.clear();
		//wyrysowanie obiektow na nowo lub dorysowane aktualnych usuniece nie aktualnych
		if(ustawienia.pinezki) pinezki.dodajPinezkiDoMapy();
		if(ustawienia.miejsca) miejsca.dodajMiejscaDoMapy();
		if(ustawienia.znajomi) znajomi.dodajPinezkiDoMapy();
		if(ustawienia.trasa) trasa.dodajTraseDoMapy();
	}


	
	public Mapa(Fragment MapFragment)
	{
		
		this.wskNaFragment = MapFragment;		
		this.ustawienia = InterfejsDlaMapy.getInstance();
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
		if(isGooglePlay(wskNaFragment)){
			if(mapaGoogle == null) //setup mapy
	    	{
				mapaGoogle = ((SupportMapFragment)wskNaFragment).getMap();
				//mapaGoogle = ((SupportMapFragment)wskNaFragment.getSupportFragmentManager().findFragmentById(R.id.mapa)).getMap();
	    		
	    		if(mapaGoogle !=null){
	    			//add some code
	    			mapaGoogle.setMyLocationEnabled(true);
	    			LocationManager lm = (LocationManager)wskNaFragment.getActivity().getSystemService(Activity.LOCATION_SERVICE);
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
	private boolean isGooglePlay(Fragment wskNaFragment)
    {
    	int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(wskNaFragment.getActivity());
    	if(status == ConnectionResult.SUCCESS)
    	{
    		return true;
    	}
    	else
    	{
    		((Dialog)GooglePlayServicesUtil.getErrorDialog(status, wskNaFragment.getActivity(), 10)).show();
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
