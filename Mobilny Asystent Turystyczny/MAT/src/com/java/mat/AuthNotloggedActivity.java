package com.java.mat;

import org.json.JSONException;
import org.json.JSONObject;

import com.java.mat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthNotloggedActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_autoryzacja_niezalogowany);
        
        zaloguj();
        zarejestruj();

        
	}
	
	private void zaloguj() {
        Button zaloguj = (Button)findViewById(R.id.konto_logowanie);
        zaloguj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_logowanie);
				Button send = (Button) findViewById(R.id.logowanie_ok);
				send.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final EditText email = (EditText) findViewById(R.id.logowanie_mail);
						final EditText haslo = (EditText) findViewById(R.id.logowanie_pass);
					    email.setOnFocusChangeListener(new OnFocusChangeListener()
					    {
					        @Override
					        public void onFocusChange(View v, boolean hasFocus) 
					        {
					            if (hasFocus==true)
					            {
					                if (email.getText().toString().compareTo("email")==0)
					                {
					                    email.setText("");
					                }
					            }
					        }
					    });
					    haslo.setOnFocusChangeListener(new OnFocusChangeListener()
					    {
					        @Override
					        public void onFocusChange(View v, boolean hasFocus) 
					        {
					            if (hasFocus==true)
					            {
					                if (haslo.getText().toString().compareTo("has³o")==0)
					                {
					                    haslo.setText("");
					                }
					            }
					        }
					    });
						boolean ok = Autoryzacja.zaloguj(email.getText().toString(), haslo.getText().toString());
	    				
						if(libs.Validator.isValidEmail((CharSequence) email.getText()) && ok){
							Toast t = Toast.makeText(getApplicationContext(), "Zalogowano poprawnie " + email.getText().toString(), Toast.LENGTH_SHORT);
							t.show();
							GlobalSettings.getInstance().setUserLoggedStatus(true);
							GlobalSettings.getInstance().setMail(email.getText().toString());
							GlobalSettings.getInstance().setGuardMail(getGuardMail());
							new GPSThread(AuthNotloggedActivity.this);
//							GPSThread gps = GPSThread.getInstance();
//							gps.setSend(true);
//							gps.getExecutor().scheduleAtFixedRate(gps, 0, 20, TimeUnit.SECONDS);
							
							startActivity(new Intent(AuthNotloggedActivity.this, MenuActivity.class));
						} else if (!ok){
							Toast t = Toast.makeText(getApplicationContext(), "Niepoprawny email lub haslo " + email.getText().toString(), Toast.LENGTH_SHORT);
							t.show();
						} else{
							Toast t = Toast.makeText(getApplicationContext(), "Email nie spelnia walidacji " + email.getText().toString(), Toast.LENGTH_SHORT);
							t.show();
						}

					}
					
					private String getGuardMail(){
						String url = GlobalSettings.getInstance().getHost()+ "&function=getMailGuide&email="
								+GlobalSettings.getInstance().getHost();
					    String mail = null;
					    JSONObject data = Connection.connectToServer(url);
						if(!(data == null)){
							try {
								mail = data.getString("email");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						};
						return mail;
					}
				});
			}
		});
	}

	private void zarejestruj(){
        Button zarejestruj = (Button)findViewById(R.id.konto_nowe);
        zarejestruj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_nowe_konto);
				Button send = (Button) findViewById(R.id.nowekonto_ok);

				send.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final EditText imie = (EditText) findViewById(R.id.nowekonto_imie);
						final EditText nazwisko = (EditText) findViewById(R.id.nowekonto_nazwisko);
						final EditText email = (EditText) findViewById(R.id.nowekonto_mail);
						email.setOnFocusChangeListener(new OnFocusChangeListener()
					    {
					        @Override
					        public void onFocusChange(View v, boolean hasFocus) 
					        {
					            if (hasFocus==true)
					            {
					                if (email.getText().toString().compareTo("e-mail")==0)
					                {
					                    email.setText("");
					                }
					            }
					        }
					    });
						nazwisko.setOnFocusChangeListener(new OnFocusChangeListener()
					    {
					        @Override
					        public void onFocusChange(View v, boolean hasFocus) 
					        {
					            if (hasFocus==true)
					            {
					                if (nazwisko.getText().toString().compareTo("nazwisko")==0)
					                {
					                	nazwisko.setText("");
					                }
					            }
					        }
					    });
						imie.setOnFocusChangeListener(new OnFocusChangeListener()
					    {
					        @Override
					        public void onFocusChange(View v, boolean hasFocus) 
					        {
					            if (hasFocus==true)
					            {
					                if (imie.getText().toString().compareTo("imie")==0)
					                {
					                	imie.setText("");
					                }
					            }
					        }
					    });
						boolean rejestracja = Autoryzacja.zarejestruj(email.getText().toString(), imie.getText().toString(), nazwisko.getText().toString());
						if(rejestracja && libs.Validator.isValidEmail((CharSequence) email.getText())){
							Toast t = Toast.makeText(getApplicationContext(), "Zarejestrowano poprawnie, na twoj email " + email.getText().toString() + " wyslano haslo ", Toast.LENGTH_SHORT);
							t.show();
						} else {
							Toast t = Toast.makeText(getApplicationContext(), "Nie zarejestrowano poprawnie, email " + email.getText().toString() + "jest w bazie lub jest nieprawid³owy", Toast.LENGTH_SHORT);
							t.show();
						}
					    
					}
				});
			}
		});
	}
	
	
}
