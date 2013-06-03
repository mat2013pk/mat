package com.java.mat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GPSThread implements Runnable {
	private ScheduledExecutorService executor;
	private boolean send;
	private static GPSThread gpsThread = null;
	
	private GPSThread(){
		executor = Executors.newSingleThreadScheduledExecutor();
		send = true;
	}
	
	public static GPSThread getInstance(){
		if(gpsThread==null){
			gpsThread = new GPSThread();
		} 
		return gpsThread;
	}
	
	@Override
	public void run() {
		if(isSend()){
			sendGPSLocation();
		} else {
			executor.shutdown();
		}
	}

	/**
	 * Function send actual GPS location
	 */
	private void sendGPSLocation() {
		String url = GlobalSettings.getInstance().getHost()+ "&function=periodicPackage" +
				"&email=" + GlobalSettings.getInstance().getMail() + 
				"&latitude=11.11&longitude=12.12";
		JSONObject data = Connection.connectToServer(url);
		try {
			Log.e("adres url ", url);
			Log.e("status wiadomosci ", data.getString("message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public ScheduledExecutorService getExecutor() {
		return executor;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

}
