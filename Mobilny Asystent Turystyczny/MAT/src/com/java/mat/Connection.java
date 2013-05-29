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

public class Connection {
	/**
	 * Function return JsonObject from server
	 * @param url to server
	 * @return JsonObject
	 */
	public static JSONObject connectToServer(String url){
    	String doc = "";
    	JSONObject json;
		try {
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
	    	return null;
	    }
	    if(Integer.parseInt(doc) == 0){
	    	return json;
	    }else{
	    	return null;
		}
	}
}
