package com.java.mat;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;



final class Miejsca {
	Pinezki znalezoneMiejsca;
	GoogleMap wskNaInstancjeGoogleMap;
	String klucz = "AIzaSyASEwRkZt-0VSm5aPL32rbDD2CwpaZdxlE"; //autoryzacja dla api
	
	public int getIloscMiejsc()
	{
		return znalezoneMiejsca.getIloscPinezek();
	}
	public Miejsca(GoogleMap wskNaInstancjeGoogleMap, BitmapDescriptor bitmapa)
	{
		this.wskNaInstancjeGoogleMap = wskNaInstancjeGoogleMap;
		znalezoneMiejsca = new Pinezki(wskNaInstancjeGoogleMap, bitmapa);
	}
	public Miejsca(GoogleMap wskNaInstancjeGoogleMap)
	{
		this.wskNaInstancjeGoogleMap = wskNaInstancjeGoogleMap;
		znalezoneMiejsca = new Pinezki(wskNaInstancjeGoogleMap, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
	}
	
	public void dodajMiejscaDoMapy()
	{
		znalezoneMiejsca.dodajPinezkiDoMapy();
	}
	public void wyczyscMiejsca()
	{
		znalezoneMiejsca.wyczyscListePinezek();
	}
	public void getPlaces (LatLng pozycjaStartowa, String nazwaMiejsca, int promien) 
			throws ClientProtocolException, IOException, ParserConfigurationException, SAXException 
	{
		Document document = getDocument(pozycjaStartowa, nazwaMiejsca, promien);
		if(document != null) 
		{
			NodeList listaZnalezonych = document.getElementsByTagName("result");
			
			Log.d("userDebuggggg","ile wierszy"+listaZnalezonych.getLength());
			
			NodeList tmpList;
			
			String nazwa="", kolo="", lat="" ,lng="";

			if(listaZnalezonych.getLength()>0)
			{
				for(int i = 0 ; i <listaZnalezonych.getLength(); i++)
				{
					tmpList = listaZnalezonych.item(i).getChildNodes();//pojedynczy rekord result i
					for(int j = 0 ; j < tmpList.getLength() ; j++) {
			            
						if(tmpList.item(j).getNodeName().equals("name"))
			            {
			            	nazwa = tmpList.item(j).getTextContent();
			            }else
			            if(tmpList.item(j).getNodeName().equals("vicinity"))
			            {
			            	kolo = tmpList.item(j).getTextContent();
			            }else
			         /*   if(tmpList.item(j).getNodeName().equals("type"))
			            {
			            	opis += tmpList.item(j).getTextContent();
			            }else*/
			            if(tmpList.item(j).getNodeName().equals("geometry"))
			            {
			            	NodeList tmp = tmpList.item(j).getChildNodes();
			            	for(int k=0;k<tmp.getLength();k++)
			            	{
			            		if(tmp.item(k).getNodeName().equals("location"))
				            	{
			            			for(int l=0;l<tmp.item(k).getChildNodes().getLength();l++)
			            			{
			            				if(tmp.item(k).getChildNodes().item(l).getNodeName().equals("lat"))
			            				{
			            					lat = tmp.item(k).getChildNodes().item(l).getTextContent();
			            				}
			            				if(tmp.item(k).getChildNodes().item(l).getNodeName().equals("lng"))
			            				{
			            					lng = tmp.item(k).getChildNodes().item(l).getTextContent();
			            				}
			            			}
			            			
				            	}	
			            	}
			            	
			            }
			        }
					//Log.d("userDebuggggg",nazwa+kolo+lat+lng);
					/*"http://maps.gstatic.com/mapfiles/place_api/icons/cafe-71.png"*/

					znalezoneMiejsca.dodajPinezkeDoListy(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), nazwa, kolo);
				}
				
			}
		}
	 }
	
	private Document getDocument(LatLng pozycjaStartowa, String nazwaMiejsca, int promien) 
			throws ClientProtocolException, IOException, ParserConfigurationException, SAXException {
        String url = "https://maps.googleapis.com/maps/api/place/search/xml?" 
                + "location=" + pozycjaStartowa.latitude + "," + pozycjaStartowa.longitude  
                + "&radius=" + String.valueOf(promien)
                + "&types=" + nazwaMiejsca
                + "&sensor=false" 
                + "&key=" + klucz;

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
 }
	

}
