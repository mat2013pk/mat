package com.java.mat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AuthNotloggedActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_autoryzacja_niezalogowany);
	}
	
	
}
