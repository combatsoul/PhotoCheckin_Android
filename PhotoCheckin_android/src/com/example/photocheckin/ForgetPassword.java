package com.example.photocheckin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.photocheckin.Register.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends Activity implements View.OnClickListener {
	
	private EditText email;
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
	
	private ProgressDialog pDialog;
	String response = "";
	
	class forgetpass extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ForgetPassword.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://www.checkinphoto.com/android/forgetpass/chkForget.php");
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("textEmail", email.getText().toString()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse execute = httpclient.execute(httppost);
				InputStream content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}
				String value = response.substring(response.length()-1);
				if(value.equals("0")){
					Intent call_index_wallpage = new Intent(ForgetPassword.this, resultInvalidForgetPassword.class);
					startActivity(call_index_wallpage);
				}else{
					Intent call_index_wallpage = new Intent(ForgetPassword.this, resultForgetPassword.class);
					startActivity(call_index_wallpage);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			return null;
		}

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
				new forgetpass().execute();
			}
			break;

		}
	}
}
