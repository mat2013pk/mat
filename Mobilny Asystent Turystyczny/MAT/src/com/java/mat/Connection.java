package com.java.mat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class Connection {
	private static boolean strictModeAvailable;
	static MatServer matServer = null;
	
	static {
        try {
            StrictModeWrapper.checkAvailable();
            strictModeAvailable = true;
        } catch (Throwable throwable) {
            strictModeAvailable = false;
        }
    }
 
	public static MatServer getMatServer()
	{
		if(matServer == null)
		{
			matServer = new MatServer();
			return matServer;
		}
		else
		{
			return matServer;
		}
	}
	

	public static JSONObject connectToServer(String url){
    	String doc = "";
    	JSONObject json;
    	Log.d("KOD","connect");
		try {
			if (strictModeAvailable) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy); 
	        }
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpContext localContext = new BasicHttpContext();
	        HttpPost httpPost = new HttpPost(url);
	        HttpResponse response = httpClient.execute(httpPost, localContext);
	        InputStream in = response.getEntity().getContent();
	        StringBuilder sb = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buffer = new BufferedReader(reader);
			String cur;
			while ((cur = buffer.readLine()) != null)
				sb.append(cur + "\n");
			
			json = new JSONObject(sb.toString());
			doc = json.getString("code");
	      
	    } catch (Exception e) {
	    	Log.e("KOD", "kod nie zero - exception"+e.toString());
	    	return null;
	    }
	    if(Integer.parseInt(doc) == 0){
	    	return json;
	    }else{
	    	Log.e("KOD", "kod nie zero");
	    	return null;
		}
	}
	
	public static ArrayList<JSONObject> connectToServerGetArray(String url2){
	    ArrayList<JSONObject> items = new ArrayList<JSONObject>();
	 
	    try {
			if (strictModeAvailable) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy); 
	        }
	        URL url = new URL(url2);
	        HttpURLConnection urlConnection =
	            (HttpURLConnection) url.openConnection();
	        urlConnection.setRequestMethod("GET");
	        urlConnection.connect();
	                    // gets the server json data
	        BufferedReader bufferedReader =
	            new BufferedReader(new InputStreamReader(
	                    urlConnection.getInputStream()));		
	            String next;
	            while ((next = bufferedReader.readLine()) != null){
	                JSONArray ja = new JSONArray(next);
	 
	                for (int i = 0; i < ja.length(); i++) {
	                    JSONObject jo = (JSONObject) ja.get(i);
	                    items.add(jo);
	                }
	            }
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return items;
	}
}
