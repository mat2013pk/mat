package com.java.mat;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Trasa {
	private GoogleMap mapaGoogle;
	private ArrayList<PolylineOptions> trasa; //lista lini tworzących trase
	
	public void setGoogleMapInstance(GoogleMap instancja)
	{
		mapaGoogle = instancja;
	}
	
    public static enum Mode{
    	driving,
    	walking;
    }	
 
	public Trasa(GoogleMap wskNaInstancjeGoogleMap)
	{
		mapaGoogle = wskNaInstancjeGoogleMap;
		trasa = new ArrayList<PolylineOptions>();
	}
	public void dodajTraseDoMapy()
	{
		for(PolylineOptions p: trasa)
		{
			mapaGoogle.addPolyline(p);
		}
		//Log.d("userDebbb", "dodano trase");
	}
	public void wyznaczTrase(MarkerOptions m1,MarkerOptions m2,Mode mode)
	{
		Document doc = getDocument(m1.getPosition(), m2.getPosition(), mode);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLACK);
		
		rectLine.addAll(getDirection(doc));
		trasa.add(rectLine);
	}
	
	public void wyznaczTrase(LatLng m1,LatLng m2,Mode mode)
	{
		Document doc = getDocument(m1, m2, mode);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLACK);
		
		rectLine.addAll(getDirection(doc));
		trasa.add(rectLine);
	}

	
	
	
/****************************wklejka**************************************/
	
	
	
	
    private String getDurationText (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("duration");
        Node node1 = nl1.item(0);
        NodeList nl2 = node1.getChildNodes();
        Node node2 = nl2.item(getNodeIndex(nl2, "text"));
        Log.i("DurationText", node2.getTextContent());
        return node2.getTextContent();
    }

    private int getDurationValue (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("duration");
        Node node1 = nl1.item(0);
        NodeList nl2 = node1.getChildNodes();
        Node node2 = nl2.item(getNodeIndex(nl2, "value"));
        Log.i("DurationValue", node2.getTextContent());
        return Integer.parseInt(node2.getTextContent());
    }

    private String getDistanceText (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("distance");
        Node node1 = nl1.item(0);
        NodeList nl2 = node1.getChildNodes();
        Node node2 = nl2.item(getNodeIndex(nl2, "text"));
        Log.i("DistanceText", node2.getTextContent());
        return node2.getTextContent();
    }

    private int getDistanceValue (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("distance");
        Node node1 = nl1.item(0);
        NodeList nl2 = node1.getChildNodes();
        Node node2 = nl2.item(getNodeIndex(nl2, "value"));
        Log.i("DistanceValue", node2.getTextContent());
        return Integer.parseInt(node2.getTextContent());
    }

    private String getStartAddress (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("start_address");
        Node node1 = nl1.item(0);
        Log.i("StartAddress", node1.getTextContent());
        return node1.getTextContent();
    }

    private String getEndAddress (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("end_address");
        Node node1 = nl1.item(0);
        Log.i("StartAddress", node1.getTextContent());
        return node1.getTextContent();
    }

    private String getCopyRights (Document doc) {
        NodeList nl1 = doc.getElementsByTagName("copyrights");
        Node node1 = nl1.item(0);
        Log.i("CopyRights", node1.getTextContent());
        return node1.getTextContent();
    }

    private ArrayList<LatLng> getDirection (Document doc) {
        NodeList nl1, nl2, nl3;
        ArrayList<LatLng> listGeopoints = new ArrayList<LatLng>();
        nl1 = doc.getElementsByTagName("step");
        if (nl1.getLength() > 0) {
            for (int i = 0; i < nl1.getLength(); i++) {
                Node node1 = nl1.item(i);
                nl2 = node1.getChildNodes();

                Node locationNode = nl2.item(getNodeIndex(nl2, "start_location"));
                nl3 = locationNode.getChildNodes();
                Node latNode = nl3.item(getNodeIndex(nl3, "lat"));
                double lat = Double.parseDouble(latNode.getTextContent());
                Node lngNode = nl3.item(getNodeIndex(nl3, "lng"));
                double lng = Double.parseDouble(lngNode.getTextContent());
                listGeopoints.add(new LatLng(lat, lng));

                locationNode = nl2.item(getNodeIndex(nl2, "polyline"));
                nl3 = locationNode.getChildNodes();
                latNode = nl3.item(getNodeIndex(nl3, "points"));
                ArrayList<LatLng> arr = decodePoly(latNode.getTextContent());
                for(int j = 0 ; j < arr.size() ; j++) {
                    listGeopoints.add(new LatLng(arr.get(j).latitude, arr.get(j).longitude));
                }

                locationNode = nl2.item(getNodeIndex(nl2, "end_location"));
                nl3 = locationNode.getChildNodes();
                latNode = nl3.item(getNodeIndex(nl3, "lat"));
                lat = Double.parseDouble(latNode.getTextContent());
                lngNode = nl3.item(getNodeIndex(nl3, "lng"));
                lng = Double.parseDouble(lngNode.getTextContent());
                listGeopoints.add(new LatLng(lat, lng));
            }
        }

        return listGeopoints;
    }

    private int getNodeIndex(NodeList nl, String nodename) {
        for(int i = 0 ; i < nl.getLength() ; i++) {
            if(nl.item(i).getNodeName().equals(nodename))
                return i;
        }
        return -1;
    }

    private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }
	
	
	
/********************************************************koniec wklejki*//////////////////////////////////

	private Document getDocument(LatLng start, LatLng end, Mode tryb){
		String trybStr = tryb.toString();  
	/*
		if(tryb == Mode.driving)
		{
			trybStr = "drivng";
		}else
		{
			trybStr = "walking";
		}
	  */
	    String url = "http://maps.googleapis.com/maps/api/directions/xml?" 
	            + "origin=" + start.latitude + "," + start.longitude  
	            + "&destination=" + end.latitude + "," + end.longitude 
	            + "&sensor=false&units=metric&mode="+trybStr;
	    
	   // Log.d("userDebbbb",url);
	    
	    try {
	    	// @TODO zapytac od czego tak klasa jest StrictMode i do czego jest potrzebna
//        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        	StrictMode.setThreadPolicy(policy);
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpContext localContext = new BasicHttpContext();
	        HttpPost httpPost = new HttpPost(url);
	        HttpResponse response = httpClient.execute(httpPost, localContext);
	        InputStream in = response.getEntity().getContent();
	        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc = builder.parse(in);
	        return doc;
	    } catch (Exception e) {
	    	//MapActivity.showConnectionAllert();
	    }
	    return null;
	}

}
