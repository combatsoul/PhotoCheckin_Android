package com.example.photocheckin;

import com.example.imgaeloader.ImageLoader;

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
		
		//call
		getDataProfile();
	}

	
	@Override
	public void onClick(View v) {
			
	}
	
	public void getDataProfile(){

		String getName = getIntent().getStringExtra("Name_Profile");
		String getImage = getIntent().getStringExtra("Image_Profile");
		//ImageView getImage = (ImageView).getStringExtra("Image_Profile");
		
 		
		//name
		TextView varName = (TextView)findViewById(R.id.profilename_text);
		varName.setText(getName);
		
		ImageView varImage = (ImageView)findViewById(R.id.PictureProfile);
		varImage.setTag(getName);
		
		 
	}
	
 
	
}
