package com.java.mat;

import com.java.mat.R;

import android.app.Activity;
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
						if(libs.Validator.isValidEmail((CharSequence) email.getText())){
							Toast t = Toast.makeText(getApplicationContext(), "WYSYLANIE" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						} else {
							Toast t = Toast.makeText(getApplicationContext(), "NIE WYSLANO" + email.getText(), Toast.LENGTH_SHORT);
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
						EditText email = (EditText) findViewById(R.id.nowekonto_mail);
						if(libs.Validator.isValidEmail((CharSequence) email.getText())){
							Toast t = Toast.makeText(getApplicationContext(), "WYSYLANIE" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						} else {
							Toast t = Toast.makeText(getApplicationContext(), "NIE WYSLANO" + email.getText(), Toast.LENGTH_SHORT);
							t.show();
						}

					}
				});
			}
		});
	}
	
	
}
