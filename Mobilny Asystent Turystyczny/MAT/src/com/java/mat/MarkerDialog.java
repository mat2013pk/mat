package com.java.mat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

//klasa, która buduje dialog pod Activity, które wyœwietli opcje nawigacji oraz punktu orientacyjnego
public class MarkerDialog {
	Fragment mapActivity;
	private CharSequence[] options;
	private boolean[] selections;
	Dialog dialog;
	private Mapa map;
	private InterfejsDlaMapy ustawienia;
	
	public MarkerDialog(Marker m) {
		this.mapActivity = SingletonInterfejsMapy.getInstance().getMapActivity();
		this.map = SingletonInterfejsMapy.getInstance().getMapInstance();
		ustawienia = SingletonInterfejsMapy.getInstance();
		if(isAdmin())
		{
			selections = new boolean[3];
			if(map.getTrasa().getMarkerDestiny() != null)
			{
				if(m.getPosition().equals(map.getTrasa().getMarkerDestiny() ) )
					selections[0] = true;
			}
				//String adminMail = Connection.getMatServer().getMailGuide(GlobalSettings.getInstance().getMail() );
			int id;
			try{
				id = Integer.parseInt(m.getSnippet());
			}catch(Exception e){id = -1 ;}
			if(id != -1)
				if(SingletonInterfejsMapy.getInstance().getMiejsca().equal(Integer.parseInt(m.getSnippet())))
					selections[1] = true;
				//if(m.getPosition().equals(matServer.getGroupPOI(adminMail).getPosition() ) )
					//selections[1] = true;
				
			options = new String[]{"Wyznacz trasê", "Ustaw punkt spotkania", "Usuñ pinezkê"};
			dialog = onCreateAdminDialog(m);
		}
		else
		{
			selections = new boolean[2];
			if(map.getTrasa().getMarkerDestiny() != null)
				if(m.getPosition().equals(map.getTrasa().getMarkerDestiny() ) )
					selections[0] = true;
			options = new String[]{"Wyznacz trasê", "Usuñ pinezkê"};
			dialog = onCreateUserDialog(m);
		}
		
	}
	
	private Dialog onCreateAdminDialog(final Marker m)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mapActivity.getActivity());
	    // Set the dialog title
	    builder.setTitle("Opcje")
	    	.setCancelable(false)
	    	.setMultiChoiceItems(options, selections, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if(which == 0 && isChecked == true)
						selections[0] = true;
					else if(which == 0 && isChecked == false)
						selections[0] = false;
					else if(which == 1 && isChecked == true)
						selections[1] = true;
					else if(which == 1 && isChecked == false)
						selections[1] = false;
					else if(which == 2 && isChecked == true)
						selections[2] = true;
					else if(which == 2 && isChecked == false)
						selections[2] = false;
				}
			})
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(selections[0])
					{
						map.getTrasa().WyczyscTrase();
						map.wyczyscMape();
						map.getTrasa().wyznaczTrase
						(
								new LatLng(map.getMojaPozycja().getLatitude(),map.getMojaPozycja().getLongitude()),
								m.getPosition(),
								ustawienia.trybTrasa
						);
						map.getTrasa().dodajTraseDoMapy();
						map.rysujWszystko();
					}
					else{
						if(map.getTrasa().getMarkerDestiny() != null)
							if(m.getPosition().equals(map.getTrasa().getMarkerDestiny()))
							{
								map.getTrasa().WyczyscTrase();
								map.wyczyscMape();
								map.rysujWszystko();
								map.getTrasa().setMarkerDestiny(null);
							}
					}
					
					if(selections[1])
					{
						//dodanie miejsca do bazy
						Connection.getMatServer().addGropupPOI(GlobalSettings.getInstance().getMail(), m);
						//SingletonInterfejsMapy.getInstance().addMiejsce(m);
					}
					else{
						int id;
						try{ id = Integer.parseInt(m.getSnippet()); }catch(Exception e){ id=-1; }
						
						if(id != -1)
							if(SingletonInterfejsMapy.getInstance().getMiejsca().equal(id))
								Connection.getMatServer().removeGroupPOI(GlobalSettings.getInstance().getMail(), Integer.parseInt(m.getSnippet()));
						//matServer.removeGroupPOI(Autoryzacja.getMail(), Integer.parseInt(m.getSnippet()));
					}
						
						
					if(selections[2])
					{
						map.getPinezki().usunPinezke(m.getPosition());
						//m.remove();
						map.wyczyscMape();
						map.rysujWszystko();
					}
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			
	    return builder.create();

	}
	
	private Dialog onCreateUserDialog(final Marker m)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mapActivity.getActivity());
	    // Set the dialog title
	    builder.setTitle("Opcje")
	    	.setCancelable(false)
	    	.setMultiChoiceItems(options, selections, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if(which == 0 && isChecked == true)
						selections[0] = true;
					else if(which == 0 && isChecked == false)
						selections[0] = false;
					else if(which == 1 && isChecked == true)
						selections[1] = true;
					else if(which == 1 && isChecked == false)
						selections[1] = false;
				}
			})
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(selections[0])
					{
						map.getTrasa().WyczyscTrase();
						map.wyczyscMape();
						map.getTrasa().wyznaczTrase
						(
								new LatLng(map.getMojaPozycja().getLatitude(),map.getMojaPozycja().getLongitude()),
								//new LatLng(50.060, 19.959),
								m.getPosition(),
								ustawienia.trybTrasa
						);
						map.getTrasa().dodajTraseDoMapy();
						map.rysujWszystko();
					}
					else{
						if(map.getTrasa().getMarkerDestiny() != null)
							if(m.getPosition().equals(map.getTrasa().getMarkerDestiny()))
							{
								map.getTrasa().WyczyscTrase();
								map.wyczyscMape();
								map.rysujWszystko();
								map.getTrasa().setMarkerDestiny(null);
							}
					}
					
					if(selections[1])
					{
						map.getPinezki().usunPinezke(m.getPosition());
						//m.remove();
						map.wyczyscMape();
						map.rysujWszystko();
					}
				}
				
				
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			
	    return builder.create();

	}
	
	void showMarkerDialog()
	{
		dialog.show();
		
	}
	
	void wyznaczTrase(Marker m)
	{

	}
	
	boolean isAdmin()
	{
		if(GlobalSettings.getInstance().getUserLoggingStatus())
		{
			if( GlobalSettings.getInstance().getMail().equals(Connection.getMatServer().getMailGuide(GlobalSettings.getInstance().getMail() ) ) )
				return true;
			else
				return false;
		}
		return false;
	}
	
	
	
}