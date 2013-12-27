package com.example.photocheckin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class DateTimeSelection extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.datetime_selection);		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
