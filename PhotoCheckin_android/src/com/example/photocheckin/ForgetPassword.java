package com.example.photocheckin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends Activity implements View.OnClickListener {
	
	private EditText email;
	private EditText address, subject, emailbody, et_email;
	private String strEmail;
	
	//Set E-mail syntax  
	private String checkE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_forgetpassword);
		
		email = (EditText) findViewById(R.id.email_texf);

		// call btn 'ตกลง'
		Button call_login = (Button) findViewById(R.id.ok_btn);
		call_login.setOnClickListener(this);


	}
	
	//Check validate of Email
	public boolean btnValidateEmail(View v){
		boolean value = true;
			try{
				//Get value converted to a string
				strEmail = email.getText().toString().trim();
				
		    	//เช็คว่า syntax ของ E-mail กับ  E-mail ที่ User กรอกมา นั้นตรงกันหรือไม่ เก็บค่าไว้ใน matcherObj>>>True or False
		        Matcher matcherObj = Pattern.compile(checkE).matcher(strEmail);
		        
		        //เช็คว่าเป็นค่าว่างหรือเปล่า?
		        if(strEmail.isEmpty()){
		        	Toast.makeText(v.getContext(), "Email must not empty", Toast.LENGTH_SHORT).show();
		        	value = false;
		        }else if(!matcherObj.matches()){
		 	        Toast.makeText(v.getContext(), "Email "+strEmail+" is Invalid", Toast.LENGTH_SHORT).show();
		 	        value = false;
		        }
		        
			}
			
	     	catch (NullPointerException ex) {
	     		ex.printStackTrace();	
	        }
		return value;
	
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			if (btnValidateEmail(v)) {
//				checkLogin(v);
				
				Intent call_index_wallpage = new Intent(this, resultForgetPassword.class);
				startActivity(call_index_wallpage);
			}
			break;

		}
	}
	
    public void sendEmail(){


        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"testmail@testmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailbody.getText());
        ForgetPassword.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

}
}
