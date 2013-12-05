package com.example.photocheckin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class Register extends Activity implements View.OnClickListener {

	EditText name_texf;
	EditText username_texf;	
	EditText password_texf;
	EditText confirm_password_texf;
	EditText email_texf;
	String strName;
	String strUsername;
	String strPass;
	String strConfirmPass;
	String strEmail;
	
	//Set E-mail syntax  
	String checkE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_register);
		
		// call btn login
		Button call_regis = (Button) findViewById(R.id.register_btn);
		call_regis.setOnClickListener(this);

		
        //Get the value that the user entered in each channel to store in the variable
		name_texf = (EditText) findViewById(R.id.name_texf);
        username_texf = (EditText) findViewById(R.id.username_texf);
        password_texf = (EditText) findViewById(R.id.password_texf);
        confirm_password_texf = (EditText) findViewById(R.id.confirm_password_texf);
		email_texf = (EditText) findViewById(R.id.email_texf);
	}
	
	//Check validate of Name
	public boolean btnValidateName(View v){
		boolean value = true;
			try{
				//Get value converted to a string
				strName = name_texf.getText().toString().trim();
					//Empty value checking 
			        if(strName.isEmpty()){
			        	Toast.makeText(v.getContext(),"Your Name must not empty", Toast.LENGTH_SHORT).show();
			        	value = false;
			        }
		        }
			
	     	catch (NullPointerException ex) {
	     		ex.printStackTrace();	
	        }
		return value;
	
	}
	
	
	//Check validate of UserName
	public boolean btnValidateUserName(View v){
		boolean value = true;
			try{
				//Get value converted to a string
				strUsername = username_texf.getText().toString().trim();
					//Empty value checking
			        if(strUsername.isEmpty()){
			        	Toast.makeText(v.getContext(),"Your Username must not empty", Toast.LENGTH_SHORT).show();
			        	value = false;
			        }
		        }
			
	     	catch (NullPointerException ex) {
	     		ex.printStackTrace();	
	        }
		return value;
	
	}

	//Check validate of Password and Confirm Password
	public boolean btnValidatePassAndConfirmPass(View v){
		boolean value = true;
			try{
				//Get value converted to a string
				strPass = password_texf.getText().toString().trim();
				strConfirmPass = confirm_password_texf.getText().toString().trim();
					

			
			    	//Check that Password And Confirm password is equal or not
			        Matcher matcherObj = Pattern.compile(strPass).matcher(strConfirmPass);
			        
			        //Empty value checking
			        if(strPass.isEmpty()){
			        	Toast.makeText(v.getContext(), "Password must not empty", Toast.LENGTH_SHORT).show();
			        	value = false;
			        }else if(strConfirmPass.isEmpty()){
			        	Toast.makeText(v.getContext(), "ConfirmPassword must not empty", Toast.LENGTH_SHORT).show();
			        	value = false;
			        }else if(!matcherObj.matches()){
			            Toast.makeText(v.getContext(), "Password is InValid", Toast.LENGTH_SHORT).show();
			            value = false;
			        }
		        }
			
	     	catch (NullPointerException ex) {
	     		ex.printStackTrace();	
	        }
		return value;
	
	}
	
	//Check validate of Email
	public boolean btnValidateEmail(View v){
		boolean value = true;
			try{
				//Get value converted to a string
				strEmail = email_texf.getText().toString().trim();
				
		    	//เช็คว่า syntax ของ E-mail กับ  E-mail ที่ User กรอกมา นั้นตรงกันหรือไม่ เก็บค่าไว้ใน matcherObj>>>True or False
		        Matcher matcherObj = Pattern.compile(checkE).matcher(strEmail);
		        
		        //เช็คว่าเป็นค่าว่างหรือเปล่า?
		        if(strEmail.isEmpty()){
		        	Toast.makeText(v.getContext(), "Email must not empty", Toast.LENGTH_SHORT).show();
		        	value = false;
		        }if(!matcherObj.matches()){
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
		case R.id.register_btn:
			if(btnValidateName(v) && btnValidateUserName(v) && btnValidatePassAndConfirmPass(v) && btnValidateEmail(v)){
				Intent call_index_wallpage = new Intent(this, WallPage.class);
				startActivity(call_index_wallpage);
			}
			break;
//		case R.id.imgbtn_register:
//			Intent call_registerbtn = new Intent(this, Register.class);
//			startActivity(call_registerbtn);
//			break;

		}

	}
}
