package com.java.mat;

import org.json.JSONException;
import org.json.JSONObject;

import com.java.mat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AuthLoggedActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_autoryzacja_zalogowany);
        
        mojeDane();
        zmienHaslo();
        wyloguj();
       
	}

	private void wyloguj() {
		Button wyloguj = (Button) findViewById(R.id.zalogowany_wyloguj);
		wyloguj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentView(R.layout.dialog_logout);
				Button tak = (Button) findViewById(R.id.logout_y);
				tak.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						GlobalSettings.getInstance().setMail("");
						GlobalSettings.getInstance().setUserLoggedStatus(false);
						GPSThread.getInstance().setSend(false);
						startActivity(new Intent(AuthLoggedActivity.this, MenuActivity.class));
					}
				});
				
				Button nie = (Button) findViewById(R.id.logout_n);
				nie.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						setContentView(R.layout.activity_autoryzacja_zalogowany);
					}
				});
			}
		});
		
	}

	private void zmienHaslo() {
		Button zmienHaslo = (Button) findViewById(R.id.zalogowany_zmienhaslo);
		zmienHaslo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_zmien_haslo);
				Button send = (Button) findViewById(R.id.logowanie_ok);
				send.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						EditText pass1 = (EditText) findViewById(R.id.zmienpass_pass1);
						EditText pass2 = (EditText) findViewById(R.id.zmienpass_pass2);
						if((pass1.getText().toString()).equals((pass2.getText().toString()))){
							String url = GlobalSettings.getInstance().getHost()+ "&function=setUserPassword&email="
									+GlobalSettings.getInstance().getMail()+"&password="+pass1.getText().toString();
							if(Connection.connectToServer(url)!=null){
								Toast t = Toast.makeText(getApplicationContext(), "Poprawnie zmieniono haslo", Toast.LENGTH_SHORT);
								t.show();
								setContentView(R.layout.activity_autoryzacja_zalogowany);
							} else {
								Toast t = Toast.makeText(getApplicationContext(), "Blad po stronie serwera, sprobuj ponownie pozniej", Toast.LENGTH_SHORT);
								t.show();
							}
						} else {
							Toast t = Toast.makeText(getApplicationContext(), "Has³a nie s¹ takie same", Toast.LENGTH_SHORT);
							t.show();
						}
					}
				});
			}
		});	
	}

	private void mojeDane() {
		Button mojeDane = (Button) findViewById(R.id.zalogowany_mojedane);
		mojeDane.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = GlobalSettings.getInstance().getHost()+ "&function=getUserData&email="
						+GlobalSettings.getInstance().getMail();
				JSONObject data = Connection.connectToServer(url);
				if (data==null){
					
				} else {
					setContentView(R.layout.activity_moje_dane);
					TextView tv_imie_i_nazwisko = (TextView) findViewById(R.id.moje_dane_imie_i_nazwisko);
					TextView tv_mail = (TextView) findViewById(R.id.moje_dane_mail);
					TextView tv_grupa = (TextView) findViewById(R.id.moje_dane_grupa);
					TextView tv_status = (TextView) findViewById(R.id.moje_dane_status);
					try {
						tv_imie_i_nazwisko.setText(data.getString("name") + " " + data.getString("surname"));
						tv_mail.setText(GlobalSettings.getInstance().getMail());
						tv_grupa.setText("Grupa: " + data.getString("groupName"));
						tv_status.setText("Status: " + data.getString("status"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
	}
	
	
}
