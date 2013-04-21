package com.java.mat;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.java.mat.GMapV2Direction.Mode;

public class FunctionMaps {
	
	private ArrayList<MarkerOptions> markers;
	private GMapV2Direction md;
	private ArrayList<PolylineOptions> routes;
	
	private GMapV2Places g = new GMapV2Places();
	
	//konstruktor bezarg.
	public FunctionMaps()
	{
		markers = new ArrayList<MarkerOptions>();
		md = new GMapV2Direction();
		routes = new ArrayList<PolylineOptions>();
		
		//testowe spinki
       	addLocation(52.259,21.020,"Tomek");
    	addLocation(50.259,20.020,"Mateusz");
    	addLocation(48.259,20.020,"Waldek");
    	addRoute(markers.get(0), markers.get(1),Mode.walking);
    	
    	g.getDocument(markers.get(0).getPosition());
	}
	
	//dodaje do listy jedn� spink�
	void setLocation(MarkerOptions marker)
	{
		markers.add(marker);
	}
	
	//zwraca spink� o podanym indeksie
	MarkerOptions getMarker(int index)
	{
		return markers.get(index);
	}
	
	//dodaje do listy jedn� spink� z wsp�rz�dne
	void addLocation(double x,double y,String name)
	{
		LatLng position = new LatLng(x,y);
		MarkerOptions marker = new MarkerOptions();
		marker.position(position);
		marker.visible(true);
		marker.title(name);
		markers.add(marker);
	}
	
	int getCountLocation()
	{
		return markers.size();
	}
	
	int getCountRoute()
	{
		return routes.size();
	}
	
	PolylineOptions getRoute(int index)
	{
		return routes.get(index);
	}
	
	void updateLocation()
	{

	}
	
	void updateRoute()
	{
		for(int i=0;i<routes.size();i++)
		{
			//routes
		}
			
	}
	
	PolylineOptions addRoute(MarkerOptions m1,MarkerOptions m2,Mode mode)
	{
		Document doc = md.getDocument(m1.getPosition(), m2.getPosition(), mode.toString());
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

		for(int i = 0 ; i < directionPoint.size() ; i++) {          
		rectLine.add(directionPoint.get(i));
		}
		
		routes.add(rectLine);
		
		return rectLine;
	}

}
