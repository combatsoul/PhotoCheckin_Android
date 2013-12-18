package com.example.photocheckin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends Activity implements View.OnClickListener {

	// value for register
	private EditText name;
	private EditText username;
	private EditText pass;
	private EditText email;
	private EditText confirmpass;

	String strName;
	String strUsername;
	String strPass;
	String strConfirmPass;
	String strEmail;
 

	// Set E-mail syntax
	private String checkE = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	private ProgressDialog pDialog;
	String response = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_register);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// edit text for register
		name = (EditText) findViewById(R.id.name_texf);
		username = (EditText) findViewById(R.id.username_texf);
		pass = (EditText) findViewById(R.id.password_texf);
		email = (EditText) findViewById(R.id.email_texf);
		confirmpass = (EditText) findViewById(R.id.confirm_password_texf);

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

	class register extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Register.this);
			pDialog.setTitle("Connect to Server..");
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			// Building Parameters
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.checkinphoto.com/android/register/chkRegister.php");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("R_name", name.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_username",username.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_password", pass.getText().toString()));
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
			} catch (IOException e) {
			}
			return null;
		}

		 
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			// pDialog.dismiss();
			// updating UI from Background Thread
			// runOnUiThread(new Runnable() {
			// public void run() {
			// // name.setText(response);
			// Toast.makeText(Register.this, "register complete",
			// Toast.LENGTH_SHORT).show();
			//
			// }
				
			Handler myHandler = new Handler();
			myHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
					Intent goMain = new Intent(getApplicationContext(),
							chkRegister.class);
					startActivity(goMain);
					Toast.makeText(Register.this, "register complete",
							Toast.LENGTH_SHORT).show();
				}
			}, 1000);

		}
	}

	
	// Check validate of Name ---
	public boolean btnValidateName(View v) {
		boolean value = true;
		try {
			// Get value converted to a string
			strName = name.getText().toString().trim();
			// Empty value checking
			if (strName.isEmpty()) {
				Toast.makeText(v.getContext(), "Your Name must not empty",Toast.LENGTH_SHORT).show();
				value = false;
			}
		}
		catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return value;
	}

	// Check validate of UserName ---
	public boolean btnValidateUserName(View v) {
		boolean value = true;
 
		try {
			// Get value converted to a string
			strUsername = username.getText().toString().trim();
			if (strUsername.isEmpty()) {
				Toast.makeText(v.getContext(), "Your Username must not empty",Toast.LENGTH_SHORT).show();
				value = false;
			}
			else if(strUsername.length()<4){
				Toast.makeText(v.getContext(), "Your Username must more 4 character",Toast.LENGTH_SHORT).show();
				value = false;
			}
			else if(strUsername.matches(".*[^a-z^0-9].*")) {
				Toast.makeText(v.getContext(), "Your Username  is Invalid",Toast.LENGTH_SHORT).show();
				value = false;
			}
		}

		catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return value;

	}

	// Check validate of Password and Confirm Password
	public boolean btnValidatePassAndConfirmPass(View v) {
		boolean value = true;
		try {
			// Get value converted to a string
			strPass = pass.getText().toString().trim();
			strConfirmPass = confirmpass.getText().toString().trim();
			// Check that Password And Confirm password is equal or not
			Matcher matcherObj = Pattern.compile(strPass).matcher(strConfirmPass);

			// Empty value checking
			if (strPass.isEmpty()) {
				Toast.makeText(v.getContext(), "Password must not empty",Toast.LENGTH_SHORT).show();
				value = false;	
			} 
			else if(strPass.length()<4){
				Toast.makeText(v.getContext(), "Password must must more 4 character",Toast.LENGTH_SHORT).show();
				value = false;
			}
			else if (strConfirmPass.isEmpty()) {
				Toast.makeText(v.getContext(),
						"ConfirmPassword must not empty", Toast.LENGTH_SHORT)
						.show();
				value = false;
			} else if (!matcherObj.matches()) {
				Toast.makeText(v.getContext(), "Password is InValid",
						Toast.LENGTH_SHORT).show();
				value = false;
			}}

		catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return value;

	}

	// Check validate of Email
	public boolean btnValidateEmail(View v) {
		boolean value = true;
		try {
			// Get value converted to a string
			strEmail = email.getText().toString().trim();

			// àªç¤ÇèÒ syntax ¢Í§ E-mail ¡Ñº E-mail ·Õè User ¡ÃÍ¡ÁÒ
			// ¹Ñé¹µÃ§¡Ñ¹ËÃ×ÍäÁè à¡çº¤èÒäÇéã¹ matcherObj>>>True or False
			Matcher matcherObj = Pattern.compile(checkE).matcher(strEmail);

			// àªç¤ÇèÒà»ç¹¤èÒÇèÒ§ËÃ×Íà»ÅèÒ?
			if (strEmail.isEmpty()) {
				Toast.makeText(v.getContext(), "Email must not empty",
						Toast.LENGTH_SHORT).show();
				value = false;
			}
			if (!matcherObj.matches()) {
				Toast.makeText(v.getContext(),
						"Email " + strEmail + " is Invalid", Toast.LENGTH_SHORT)
						.show();
				value = false;
			}
		}

		catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return value;

	}

	// /2. start
	// check form server

	public String postData(String username, String email) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://www.checkinphoto.com/android/register/chkusername.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs
					.add(new BasicNameValuePair("textUsername", username));
			nameValuePairs.add(new BasicNameValuePair("textEmail", email));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			InputStream is = response.getEntity().getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);

			int current = 0;

			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			String text = new String(baf.toByteArray());

			return text;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	// end
 
 
	@Override
	public void onClick(View v) {
 
		
		switch (v.getId()) {
		case R.id.register_btn:
			
			if (btnValidateName(v) && btnValidateUserName(v)
					&& btnValidatePassAndConfirmPass(v) && btnValidateEmail(v)) {

				if (postData(username.getText().toString(),
						email.getText().toString()).trim().equals("Username or Email can not available!")) {
					// this username or email have on system
					Toast.makeText(
							getApplicationContext(),postData(username.getText().toString(), email.getText().toString()), Toast.LENGTH_SHORT).show();
				}
				else {
					// ไม่ซ้ำ
					new register().execute();
					//Intent result_register = new Intent(this, chkRegister.class);
					//startActivity(result_register);
				}
			}
				
			break;
		}

	}
}