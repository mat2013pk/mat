package com.java.mat;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class MainActivity extends Activity {

    private final int secondsDelayed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        finish();
                }
        }, secondsDelayed * 1000);
    }

}
