package com.java.mat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import message.*;

public class SosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sos);
		Button wiadomosc1 = (Button) findViewById(R.id.sos_message1);
		Button wiadomosc2 = (Button) findViewById(R.id.sos_message2);
		Button wiadomosc3 = (Button) findViewById(R.id.sos_message3);
		
		wiadomosc1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				IMessage wiadomosc = (IMessage) new Message();
				Integer status = Integer.parseInt(wiadomosc.sendMsg(
						"412fg68kw378", GlobalSettings.getInstance().getMail(),
						null, "Potrzebuje pomocy", null, false));
				Toast t;
				if (status == 0) {
					t = Toast.makeText(getApplicationContext(),
							"Wiadomosc zosta³a wys³ana poprawnie",
							Toast.LENGTH_SHORT);

				} else {
					t = Toast.makeText(getApplicationContext(),
							"Wiadomosc NIE zosta³a wys³ana poprawnie",
							Toast.LENGTH_SHORT);
				}
				t.show();
			}
		});

		wiadomosc2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				IMessage wiadomosc = (IMessage) new Message();
				Integer status = Integer.parseInt(wiadomosc.sendMsg(
						"412fg68kw378", GlobalSettings.getInstance().getMail(),
						null, "Ustalmy miejsce spotkania", null, false));
				Toast t;
				if (status == 0) {
					t = Toast.makeText(getApplicationContext(),
							"Wiadomosc zosta³a wys³ana poprawnie",
							Toast.LENGTH_SHORT);

				} else {
					t = Toast.makeText(getApplicationContext(),
							"Wiadomosc NIE zosta³a wys³ana poprawnie",
							Toast.LENGTH_SHORT);
				}
				t.show();
			}
		});

		wiadomosc3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				IMessage wiadomosc = (IMessage) new Message();
				Integer status = Integer.parseInt(wiadomosc.sendMsg(
						"412fg68kw378", GlobalSettings.getInstance().getMail(),
						null, "Poczekajcie na mnie", null, false));
				Toast t;
				if (status == 0) {
					t = Toast.makeText(getApplicationContext(),
							"Wiadomosc zosta³a wys³ana poprawnie",
							Toast.LENGTH_SHORT);

				} else {
					t = Toast.makeText(getApplicationContext(),
							"Wiadomosc NIE zosta³a wys³ana poprawnie",
							Toast.LENGTH_SHORT);
				}
				t.show();
			}
		});
	}
}
