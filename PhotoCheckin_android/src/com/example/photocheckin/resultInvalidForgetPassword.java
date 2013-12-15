package com.example.photocheckin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class resultInvalidForgetPassword extends Activity implements View.OnClickListener {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.result_invalidforgetpassword);
			
			// call btn 'Login'
			Button call_login = (Button) findViewById(R.id.tryEmail_btn);
			call_login.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tryEmail_btn:				
					Intent call_index_wallpage = new Intent(this, ForgetPassword.class);
					startActivity(call_index_wallpage);
				break;

			}
		}

}
