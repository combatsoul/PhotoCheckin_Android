package com.example.photocheckin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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

public class Register extends Activity {

	String NAME;
	String USERNAME;
	// value for register
	private EditText name;
	private EditText username;
	private EditText pass;
	private EditText email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_register);

		// edit text for register
		name = (EditText) findViewById(R.id.name_texf);
		username = (EditText) findViewById(R.id.username_texf);
		pass = (EditText) findViewById(R.id.password_texf);
		email = (EditText) findViewById(R.id.email_texf);

		// submit
		Button submit = (Button) findViewById(R.id.register_btn);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new register().execute();
			}
		});

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
			HttpPost httppost = new HttpPost(
					"http://www.checkinphoto.com/android/register/chkRegister.php");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("R_name", name
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_username",
						username.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_password", pass
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("R_usertype", "2"));
				nameValuePairs.add(new BasicNameValuePair("R_email", email
						.getText().toString()));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse execute = httpclient.execute(httppost);
				InputStream content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
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
					Toast.makeText(Register.this, "register compleat",
							Toast.LENGTH_SHORT).show();
				}
			});

		}

	}

	//check validate
	
	
	
	
	
}
