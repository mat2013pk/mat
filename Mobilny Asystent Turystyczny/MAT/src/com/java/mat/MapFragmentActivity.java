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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MapFragmentActivity extends Fragment {
	private Mapa map;
	

	
	
    public void onHiddenChanged(boolean hidden)
    {
    	    	 
    	
    	if(!hidden){
    		
    		//PobierzZnajomychDodajDoMay();
    		map.rysujWszystko();
    	}
    	super.onHiddenChanged(hidden);
    }
	
    public void onCreate(Bundle savedInstanceState) 
    {
    	Log.d("userDebuggggg"," oncreate");
    	super.onCreate(savedInstanceState);
    	//SingletonInterfejsMapy.getInstance().setMapActivity(this);
    }
    

    
    /*
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    { 
    	if (keyCode == KeyEvent.KEYCODE_BACK) 
    	{ 
    		Intent intent = new Intent(ActivityTwo.this, ActivityOne.class);
    		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); startActivity(intent); 
    	} 
    return super.onKeyDown(keyCode, event); 
    }
    */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	if (container == null) {
            return null;
        }
    	
    	View tmp = inflater.inflate(R.layout.activity_tab01_mapa, container, false);
        Fragment frag = getFragmentManager().findFragmentById(R.id.mapa);
        Log.d("userDebuggggg","fragment z mapa oncreate");
        map = new Mapa(frag);
        SingletonInterfejsMapy.getInstance().setMapa(map);
        	
        new MapRefresh(this.getActivity());
        
        return (LinearLayout)tmp;
    }
    public void onDestroy()
    {
    	SingletonInterfejsMapy.getInstance().setMapa(null);
    	super.onDestroy();
    }
    
    
}