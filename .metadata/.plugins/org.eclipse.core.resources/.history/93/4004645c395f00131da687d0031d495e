package com.example.photocheckin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class chkRegister extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result_register);
	
	    // redirect   
	    new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) { }

	            Intent intent = new Intent(chkRegister.this, LoginForm.class);
	            startActivity(intent);
	        }
	    }).start();
	}

	
}
