package com.java.mat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class GPSThread implements Runnable {
	private Thread t;
	private GPSTracker gps;
	Double longtitude;
	Double latitude;

	public GPSThread(Context m) {
		t = new Thread(this);
		gps = new GPSTracker(m);
		t.start();
		// send = true;
	}

	@Override
	public void run() {
		while (GlobalSettings.getInstance().getUserLoggingStatus()) {
			sendGPSLocation();
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.e("BLAD W SENDZIE", "NIE DZIALA WATEK");
			}
		}
		Log.e("logout", "poszlo");
	}

	/**
	 * Function send actual GPS location
	 */
	private void sendGPSLocation() {
		boolean status = false;
		String url = GlobalSettings.getInstance().getHost()
				+ "&function=periodicPackage" + "&email="
				+ GlobalSettings.getInstance().getMail() + "&latitude="
				+ gps.getLatitude() + "&longitude=" + gps.getLongitude();
		JSONObject data = Connection.connectToServer(url);
		try {
			Log.e("adres url ", url);
			Log.e("status wiadomosci ", data.getString("message"));

			if (data.getString("message") == "true") {
				status = true;
			} else {
				status = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			status = false;
		}
		GlobalSettings.getInstance().setMessageStatus(status);

	}

}
