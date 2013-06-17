package com.java.mat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;

public class FaqActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_faq);
		
		TextView foo = (TextView) findViewById(R.id.faq_textView);
		foo.setText(Html.fromHtml(getString(R.string.nice_faq)));
		foo.setMovementMethod(new ScrollingMovementMethod());
	}
}
