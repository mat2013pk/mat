package com.java.mat;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

final class Pinezki{
	private ArrayList<MarkerOptions> listaPinezek = new ArrayList<MarkerOptions>();

	private GoogleMap mapaGoogle;
	private BitmapDescriptor bitmapa;
	
	public void setGoogleMapInstance(GoogleMap instancja)
	{
		mapaGoogle = instancja;
	}
	
	public Pinezki(GoogleMap wskNaInstancjeGoogleMap, BitmapDescriptor bitmapa)
	{
		mapaGoogle = wskNaInstancjeGoogleMap;
		this.bitmapa = bitmapa;	
	}
	
	public Pinezki(GoogleMap wskNaInstancjeGoogleMap)
	{
		mapaGoogle = wskNaInstancjeGoogleMap;
		this.bitmapa = BitmapDescriptorFactory.defaultMarker();	
	}
	
	public boolean equal(MarkerOptions marker)
	{
		for(MarkerOptions m : listaPinezek)
		{
			if((m.getPosition().latitude == marker.getPosition().latitude) 
				&& (m.getPosition().longitude == marker.getPosition().longitude)
				&& m.getTitle().equals(marker.getTitle())
			){
				return true;
			}
		}
		return false;
	}
	
	public boolean equal(int id)
	{
			for(MarkerOptions m : listaPinezek)
			{
				if(Integer.parseInt(m.getSnippet()) == id)
				{
					return true;
				}
				
			}
		
		return false;
	
	}
	
	public boolean dodajPinezkeDoListy(LatLng pozycja, String nazwa, String opis, Boolean widocznosc)
	{
		MarkerOptions marker = new MarkerOptions() .position(pozycja) .visible(widocznosc) .title(nazwa) .snippet(opis) .icon(bitmapa);
		if(!equal(marker))
		{
			listaPinezek.add(marker);
			return true;
		}
			return false;
	}
	public boolean dodajPinezkeDoListy(LatLng pozycja, String nazwa, String opis)
	{
		return dodajPinezkeDoListy(pozycja, nazwa,  opis, true);
	}
	public boolean dodajPinezkeDoListy(LatLng pozycja, String nazwa)
	{
		return dodajPinezkeDoListy(pozycja, nazwa,  "", true);
	}
	
	public void dodajPinezkeDoListy(MarkerOptions marker)
	{
		listaPinezek.add(marker);
	}
	
	public void dodajPinezkiDoListy(ArrayList<MarkerOptions> markers){
		listaPinezek = markers;
	}
	
	public void dodajPinezkiDoMapy() //doddaæ sprawdzanie czy marker nie jest ju¿ dodany 
	{
	   		for(MarkerOptions m : listaPinezek)
		{
		/*if(mapaGoogle. equals(m)) 
			{
				Log.d("userDebuggggg","marker ju¿ jest");
			}else
			{*/
				mapaGoogle.addMarker(m);
			/*	Log.d("userDebuggggg","marker dodany");
			}*/
		}
	}

	public void wyczyscListePinezek()
	{
		listaPinezek.clear();
	}
	public boolean usunPinezke(int id)
	{
		if(id<listaPinezek.size()){
			listaPinezek.remove(id);
			return true;
		}else 
			return false;
	}
	public MarkerOptions getPinezka(int id)
	{
		if(id<listaPinezek.size()){
			return listaPinezek.get(id);
		}else 
			return null;
	}
	public int getIloscPinezek()
	{
		return listaPinezek.size();
	}
	
	public boolean usunPinezke(LatLng position)
	{
		for(int i=0;i<listaPinezek.size();i++)
		{
			if(listaPinezek.get(i).getPosition().equals(position))
			{
				listaPinezek.remove(i);
				return true;
			}	
		}
		return false;
	}
}
