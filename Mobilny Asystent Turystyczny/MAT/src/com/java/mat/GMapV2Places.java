package com.java.mat;

import java.io.Console;

import org.w3c.dom.Document;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class GMapV2Places {
	
	public GMapV2Places() { }
	
	 public Document getDocument(LatLng loc) {
	        String url = "https://maps.googleapis.com/maps/api/place/search/json" 
	                + "?location=" + loc.latitude + "," + loc.longitude  
	                + "&radius=" + 1000
	                + "&sensor=false" 
	                + "&key=" + "AIzaSyCbnY791sIlMq_Nufk_Bdp-DdNRu79MGXA";
	        
	        Log.i("DurationText",url);
	        System.out.println(url);
	        return null;
	 }
}
