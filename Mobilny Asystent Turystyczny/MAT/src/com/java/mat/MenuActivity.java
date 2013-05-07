package com.java.mat;


import java.util.ArrayList;
import java.util.List;

import com.example.mat.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        
    	Button autoryzacja = (Button)findViewById(R.id.menu_autoryzacja);
    	Button mapa = (Button)findViewById(R.id.menu_nawigacja);
    	Button grupa = (Button)findViewById(R.id.menu_grupa);
    	Button miejsca = (Button)findViewById(R.id.menu_miejsca);
    	Button wiadomosci = (Button)findViewById(R.id.menu_wiadomosci);
    	Button aparat = (Button)findViewById(R.id.menu_aparat);
    	Button sos = (Button)findViewById(R.id.menu_sos);
    	Button faq = (Button)findViewById(R.id.menu_faq);
    	Button informacje = (Button)findViewById(R.id.menu_info);
    	Button ustawienia = (Button)findViewById(R.id.menu_ustawienia);
    	Button wyjscie = (Button)findViewById(R.id.menu_wylacz);
    	
    	List<Button> buttonsToRelease = new ArrayList<Button>();
    	buttonsToRelease.add(grupa);
    	buttonsToRelease.add(miejsca);
    	buttonsToRelease.add(wiadomosci);
    	buttonsToRelease.add(aparat);
    	buttonsToRelease.add(sos);
    	
    	for(Button b : buttonsToRelease)
    	{
    		b.setEnabled(false);
    	}

    	if(GlobalSettings.getInstance().getUserLoggingStatus())
    	{
    		for(Button b : buttonsToRelease)
    			b.setEnabled(true);
    	}
    	
    	
        autoryzacja.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GlobalSettings.getInstance().getUserLoggingStatus() == false)
					startActivity(new Intent(MenuActivity.this, AuthNotloggedActivity.class));
				else
					startActivity(new Intent(MenuActivity.this, AuthLoggedActivity.class));
				
			}
		});
        
        mapa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this, TabholderActivity.class));
				
			}
		});
        
        grupa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        miejsca.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        wiadomosci.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

        aparat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        sos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        faq.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        informacje.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
        ustawienia.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        wyjscie.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
    }

}
