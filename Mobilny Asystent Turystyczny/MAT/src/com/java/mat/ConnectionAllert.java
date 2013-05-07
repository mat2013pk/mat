package com.java.mat;

import java.lang.reflect.Method;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ConnectionAllert {
	
	FragmentActivity wskNaActivity;
	private CharSequence[] options = {"Enable Data Connection", "Enable WiFi Connection"};
	private boolean[] selections;
	private WifiManager wifiManager;
	Dialog dialog;
	
	public ConnectionAllert(FragmentActivity wskNaActivity) {
		this.wskNaActivity = wskNaActivity;
		selections = new boolean[2];
		wifiManager = (WifiManager)wskNaActivity.getSystemService(Context.WIFI_SERVICE);
		haveNetworkConnection();
		dialog = onCreateDialog();
	}
	
	private void haveNetworkConnection() {
	    selections[1] = false;
	    selections[0] = false;

	    ConnectivityManager cm = (ConnectivityManager)wskNaActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    
	    if(wifi.isConnectedOrConnecting())
	    	selections[1]=true;
	    
	    if(mobile.isConnectedOrConnecting())
	    	selections[0]=true;
	}

	private Dialog onCreateDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(wskNaActivity);
	    // Set the dialog title
	    builder.setTitle("Connection Error")
	    	.setCancelable(false)
	    	.setMultiChoiceItems(options, selections, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if(which == 0 && isChecked == true)
						turnOnDataConnection(true, wskNaActivity.getApplicationContext());
					else if(which == 0 && isChecked == false)
						turnOnDataConnection(false, wskNaActivity.getApplicationContext());
					else if(which == 1 && isChecked == true)
						wifiManager.setWifiEnabled(true);
					else if(which == 1 && isChecked == false)
						wifiManager.setWifiEnabled(false);
				}
			})
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//wskNaActivity.odswiezMape();
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			
	    return builder.create();

	}
	
	void showConnectionAllert()
	{
		dialog.show();
	}

	boolean turnOnDataConnection(boolean ON,Context context)
	 {

	     try{
	    	 //log.i("App running on Ginger bread+");
	         final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	         final Class<?> conmanClass = Class.forName(conman.getClass().getName());
	         final java.lang.reflect.Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
	         iConnectivityManagerField.setAccessible(true);
	         final Object iConnectivityManager = iConnectivityManagerField.get(conman);
	         final Class<?> iConnectivityManagerClass =  Class.forName(iConnectivityManager.getClass().getName());
	         final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	         setMobileDataEnabledMethod.setAccessible(true);
	         setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);
	         return true;
	     }catch(Exception e){
	    	 Log.i("dasd","error turning on/off data");
	    	 return false;
	    	 }
	 }

}