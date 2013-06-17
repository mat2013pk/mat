package com.java.mat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.StrictMode;

public class MatServer {
	
	public MatServer(){
	}

	
	public String getMailGuide(String mail)
	{
		if(GlobalSettings.getInstance().getUserLoggingStatus())
		{
			String url= "http://mat.sofect.com/server.php?key=412fg68kw378&function=getMailGuide&email="+mail;
			String doc="";
		    
		    try {
				JSONObject json = Connection.connectToServer(url);
				doc = json.getString("code");
				
				if(Integer.parseInt(doc) == 0){
					doc = json.getString("email");
					return doc;
				}
				else
					return null;
				
		    }catch(Exception e)
		    {
		    	
		    }
		}
		return null;
	}

	public boolean addGropupPOI(String adminMail, Marker marker)
	{
		if(GlobalSettings.getInstance().getUserLoggingStatus())
		{
			String url= "http://mat.sofect.com/server.php?key=412fg68kw378&function=addGroupPOI&email="+ adminMail +
					"&POIname=" + marker.getTitle() + "&latitude=" + marker.getPosition().latitude + "&longitude=" + marker.getPosition().longitude;
			
			url = url.replaceAll(" ", "%20");
			String doc="";
		    
		    try {
				JSONObject json = Connection.connectToServer(url);
				doc = json.getString("code");
				
				if(Integer.parseInt(doc) == 0)
					return true;
				else
					return false;
				
		    }catch(Exception e){
		    	e.toString();
		    }
		}
		return false;
	}

	public ArrayList<MarkerOptions> getGroupPOI(String adminMail){
		
		if(GlobalSettings.getInstance().getUserLoggingStatus())
		{
			String url= "http://mat.sofect.com/server.php?key=412fg68kw378&function=getGroupPOI&email="+ adminMail;
			String doc="";
			try {
				ArrayList<JSONObject> jsonArray = Connection.connectToServerGetArray(url);
				
				if(jsonArray == null){ return null; }
				
				ArrayList<MarkerOptions> GroupPOI = new ArrayList<MarkerOptions>();
				
				//for(JSONObject json : jsonArray)
				for(int i=1;i < jsonArray.size();i++)
				{
					MarkerOptions marker = new MarkerOptions();
					doc = jsonArray.get(i).getString("id");
					marker.snippet(doc);
					doc = jsonArray.get(i).getString("name");
					marker.title(doc);
					doc = jsonArray.get(i).getString("latitude");
					double lat = Double.parseDouble(doc);
					doc = jsonArray.get(i).getString("longitude");
					double lng = Double.parseDouble(doc);
					marker.position(new LatLng(lat, lng));
					
					GroupPOI.add(marker);
				}
				
				return GroupPOI;
				
			}catch(Exception e){}
		}
		return null;
	}

	public boolean removeGroupPOI(String adminMail, int idPOI){
		
		if(GlobalSettings.getInstance().getUserLoggingStatus())
		{
			String url= "http://mat.sofect.com/server.php?key=412fg68kw378&function=removeGroupPOI&email=" + adminMail + "&id=" + Integer.toString(idPOI);
			String doc="";
			try {
				JSONObject json = Connection.connectToServer(url);
				doc = json.getString("code");
				if(Integer.parseInt(doc) == 0)
					return true;
				else
					return false;
				
			}catch(Exception e){}
		}
		return false;
	}

}