package com.example.photocheckin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class resultForgetPassword extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_forgetpassword);
		
		//call btn forgetpassword
		Button call_forget = (Button) findViewById(R.id.login_btn);
        call_forget.setOnClickListener(this); 

	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.login_btn:				
//				Intent call_index_wallpage = new Intent(this, LoginForm.class);
//				startActivity(call_index_wallpage);
//			break;
//
//		}
		
		//edit
		int id = v.getId();
		if(id == R.id.login_btn){
			Intent call_index_wallpage = new Intent(this, LoginForm.class);
			startActivity(call_index_wallpage);
		}
		
		
		
	}
}
