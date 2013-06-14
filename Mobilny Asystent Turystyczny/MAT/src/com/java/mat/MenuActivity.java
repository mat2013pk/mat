package com.java.mat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import message.Group;
import message.IGroup;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);

		Button autoryzacja = (Button) findViewById(R.id.menu_autoryzacja);
		Button mapa = (Button) findViewById(R.id.menu_nawigacja);
		Button grupa = (Button) findViewById(R.id.menu_grupa);
		Button miejsca = (Button) findViewById(R.id.menu_miejsca);
		Button wiadomosci = (Button) findViewById(R.id.menu_wiadomosci);
		Button aparat = (Button) findViewById(R.id.menu_aparat);
		Button sos = (Button) findViewById(R.id.menu_sos);
		Button faq = (Button) findViewById(R.id.menu_faq);
		Button informacje = (Button) findViewById(R.id.menu_info);
		Button ustawienia = (Button) findViewById(R.id.menu_ustawienia);
		Button wyjscie = (Button) findViewById(R.id.menu_wylacz);

		List<Button> buttonsToRelease = new ArrayList<Button>();
		buttonsToRelease.add(grupa);
		buttonsToRelease.add(miejsca);
		buttonsToRelease.add(wiadomosci);
		buttonsToRelease.add(aparat);
		buttonsToRelease.add(sos);

		for (Button b : buttonsToRelease) {
			b.setEnabled(false);
		}

		if (GlobalSettings.getInstance().getUserLoggingStatus()) {
			for (Button b : buttonsToRelease)
				b.setEnabled(true);
		}

		autoryzacja.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (GlobalSettings.getInstance().getUserLoggingStatus() == false)
					startActivity(new Intent(MenuActivity.this,
							AuthNotloggedActivity.class));
				else {
					startActivity(new Intent(MenuActivity.this,
							AuthLoggedActivity.class));
				}
			}
		});

		mapa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this,
						TabholderActivity.class));

			}
		});

		grupa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this, GroupActivity.class));
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
				//if (GlobalSettings.getInstance().isMessageStatus()){
				//	v.setBackgroundColor(Color.RED);
					startActivity(new Intent(MenuActivity.this, MessageActivity.class));
				//}else {
				//	v.setBackgroundColor(Color.LTGRAY);
				//}
			}
		});

		aparat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//Testy do listy uzytkownikow w grupie //
				IGroup test = (IGroup) new Group();
				ArrayList<JSONObject> groupList = test.getListGroup(GlobalSettings
						.getInstance().getSecretKey(), GlobalSettings
						.getInstance().getMail());
				List<String> listContents = new ArrayList<String>();
				for (JSONObject jo : groupList){
					try {
						listContents.add(jo.getString("name").toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setContentView(R.layout.activity_tab03_grupa);
				//ListView lv = (ListView) findViewById(R.id.group_list);
				//lv.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_group_list, listContents);
			}
		});

		sos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MenuActivity.this, SosActivity.class));
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
				GlobalSettings.getInstance().setUserLoggedStatus(false);
				finish();
			}
		});

	}

}
