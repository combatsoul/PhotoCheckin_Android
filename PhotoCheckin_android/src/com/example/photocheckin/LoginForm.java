package com.example.photocheckin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.listview.WallPage;
import com.example.photocheckin.Register.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginForm extends Activity implements View.OnClickListener {

	String strUsername = "";
	String strPassword = "";
	EditText input_username;
	EditText input_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_loginform);

		// call btn login
		Button call_login = (Button) findViewById(R.id.loginform_btn);
		call_login.setOnClickListener(this);
		
		//call btn forgetpassword
        TextView call_forget = (TextView) findViewById(R.id.forgetpass_btn);
        call_forget.setOnClickListener(this);   

		// input value form textflid
		input_username = (EditText) findViewById(R.id.login_username_texf);
		input_password = (EditText) findViewById(R.id.login_password_texf);

		// go to register
		ImageButton goto_register = (ImageButton) findViewById(R.id.register_btnimg);
		goto_register.setOnClickListener(this);

	}

	// validate --
	private ProgressDialog pDialog;
	String response = "";
	
	class login extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginForm.this);
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
			HttpPost httppost = new HttpPost(
					"http://www.checkinphoto.com/android/login/chkLogin.php");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("textUsername", input_username.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("textPassword",input_password.getText().toString()));
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
				Log.d("value : ", value);
				
				String usertypeRegis = "register";
				String usertypeAdmin = "admin";
				String notblock = "block0";
				String block = "block1";
				String[] sentence = response.split(" ");
				
				for(String word: sentence)
				{
				    if(word.equals(usertypeRegis)){
				    	for(String word2: sentence){
				    		if(word2.equals(notblock)){
				    			if(value.equals("0")){	
									// dismiss the dialog after getting all albums
									pDialog.dismiss();
									// updating UI from Background Thread
									runOnUiThread(new Runnable() {
										public void run() {
											// name.setText(response);
											Toast.makeText(LoginForm.this, "Login fail, please try again",
													Toast.LENGTH_SHORT).show();
										}
									});
										
									// when can't login it will close page loginform last
									finish();
									Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
									startActivity(goMain);
								}else{
									
									// login done
									finish();
									Intent goMain = new Intent(getApplicationContext(),WallPage.class);
									startActivity(goMain);
								}
				    		}
				    		if(word2.equals(block)){
								// dismiss the dialog after getting all albums
								pDialog.dismiss();
								// updating UI from Background Thread
								runOnUiThread(new Runnable() {
									public void run() {
										// name.setText(response);
										Toast.makeText(LoginForm.this, "Login fail, please try again",
												Toast.LENGTH_SHORT).show();
									}
								});
									
								// when can't login it will close page loginform last
								finish();
								Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
								startActivity(goMain);
				    		}
				    	}
				    }else if(word.equals(usertypeAdmin)){
				    	for(String word2: sentence){
				    		if(word2.equals(notblock)){
				    			if(value.equals("0")){	
									// dismiss the dialog after getting all albums
									pDialog.dismiss();
									// updating UI from Background Thread
									runOnUiThread(new Runnable() {
										public void run() {
											// name.setText(response);
											Toast.makeText(LoginForm.this, "Login fail, please try again",
													Toast.LENGTH_SHORT).show();
										}
									});
										
									// when can't login it will close page loginform last
									finish();
									Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
									startActivity(goMain);
								}else{
									
									// login done
									finish();
									Intent goMain = new Intent(getApplicationContext(),WallPage.class);
									startActivity(goMain);
								}
				    		}
				    		if(word2.equals(block)){
								// dismiss the dialog after getting all albums
								pDialog.dismiss();
								// updating UI from Background Thread
								runOnUiThread(new Runnable() {
									public void run() {
										// name.setText(response);
										Toast.makeText(LoginForm.this, "Login fail, please try again",
												Toast.LENGTH_SHORT).show();
									}
								});
									
								// when can't login it will close page loginform last
								finish();
								Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
								startActivity(goMain);
				    		}
				    	}
				    }
//				    else{
//						// dismiss the dialog after getting all albums
//						pDialog.dismiss();
//						// updating UI from Background Thread
//						runOnUiThread(new Runnable() {
//							public void run() {
//								// name.setText(response);
//								Toast.makeText(LoginForm.this, "Login fail, please try again",
//										Toast.LENGTH_SHORT).show();
//							}
//						});
//				    }
				        
				}

//				if(value.equals("0")){	
//					// dismiss the dialog after getting all albums
//					pDialog.dismiss();
//					// updating UI from Background Thread
//					runOnUiThread(new Runnable() {
//						public void run() {
//							// name.setText(response);
//							Toast.makeText(LoginForm.this, "Login fail, please try again",
//									Toast.LENGTH_SHORT).show();
//						}
//					});
//						
//					// when can't login it will close page loginform last
//					finish();
//					Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
//					startActivity(goMain);
//				}
//				else{
//					
//					// login done
//					finish();
//					Intent goMain = new Intent(getApplicationContext(),WallPage.class);
//					startActivity(goMain);
//				}
//				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			return null;
		}
			
		

	}
	
	//check username
	public boolean btnValidateUsername(View v) {
		boolean value = true;
		try {
			// รับค่ามาแปลงให้เป็น String
			strUsername = input_username.getText().toString().trim();
			if (strUsername.isEmpty()) {
				Toast.makeText(v.getContext(), "Your Username must not empty",
						Toast.LENGTH_SHORT).show();
				value = false;
			}
		}
		catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return value;
	}

	public boolean btnValidatePassword(View v) {
		boolean value = true;
		try {
			// รับค่ามาแปลงให้เป็น String
			strPassword = input_password.getText().toString().trim();
			if (strPassword.isEmpty()) {
				Toast.makeText(v.getContext(), "Your Password must not empty",
						Toast.LENGTH_SHORT).show();
				value = false;
			}
		}
		catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return value;
	} 
	
	// check link to page ---
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginform_btn:
			if (btnValidateUsername(v) && btnValidatePassword(v)) {
//				checkLogin(v);
				new login().execute();
//				Intent call_index_wallpage = new Intent(this, WallPage.class);
//				startActivity(call_index_wallpage);
			}
			break;
		case R.id.register_btnimg:
			Intent call_registerbtn = new Intent(this, Register.class);
			startActivity(call_registerbtn);
			break;
			
		 case R.id.forgetpass_btn:
             Intent call_forgetpassword = new Intent(this, ForgetPassword.class);
             startActivity(call_forgetpassword);                        
             break; 
		}
	}

}
