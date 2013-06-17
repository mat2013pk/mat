package com.java.mat;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import message.*;

public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wiadomosci);
		GlobalSettings.getInstance().setMessageStatus(false);
		ListView lv = (ListView) findViewById(R.id.wiadomosci_lista);
		
		IMessage m = new Message();
		m.recvMsgAll(GlobalSettings.getInstance().getSecretKey(), GlobalSettings.getInstance().getMail());
		//m.recvMsg(GlobalSettings.getInstance().getSecretKey(), GlobalSettings.getInstance().getMail());
		ArrayList<String> lista_wiadomosci = m.getMsgByTextAll();
		Log.e("Glowna lista", "" + lista_wiadomosci.size());
		ArrayAdapter<String> arrayAdapter =      
		         new ArrayAdapter<String>(MessageActivity.this,android.R.layout.simple_list_item_1, lista_wiadomosci);
		         lv.setAdapter(arrayAdapter);
	}
}
