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
import org.json.JSONObject;

public class Autoryzacja {

	private static boolean zalogowany = false;
	private static String mail ="";
	private Autoryzacja(){}
	public static boolean getState()
	{
		return zalogowany;
	}
	public static String getMail()
	{
		return mail;
	}
	public static boolean Zarejestruj(String email, String imie, String nazwisko){
		String url = GlobalSettings.getInstance().getHost()+ "&function=registerUser&name=" 
	            +imie+"&surname="
				+nazwisko.toString()+"&email="
				+email;
	    
		String doc;
	    
	    try {
//        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        	StrictMode.setThreadPolicy(policy);
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
			
				JSONObject json = new JSONObject(sb.toString());
				doc = json.getString("code");
	       
	    } catch (Exception e) {
	    	return false;
	    }
	    if(Integer.parseInt(doc) == 0){
	    	return true;
	    }else{
	    	return false;
		}
	}
	public static boolean Zaloguj(String email, String hasloMd5 )
	{
		
		String url = GlobalSettings.getInstance().getHost() + "&function=loginUser&email=" 
	            +email+"&password="
				+hasloMd5;
	    
		String doc="";
	    
	    try {
//        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        	StrictMode.setThreadPolicy(policy);
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
			
				JSONObject json = new JSONObject(sb.toString());
				doc = json.getString("code");
	       
	    } catch (Exception e) {
	    	Wyloguj();
	    	return false;
	    }
	    if(Integer.parseInt(doc) == 0){
	    	zalogowany = true;
	    	mail= email;
	    	return true;
	    }else{
	    	Wyloguj();
	    	return false;
		}

	}
	public static void Wyloguj()
	{
		mail ="";
		zalogowany = false;
	}
	
}