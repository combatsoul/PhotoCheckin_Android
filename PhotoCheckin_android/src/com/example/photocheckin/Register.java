package com.example.photocheckin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends Activity implements View.OnClickListener{
	
	// value for register
	private EditText name;
	private EditText username;
	private EditText pass;
	private EditText email;
	private EditText confirmpass;
	//private EditText registerdate;
	
	String strName;
	String strUsername;
	String strPass;
	String strConfirmPass;
	String strEmail;
	//String srrRegisterdate;
	
	//Set E-mail syntax  
	private String checkE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_register);

		// edit text for register
		name = (EditText) findViewById(R.id.name_texf);
		username = (EditText) findViewById(R.id.username_texf);
		pass = (EditText) findViewById(R.id.password_texf);
		email = (EditText) findViewById(R.id.email_texf);
		confirmpass = (EditText) findViewById(R.id.confirm_password_texf);
		//registerdate = (EditText) findViewById(R.id.registerdate);

		
		// call btn login into onClick()
		Button call_regis = (Button) findViewById(R.id.register_btn);
		call_regis.setOnClickListener(this);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro_main, menu);
		return true;
	}

	private ProgressDialog pDialog;
	String response = "";

	class register extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
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
			HttpPost httppost = new HttpPost("http://www.checkinphoto.com/android/register/chkRegister.php");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("R_name", name.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_username",username.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_password", pass.getText().toString()));
			//	nameValuePairs.add(new BasicNameValuePair("R_registerDate", registerdate.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_email", email.getText().toString()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse execute = httpclient.execute(httppost);
				InputStream content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}
				Log.d("response", response);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

					// name.setText(response);
					Toast.makeText(Register.this, "register complete",
							Toast.LENGTH_SHORT).show();
				}
			});

		}

	}

	//check validate
	//Check validate of Name
	public boolean btnValidateName(View v){
		boolean value = true;
			try{
				//Get value converted to a string
				strName = name.getText().toString().trim();
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
				strUsername = username.getText().toString().trim();
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
				strPass = pass.getText().toString().trim();
				strConfirmPass = confirmpass.getText().toString().trim();
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
				strEmail = email.getText().toString().trim();
				
		    	//����� syntax �ͧ E-mail �Ѻ  E-mail ��� User ��͡�� ��鹵ç�ѹ������� �纤������ matcherObj>>>True or False
		        Matcher matcherObj = Pattern.compile(checkE).matcher(strEmail);
		        
		        //������繤����ҧ��������?
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
				new register().execute();
				Intent call_index_wallpage = new Intent(this, chkRegister.class);
				startActivity(call_index_wallpage);
			}
			break;
		}

	}
	
	
}
