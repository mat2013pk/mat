package com.java.mat;

import message.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GroupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_group);
		Button create = (Button) findViewById(R.id.group_create);
		Button join = (Button) findViewById(R.id.group_join);

		create.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_action_group);
				Button ok = (Button) findViewById(R.id.action_group_ok);
				ok.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						IGroup group = (IGroup) new Group();
						EditText name = (EditText) findViewById(R.id.action_group_name);
						EditText pass = (EditText) findViewById(R.id.action_group_password);
						if (!(GlobalSettings.getInstance().getGuardMail() == null)) {
							Integer status = Integer.parseInt(group
									.delUserFromGroup(GlobalSettings
											.getInstance().getSecretKey(),
											GlobalSettings.getInstance()
													.getMail(), GlobalSettings
													.getInstance()
													.getGuardMail()));
							if (status == 0) {
								Log.e("DEL",
										"Usunieto poprawnie -> create group");
							} else {
								Log.e("DEL",
										"NIE usunieto poprawnie -> create group");
							}
						}
						Integer status = Integer.parseInt(group.createGroup(
								GlobalSettings.getInstance().getSecretKey(),
								GlobalSettings.getInstance().getMail(), name
										.getText().toString(), pass.getText()
										.toString()));
						Toast t;
						if (status == 0) {
							t = Toast.makeText(getApplicationContext(),
									"Grupa zostala utworzona",
									Toast.LENGTH_SHORT);

						} else {
							t = Toast.makeText(getApplicationContext(),
									"Grupa NIE zostala utworzona",
									Toast.LENGTH_SHORT);
						}
						t.show();
					}
				});
			}
		});

		join.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_action_group);
				Button ok = (Button) findViewById(R.id.action_group_ok);
				EditText name = (EditText) findViewById(R.id.action_group_name);
				name.setText("Wpisz maila przewodnika");
				ok.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						IGroup group = (IGroup) new Group();
						EditText email = (EditText) findViewById(R.id.action_group_name);
						EditText pass = (EditText) findViewById(R.id.action_group_password);
						Toast t;
						if (!libs.Validator.isValidEmail(email.getText()
								.toString())) {
							t = Toast.makeText(getApplicationContext(),
									"Niezwalidowany adres email",
									Toast.LENGTH_SHORT);
						} else {
							if (!(GlobalSettings.getInstance().getGuardMail() == null)
									&& (GlobalSettings.getInstance()
											.getGuardMail() != GlobalSettings
											.getInstance().getMail())) {
								Integer status = Integer.parseInt(group
										.delUserFromGroup(GlobalSettings
												.getInstance().getSecretKey(),
												GlobalSettings.getInstance()
														.getGuardMail(),
												GlobalSettings.getInstance()
														.getMail()));
								if (status == 0) {
									Log.e("DEL",
											"Usunieto poprawnie -> join group");
								} else {
									Log.e("DEL",
											"NIE usunieto poprawnie -> join group");
								}
							} else if (GlobalSettings.getInstance()
									.getGuardMail() == GlobalSettings
									.getInstance().getMail()) {
								//startActivity(new Intent(GroupActivity.this, GroupList.class));
								Log.e("TODO", "Wypisanie listy uzytkownikow z grupy");
							}
							Integer status = Integer.parseInt(group
									.addUserToGroup(GlobalSettings
											.getInstance().getSecretKey(),
											email.getText().toString(),
											GlobalSettings.getInstance()
													.getMail(), pass.getText()
													.toString()));

							if (status == 0) {
								GlobalSettings.getInstance().setGuardMail(
										email.getText().toString());
								t = Toast.makeText(getApplicationContext(),
										"Zosta³eœ dodany do grupy",
										Toast.LENGTH_SHORT);

							} else {
								t = Toast.makeText(getApplicationContext(),
										"Nie zosta³eœ dodany do grupy",
										Toast.LENGTH_SHORT);
							}
						}
						t.show();
					}
				});
			}
		});
	}
}
