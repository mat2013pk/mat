package com.java.mat;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

public class InterfejsDlaMapy
{
	protected boolean trasa, znajomi, pinezki, miejsca, trasaWyznaczanie, dodajPinezke;
	protected Trasa.Mode trybTrasa;
	protected Mapa mapa;
	protected Fragment mapActivity;
	private Pinezki listaMiejsc;
	private GoogleMap googleMap;
	
	
	public InterfejsDlaMapy()
	{
		trasa = true;
		znajomi= true;
		pinezki= true;
		miejsca= true;
		trasaWyznaczanie = false;
		dodajPinezke = false;
		trybTrasa = Trasa.Mode.walking;
		listaMiejsc = new Pinezki(googleMap);
	}
	
	public GoogleMap getGoogleMapInstance(){ return googleMap; }
	public void setGoogleMap(GoogleMap googleMap){ this.googleMap = googleMap; listaMiejsc.setGoogleMapInstance(googleMap); }
	
	public Mapa getMapInstance(){ return mapa; }
	public void setMapa(Mapa mapa){ this.mapa = mapa;}
	
	public void setMapActivity(Fragment mapActivity){
		this.mapActivity = mapActivity; 
		}
	public Fragment getMapActivity(){ return this.mapActivity; }
	
	public void addMiejsce(MarkerOptions marker){ this.listaMiejsc.dodajPinezkeDoListy(marker); }
	public void addMiejsca(ArrayList<MarkerOptions> markers){ this.listaMiejsc.dodajPinezkiDoListy(markers); }
	public Pinezki getMiejsca(){ return listaMiejsc; }
	
	public void setTrasa(boolean val)  { trasa = val;   }
	public void setZnajomi(boolean val){ znajomi = val; }
	public void setPinezki(boolean val){ pinezki = val; }
	public void setMiejsca(boolean val){ miejsca = val; }
	public void setWyznaczanieTrasy(boolean val){ trasaWyznaczanie = val;}
	public void setDodawaniePinezek(boolean val){ dodajPinezke = val; };
	public void setTrybTrasa(Trasa.Mode val)
	{
		trybTrasa = val;
	}
}
