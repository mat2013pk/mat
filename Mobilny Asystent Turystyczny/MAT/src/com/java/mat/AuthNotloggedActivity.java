package com.java.mat;

import com.java.mat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
						EditText email = (EditText) findViewById(R.id.logowanie_mail);
						EditText haslo = (EditText) findViewById(R.id.logowanie_pass);
						
						boolean ok = Autoryzacja.Zaloguj(email.getText().toString(), haslo.getText().toString());
	    				
						if(libs.Validator.isValidEmail((CharSequence) email.getText()) && ok){
							Toast t = Toast.makeText(getApplicationContext(), "Zalogowano poprawnie" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
							GlobalSettings.getInstance().setUserLoggedStatus(true);
							GlobalSettings.getInstance().setMail(email.getText().toString());
							startActivity(new Intent(AuthNotloggedActivity.this, MenuActivity.class));
						} else if (!ok){
							Toast t = Toast.makeText(getApplicationContext(), "Niepoprawny email lub haslo" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						} else{
							Toast t = Toast.makeText(getApplicationContext(), "Email nie spelnia walidacji" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						}

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
						EditText imie = (EditText) findViewById(R.id.nowekonto_imie);
						EditText nazwisko = (EditText) findViewById(R.id.nowekonto_nazwisko);
						EditText email = (EditText) findViewById(R.id.nowekonto_mail);
						boolean rejestracja = Autoryzacja.Zarejestruj(email.getText().toString(), imie.getText().toString(), nazwisko.getText().toString());
						if(rejestracja && libs.Validator.isValidEmail((CharSequence) email.getText())){
							Toast t = Toast.makeText(getApplicationContext(), "Zarejestrowano poprawnie, na twoj email wyslano haslo" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						} else {
							Toast t = Toast.makeText(getApplicationContext(), "Nie zarejestrowano poprawnie, email jest w bazie lub jest nieprawid³owy" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						}
					    
					}
				});
			}
		});
	}
	
	
}
