package com.java.mat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MapRefresh implements Runnable {
	private Thread t;
	
	Handler mainHandler;
	Runnable myRunnable;
	public MapRefresh(Context context)
	{
		
		mainHandler = new Handler(context.getMainLooper());
		myRunnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SingletonInterfejsMapy.getInstance().getMapInstance().wyczyscMape();
				SingletonInterfejsMapy.getInstance().getMapInstance().rysujWszystko();
				if(GlobalSettings.getInstance().getUserLoggingStatus())
					SingletonInterfejsMapy.getInstance().addMiejsca(Connection.getMatServer().getGroupPOI(GlobalSettings.getInstance().getMail()));
			}
		};
		
		t = new Thread(this);
		t.start();
		
	}
	
	public void PobierzZnajomychDodajDoMay()
	{
		if(GlobalSettings.getInstance().getUserLoggingStatus())
		{
			String url1= "http://mat.sofect.com/server.php?key=412fg68kw378&function=getMailGuide&email="+GlobalSettings.getInstance().getMail();
			
			Pinezki znajomi = SingletonInterfejsMapy.getInstance().getMapInstance().getZnajomi();
		    znajomi.wyczyscListePinezek();
		    
			String doc="";
		    
		    try {
	        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	        	StrictMode.setThreadPolicy(policy);
		        HttpClient httpClient = new DefaultHttpClient();
		        HttpContext localContext = new BasicHttpContext();
		        HttpPost httpPost = new HttpPost(url1);
		        HttpResponse response = httpClient.execute(httpPost, localContext);
		        InputStream in = response.getEntity().getContent();
		        
		        StringBuilder sb = new StringBuilder();
				InputStreamReader reader = new InputStreamReader(in);
				BufferedReader buffer = new BufferedReader(reader);
				String cur;
				while ((cur = buffer.readLine()) != null)
					sb.append(cur + "\n");
				
					JSONObject json = new JSONObject(sb.toString());
					doc = json.getString("code");
		       
					Log.d("userDe","loll"+doc);
					
					if(Integer.parseInt(doc) == 0){
				    	doc = json.getString("email");
				    	String url2 = "http://mat.sofect.com/server.php?key=412fg68kw378&function="+
								"getGroupUsers&email="+doc;
			
				        HttpPost httpPost2 = new HttpPost(url2);
				        HttpResponse response2 = httpClient.execute(httpPost2, localContext);
				        InputStream in2 = response2.getEntity().getContent();
				        
				        StringBuilder sb2 = new StringBuilder();
						InputStreamReader reader2 = new InputStreamReader(in2);
						BufferedReader buffer2 = new BufferedReader(reader2);
						String cur2;
						while ((cur2 = buffer2.readLine()) != null)
							sb2.append(cur2 + "\n");
						
						JSONArray json2 = new JSONArray(sb2.toString());
						
						if(Integer.parseInt(json2.getJSONObject(0).getString("code"))==0)
						{
							JSONObject wiersz;
							for(int i=1; i <json2.length();i++)
							{
								wiersz = json2.getJSONObject(i);
								if(!wiersz.getString("email").equals(GlobalSettings.getInstance().getMail()))
								{
									if(doc.equals(wiersz.getString("email")))
									{
										//ustawienie innego koloru pinezki
										try{
										znajomi.dodajPinezkeDoListy(
												new LatLng(Double.parseDouble(wiersz.getString("latitude")), Double.parseDouble(wiersz.getString("longitude"))), 
												wiersz.getString("name")+" "+wiersz.getString("surname"),
												"Przewodnik status: "+wiersz.getString("status"));
										}catch(Exception e){}
										
									}else{
										try{
										znajomi.dodajPinezkeDoListy(
												new LatLng(Double.parseDouble(wiersz.getString("latitude")), Double.parseDouble(wiersz.getString("longitude"))), 
												wiersz.getString("name")+" "+wiersz.getString("surname"),
												"Znajomy status: "+wiersz.getString("status"));
										}catch(Exception e){}
									};
									Log.d("userDe","PINEZKA dodana");
								}
							}
							//zebranie wpisów dodanie do mapy
						}
				    	
				    }else{
				    	//nie nale¿y do ¿adnej grupy
					}
		    } catch (Exception e) {
		    	
		    }
		   
		}
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Log.d("user","mapa watek odswiez111");
		while(SingletonInterfejsMapy.getInstance().getMapInstance()!=null){
			
			Log.d("user","mapa watek odswiez");
			
			PobierzZnajomychDodajDoMay();

			mainHandler.post(myRunnable);

	        
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

}
