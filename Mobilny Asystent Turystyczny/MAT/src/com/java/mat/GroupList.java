package com.java.mat;

import java.util.ArrayList;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class GroupList extends ListActivity {

	ArrayList<String> lista = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	int rand=0;
	Random r = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab03_grupa);
		
		Button ok = (Button)findViewById(R.id.grupa_ok);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
		// android.R.layout.simple_list_item_1 jest layoutem poszczegolnych elementow na liscie
		setListAdapter(adapter);
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(getApplicationContext(), "BANG", Toast.LENGTH_SHORT);
				t.show();
				rand = r.nextInt()%10000;
				lista.add("Rand");
				adapter.notifyDataSetChanged();
			}
		});
	}
	
}
