//package com.example.photocheckin;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.zip.Inflater;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//
//import com.example.photocheckin.DateTimePicker.DateWatcher;
////import com.example.photocheckin.DateTimePicker.DateWatcher;
////import com.example.photocheckin.DateTimePicker.TimeWatcher;
//import com.example.photocheckin.LoginForm.login;
//import com.example.photocheckin.Register.register;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.PopupMenu;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.RelativeLayout; 
//
//public class WallPage_backup extends Activity implements View.OnClickListener,DateWatcher {
//
////	private ImageView ClosePopup; 
////	private Button btnCreate;
////	private PopupWindow pwindo;
////	private ImageView calendarStart;
////	private ImageView calendarEnd;
////	private ProgressDialog pDialog;
////	private String response = "";
////	
////	private EditText activityname;
////	private EditText activitydetail;
////	private EditText location;
////	private EditText startcalendar;
////	private EditText endcalendar;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.index_wallpage);
//		
////		// call btn Create activity
////		Button call_create = (Button) findViewById(R.id.createActivity_btn);
////		call_create.setOnClickListener(this);
////		
////		// call btn Google Map
////		Button call_google = (Button) findViewById(R.id.google_btn);
////		call_google.setOnClickListener(this);
////		
////		// call btn Google Map
////		Button call_calendar = (Button) findViewById(R.id.calendar_btn);
////		call_calendar.setOnClickListener(this);
//		
////		// edit text for register
////		activityname = (EditText) pwindo.getContentView().findViewById(R.id.activityname_texf);
////		activitydetail = (EditText) pwindo.getContentView().findViewById(R.id.activitydetail_texa);
////		location = (EditText) pwindo.getContentView().findViewById(R.id.location_texf);
////		startcalendar = (EditText) pwindo.getContentView().findViewById(R.id.calendar1_texf);
////		endcalendar = (EditText) pwindo.getContentView().findViewById(R.id.calendar2_texf);
//	
//	}
//	
////	class createactivity extends AsyncTask<String, String, String> {
////
////		@Override
////		protected void onPreExecute() {
////			super.onPreExecute();
////			pDialog = new ProgressDialog(WallPage.this);
////			pDialog.setTitle("Connect to Server..");
////			pDialog.setMessage("Loading ...");
////			pDialog.setIndeterminate(false);
////			pDialog.setCancelable(false);
////			pDialog.show();
////		}
//
////		protected String doInBackground(String... args) {
////			// Building Parameters
////			HttpClient httpclient = new DefaultHttpClient();
////			HttpPost httppost = new HttpPost(
////					"http://www.checkinphoto.com/android/createactivity/chkCreate.php");
////
////			try {
////				// Add your data
////				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
////				nameValuePairs.add(new BasicNameValuePair("activityname_texf", activityname.getText().toString()));
////				nameValuePairs.add(new BasicNameValuePair("activitydetail_texa", activitydetail.getText().toString()));
////				nameValuePairs.add(new BasicNameValuePair("location_texf", location.getText().toString()));
////				nameValuePairs.add(new BasicNameValuePair("calendar1_texf", startcalendar.getText().toString()));
////				nameValuePairs.add(new BasicNameValuePair("calendar2_texf", endcalendar.getText().toString()));
////				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
////
////				// Execute HTTP Post Request
////				HttpResponse execute = httpclient.execute(httppost);
////				InputStream content = execute.getEntity().getContent();
////				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
////				
////				String s = "";
////				while ((s = buffer.readLine()) != null) {
////					response += s;
////				}
////				Log.d("response", response);
////			} catch (ClientProtocolException e) {
////			} catch (IOException e) {
////			}
////			return null;
//		//}
//
//		 
////		protected void onPostExecute(String file_url) {
////
////			Handler myHandler = new Handler();
////			myHandler.postDelayed(new Runnable() {
////				@Override
////				public void run() {
////					finish();
////					Toast.makeText(WallPage.this, "Create activity complete",
////							Toast.LENGTH_SHORT).show();
////				}
////			}, 1000);
////
////
////		}
////	}
//	
////	private OnClickListener cancel_button_click_listener = new OnClickListener() { 
////		public void onClick(View v) { 
////		pwindo.dismiss();
////
////		} 
////		};
//
////	private	OnClickListener create_button_click_listener = new OnClickListener(){
////
////		@Override
////		public void onClick(View v) {
////			new createactivity().execute();
////			pwindo.dismiss();
////			
////		}
////		
////	};
//	
////	//pop up DateTimepicker for Start date
////	private OnClickListener showStartdatePicker = new OnClickListener() {
////		@Override
////		public void onClick(View v) {
////
////		      // Create the dialog
////			final Dialog mDateTimeDialog = new Dialog(WallPage.this);
////	        // Inflate the root layout
////	        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.datetimepicker, null);
////	        // Grab widget instance
////	        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
////	        mDateTimePicker.setDateChangedListener(WallPage.this);
////	                 
////	        // Update demo edittext when the "OK" button is clicked
////	        ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {
////			public void onClick(View v) {
////				try{
////					mDateTimePicker.clearFocus();
////					//Check month from character to number 
////					String month = "";
////					if(mDateTimePicker.getMonth().equals("Jan")){
////						month = "01";
////					}else if(mDateTimePicker.getMonth().equals("Feb")){
////						month = "02";
////					}else if (mDateTimePicker.getMonth().equals("Mar")) {
////						month = "03";
////					}else if (mDateTimePicker.getMonth().equals("Apr")) {
////						month = "04";
////					}else if(mDateTimePicker.getMonth().equals("May")){
////						month = "05";
////					}else if (mDateTimePicker.getMonth().equals("Jun")) {
////						month = "06";
////					}else if (mDateTimePicker.getMonth().equals("Jul")) {
////						month = "07";
////					}else if(mDateTimePicker.getMonth().equals("Aug")){
////						month = "08";
////					}else if (mDateTimePicker.getMonth().equals("Sep")) {
////						month = "09";
////					}else if (mDateTimePicker.getMonth().equals("Oct")) {
////						month = "10";
////					}else if (mDateTimePicker.getMonth().equals("Nov")) {
////						month = "11";
////					}else if (mDateTimePicker.getMonth().equals("Dec")) {
////						month = "12";
////					}
////					
////					//get hour to 2 position ex. 01,02,03 not 1,2,3
////					String hour = "";
////					if(String.valueOf(mDateTimePicker.getHour()).length()==1){
////						hour = "0"+String.valueOf(mDateTimePicker.getHour());
////					}else{
////						hour = String.valueOf(mDateTimePicker.getHour());
////					}
////					
////					//get minute to 2 position ex. 01,02,03 not 1,2,3
////					String minute = "";
////					if(String.valueOf(mDateTimePicker.getMinute()).length()==1){
////						minute = "0"+String.valueOf(mDateTimePicker.getMinute());
////					}else{
////						minute = String.valueOf(mDateTimePicker.getMinute());
////					}
////					
////	               String result_string = String.valueOf(mDateTimePicker.getDay()) + "-" + month + "-" + String.valueOf(mDateTimePicker.getYear())
////	                                                + "  " + hour + ":" + minute +":00";
////	               System.out.print("Result2"+result_string);
////					
////					EditText text = (EditText) pwindo.getContentView().findViewById(R.id.calendar1_texf);
////					text.setText(result_string);
////					//((EditText)findViewById(R.id.calendar1_texf)).setText("123453");
////					mDateTimeDialog.dismiss();
////				}catch(Exception e){
////					Log.i("Log", e.getMessage()+"Error!");
////				}
////	         }
////	         });
////	        
////	        
////
////	         // Cancel the dialog when the "Cancel" button is clicked
////	         ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {
////	                public void onClick(View v) {
////	                     // TODO Auto-generated method stub
////	                     mDateTimeDialog.cancel();
////	                }
////	         });
////
////	                // Reset Date and Time pickers when the "Reset" button is clicked
////	       
////	         ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {
////
////	                public void onClick(View v) {
////	                      // TODO Auto-generated method stub
////	                      mDateTimePicker.reset();
////	                }
////	         });
////	                 
////	        // Setup TimePicker
////	        // No title on the dialog window
////	        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////	        // Set the dialog content view
////	        mDateTimeDialog.setContentView(mDateTimeDialogView);
////	        // Display the dialog
////	        mDateTimeDialog.show(); 
////		}
////	};
//	
///
////	 private void showCreateActivity(View v){
////		 // We need to get the instance of the LayoutInflater 
////		 LayoutInflater inflater = (LayoutInflater) WallPage.this 
////		 .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
////		 View layout = inflater.inflate(R.layout.index_createactivity,(ViewGroup)
////
////		 findViewById(R.id.linearMenu)); 
////		 pwindo = new PopupWindow(layout, 450, 600, true); 
////		 pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
////		 
////
////
////		 ClosePopup = (ImageView) layout.findViewById(R.id.imageCross); 
////		 ClosePopup.setOnClickListener(cancel_button_click_listener);
////		 
////		 btnCreate = (Button) layout.findViewById(R.id.btn_create_popup); 
////		 btnCreate.setOnClickListener(create_button_click_listener);
////		 
////		 calendarStart = (ImageView) layout.findViewById(R.id.imagecalendar1);
////		 calendarStart.setOnClickListener(showStartdatePicker);
////		 
////		 calendarEnd = (ImageView) layout.findViewById(R.id.imagecalendar2);
////		 calendarEnd.setOnClickListener(showEnddatePicker);
////		 }
////	 
////	 private void showCalendar(View v){
////		 // We need to get the instance of the LayoutInflater 
////		 LayoutInflater inflater = (LayoutInflater) WallPage.this 
////		 .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
////		 View layout = inflater.inflate(R.layout.datetime_selection,(ViewGroup)
////
////		 findViewById(R.id.DateTimePicker)); 
////		 pwindo = new PopupWindow(layout, 450, 600, true); 
////		 pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
////		 }
//
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.createActivity_btn:
////			showCreateActivity(v);
////			break;
////			
////		case R.id.google_btn:
////			Intent call_google = new Intent(this, GoogleMaps.class);
////			startActivity(call_google);
////			break;
////			
////		case R.id.calendar_btn:
////			showCalendar(v);
////			break;
////		}
////
////	}
//
////	@Override
////	public void onDateChanged(Calendar c) {
////		// TODO Auto-generated method stub
////		
////	}
//
////	@Override
////	public void onTimeChanged(int h, int m, int am_pm) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void onDateChanged(Calendar c) {
////		// TODO Auto-generated method stub
////		
////	}
//	
//
//}
