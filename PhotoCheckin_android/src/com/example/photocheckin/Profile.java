package com.example.photocheckin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_profile);
		
		
		Bundle extras = getIntent().getExtras();
		String getName = extras.getString("name");
		//String getPic = extras.getString("pic");
		
		//get value
		//String getName = getIntent().getStringExtra("name");
		//String getPic = getIntent().getStringExtra("pic");
		TextView getvalue = (TextView) findViewById(R.id.profilename_text);
		getvalue.setText(getName);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
				
				
 		
	}

	
}
