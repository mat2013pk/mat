package com.java.mat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;

public class AutorsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_autorzy);

		TextView foo = (TextView) findViewById(R.id.autorzy_textView);
		foo.setText(Html.fromHtml(getString(R.string.nice_autors)));
		foo.setMovementMethod(new ScrollingMovementMethod());
	}
}
