package com.example.listview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photocheckin.DateTimePicker;
import com.example.photocheckin.DateTimePicker.DateWatcher;
import com.example.photocheckin.R;
import com.example.photocheckin.http.HttpPhotoCheckIn;
import com.google.android.gms.internal.ar;


public class WallPage extends Activity implements View.OnClickListener,DateWatcher {
	
	private String TAG = "WallPage";
	private ListView listview;
	private LazyAdapter adapter;
		
	//set variable createactivity
	private ImageView ClosePopup; 
	private Button btnCreate;
	private PopupWindow pwindo;
	private ImageView calendarStart;
	private ImageView calendarEnd;
	private ProgressDialog pDialog;
	private String response = "";
	private LinearLayout showDialogView;
	
	private EditText activityname;
	private EditText activitydetail;
	private EditText location;
	private EditText startcalendar;
	private EditText endcalendar;
	
	ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hashMap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_wallpage);
		
		Log.d(TAG, "onCreate");
		
//		 edit text for register
		
		listview = (ListView) findViewById(android.R.id.list);
		hashMap = new HashMap<String, String>();
		new GetDataTask().execute();
		  
			String cn = "check-in";
			String ca = "create activity";
			String pf = "Profile";
			
		  // spinner1 -- 
		  final List<String> arrList = new ArrayList<String>();
		  Spinner spin = (Spinner) findViewById(R.id.spinner1);
		  arrList.add(cn); 
		  arrList.add(ca);
		  arrList.add(pf); 
		  
		 

		  
		  

		  
		  ArrayAdapter<String> arrAd = new ArrayAdapter<String>( 
		  WallPage.this,android.R.layout.simple_spinner_item, arrList);
		  spin.setAdapter(arrAd); spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		  {
			  
		//1
		class createactivity extends AsyncTask<String, String, String> {
			@Override
			protected void onPreExecute() {
						super.onPreExecute();
						pDialog = new ProgressDialog(WallPage.this);
						pDialog.setTitle("Connect to Server..");
						pDialog.setMessage("Loading ...");
						pDialog.setIndeterminate(false);
						pDialog.setCancelable(false);
						pDialog.show();
			}
					
		//2
		protected String doInBackground(String... args) {
						// Building Parameters
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://www.checkinphoto.com/android/createactivity/chkCreate.php");

						try {
							activityname = (EditText) showDialogView.findViewById(R.id.activityname_texf);
							activitydetail = (EditText) showDialogView.findViewById(R.id.activitydetail_texa);
							location = (EditText) showDialogView.findViewById(R.id.location_texf);
							startcalendar = (EditText) showDialogView.findViewById(R.id.calendar1_texf);
							endcalendar = (EditText) showDialogView.findViewById(R.id.calendar2_texf);
							// Add your data
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
							nameValuePairs.add(new BasicNameValuePair("activityname_texf", activityname.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("activitydetail_texa", activitydetail.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("location_texf", location.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("calendar1_texf", startcalendar.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("calendar2_texf", endcalendar.getText().toString()));
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
		
		//3
		protected void onPostExecute(String file_url) {
			Handler myHandler = new Handler();
			myHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
					Toast.makeText(WallPage.this, "Create activity complete",
							Toast.LENGTH_SHORT).show();
				}
			}, 1000);		}
	}
		//4
		private OnClickListener cancel_button_click_listener = new OnClickListener() { 
			public void onClick(View v) { 
			pwindo.dismiss();
			} 
			};
			
			private	OnClickListener create_button_click_listener = new OnClickListener(){
				@Override
				public void onClick(View v) {
					new createactivity().execute();
					pwindo.dismiss();
					
				}
			};	
			
			
			//pop up DateTimepicker for Start date
			private OnClickListener showStartdatePicker = new OnClickListener() {
				@Override
				public void onClick(View v) {

				      // Create the dialog
					final Dialog mDateTimeDialog = new Dialog(WallPage.this);
			        // Inflate the root layout
			        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.datetimepicker, null);
			        
			        final LinearLayout createDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.index_createactivity, null);
			        // Grab widget instance
			        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
			        mDateTimePicker.setDateChangedListener(WallPage.this);
			                 
			        // Update demo edittext when the "OK" button is clicked
			        ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						try{
							mDateTimePicker.clearFocus();
							//Check month from character to number 
							String month = "";
							if(mDateTimePicker.getMonth().equals("Jan")){
								month = "01";
							}else if(mDateTimePicker.getMonth().equals("Feb")){
								month = "02";
							}else if (mDateTimePicker.getMonth().equals("Mar")) {
								month = "03";
							}else if (mDateTimePicker.getMonth().equals("Apr")) {
								month = "04";
							}else if(mDateTimePicker.getMonth().equals("May")){
								month = "05";
							}else if (mDateTimePicker.getMonth().equals("Jun")) {
								month = "06";
							}else if (mDateTimePicker.getMonth().equals("Jul")) {
								month = "07";
							}else if(mDateTimePicker.getMonth().equals("Aug")){
								month = "08";
							}else if (mDateTimePicker.getMonth().equals("Sep")) {
								month = "09";
							}else if (mDateTimePicker.getMonth().equals("Oct")) {
								month = "10";
							}else if (mDateTimePicker.getMonth().equals("Nov")) {
								month = "11";
							}else if (mDateTimePicker.getMonth().equals("Dec")) {
								month = "12";
							}
							
							//get hour to 2 position ex. 01,02,03 not 1,2,3
							String hour = "";
							if(String.valueOf(mDateTimePicker.getHour()).length()==1){
								hour = "0"+String.valueOf(mDateTimePicker.getHour());
							}else{
								hour = String.valueOf(mDateTimePicker.getHour());
							}
							
							//get minute to 2 position ex. 01,02,03 not 1,2,3
							String minute = "";
							if(String.valueOf(mDateTimePicker.getMinute()).length()==1){
								minute = "0"+String.valueOf(mDateTimePicker.getMinute());
							}else{
								minute = String.valueOf(mDateTimePicker.getMinute());
							}
							
			               String result_string = String.valueOf(mDateTimePicker.getYear()) + "-" + month + "-" + String.valueOf(mDateTimePicker.getDay())
			                                                + "  " + hour + ":" + minute +":00";
			               System.out.print("Result2"+result_string);
							
			               EditText text = (EditText) showDialogView.findViewById(R.id.calendar1_texf);
			               text.setText(result_string);
			               //((EditText)findViewById(R.id.calendar1_texf)).setText("123453");
			               mDateTimeDialog.cancel();
						}catch(Exception e){
							Log.i("Log", e.getMessage()+"Error!");
						}
			         }
			         });
			        
			        

			         // Cancel the dialog when the "Cancel" button is clicked
			         ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {
			                public void onClick(View v) {
			                     // TODO Auto-generated method stub
			                     mDateTimeDialog.cancel();
			                }
			         });

			                // Reset Date and Time pickers when the "Reset" button is clicked
			       
			         ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			                public void onClick(View v) {
			                      // TODO Auto-generated method stub
			                      mDateTimePicker.reset();
			                }
			         });
			                 
			        // Setup TimePicker
			        // No title on the dialog window
			        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			        // Set the dialog content view
			        mDateTimeDialog.setContentView(mDateTimeDialogView);
			        // Display the dialog
			        mDateTimeDialog.show(); 
				}
			};
			
			
			//pop up DateTimepicker for End date
			private OnClickListener showEnddatePicker = new OnClickListener() {
				@Override
				public void onClick(View v) {

				      // Create the dialog
					final Dialog mDateTimeDialog = new Dialog(WallPage.this);
			        // Inflate the root layout
			        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.datetimepicker, null);
			        // Grab widget instance
			        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
			        mDateTimePicker.setDateChangedListener(WallPage.this);
			                 
			        // Update demo edittext when the "OK" button is clicked
			        ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						try{
							mDateTimePicker.clearFocus();
							//Check month from character to number 
							String month = "";
							if(mDateTimePicker.getMonth().equals("Jan")){
								month = "01";
							}else if(mDateTimePicker.getMonth().equals("Feb")){
								month = "02";
							}else if (mDateTimePicker.getMonth().equals("Mar")) {
								month = "03";
							}else if (mDateTimePicker.getMonth().equals("Apr")) {
								month = "04";
							}else if(mDateTimePicker.getMonth().equals("May")){
								month = "05";
							}else if (mDateTimePicker.getMonth().equals("Jun")) {
								month = "06";
							}else if (mDateTimePicker.getMonth().equals("Jul")) {
								month = "07";
							}else if(mDateTimePicker.getMonth().equals("Aug")){
								month = "08";
							}else if (mDateTimePicker.getMonth().equals("Sep")) {
								month = "09";
							}else if (mDateTimePicker.getMonth().equals("Oct")) {
								month = "10";
							}else if (mDateTimePicker.getMonth().equals("Nov")) {
								month = "11";
							}else if (mDateTimePicker.getMonth().equals("Dec")) {
								month = "12";
							}
							
							//get hour to 2 position ex. 01,02,03 not 1,2,3
							String hour = "";
							if(String.valueOf(mDateTimePicker.getHour()).length()==1){
								hour = "0"+String.valueOf(mDateTimePicker.getHour());
							}else{
								hour = String.valueOf(mDateTimePicker.getHour());
							}
							
							//get minute to 2 position ex. 01,02,03 not 1,2,3
							String minute = "";
							if(String.valueOf(mDateTimePicker.getMinute()).length()==1){
								minute = "0"+String.valueOf(mDateTimePicker.getMinute());
							}else{
								minute = String.valueOf(mDateTimePicker.getMinute());
							}
							
							String result_string = String.valueOf(mDateTimePicker.getYear()) + "-" + month + "-" + String.valueOf(mDateTimePicker.getDay())			                                                + "  " + hour + ":" + minute +":00";
			               System.out.print("Result2"+result_string);
							
//							EditText text = (EditText) pwindo.getContentView().findViewById(R.id.calendar2_texf);
			               EditText text = (EditText) showDialogView.findViewById(R.id.calendar2_texf);
			               
							text.setText(result_string);
							//((EditText)findViewById(R.id.calendar1_texf)).setText("123453");
							mDateTimeDialog.cancel();
						}catch(Exception e){
							Log.i("Log", e.getMessage()+"Error!");
						}
			         }
			         });
			        
			        

			         // Cancel the dialog when the "Cancel" button is clicked
			         ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {
			                public void onClick(View v) {
			                     // TODO Auto-generated method stub
			                     mDateTimeDialog.cancel();
			                }
			         });

			                // Reset Date and Time pickers when the "Reset" button is clicked
			       
			         ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			                public void onClick(View v) {
			                      // TODO Auto-generated method stub
			                      mDateTimePicker.reset();
			                }
			         });
			                 
			        // Setup TimePicker
			        // No title on the dialog window
			        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			        // Set the dialog content view
			        mDateTimeDialog.setContentView(mDateTimeDialogView);
			        // Display the dialog
			        mDateTimeDialog.show(); 
				}
			};
			
			
			private void showCreateActivitys(View v){
				final Dialog createDialog = new Dialog(WallPage.this);
		        // Inflate the root layout
		        showDialogView = (LinearLayout) getLayoutInflater().inflate(R.layout.index_createactivity, null);
		        // Setup TimePicker
				 
		        ((ImageView) showDialogView.findViewById(R.id.imageCross)).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							createDialog.cancel();
							
						}
		         });
		         
		        ((Button) showDialogView.findViewById(R.id.btn_create_popup)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						new createactivity().execute();
						createDialog.cancel();
						
					}
				});
		     // generate a 150x150 QR code			        
		         
				 calendarStart = (ImageView) showDialogView.findViewById(R.id.imagecalendar1);
				 calendarStart.setOnClickListener(showStartdatePicker);
				 
				 calendarEnd = (ImageView) showDialogView.findViewById(R.id.imagecalendar2);
				 calendarEnd.setOnClickListener(showEnddatePicker);
		        
		        // No title on the dialog window
				 createDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		        // Set the dialog content view
				 createDialog.setContentView(showDialogView);
		        // Display the dialog
				 createDialog.show(); 
				 }
			
			
//			// call
//			 private void showCreateActivity(View v){
//				 // We need to get the instance of the LayoutInflater 
//				 LayoutInflater inflater = (LayoutInflater) WallPage.this 
//				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
//				 View layout = inflater.inflate(R.layout.index_createactivity,(ViewGroup)
//
//				 findViewById(R.id.linearMenu)); 
//				 pwindo = new PopupWindow(layout, 450, 600, true); 
//				 pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
//				 
//
//
//				 ClosePopup = (ImageView) layout.findViewById(R.id.imageCross); 
//				 ClosePopup.setOnClickListener(cancel_button_click_listener);
//				 
//				 btnCreate = (Button) layout.findViewById(R.id.btn_create_popup); 
//				 btnCreate.setOnClickListener(create_button_click_listener);
//				 
//				 calendarStart = (ImageView) layout.findViewById(R.id.imagecalendar1);
//				 calendarStart.setOnClickListener(showStartdatePicker);
//				 
//				 calendarEnd = (ImageView) layout.findViewById(R.id.imagecalendar2);
//				 calendarEnd.setOnClickListener(showEnddatePicker);
//				 }
//			 
//			 private void showCalendar(View v){
//				 // We need to get the instance of the LayoutInflater 
//				 LayoutInflater inflater = (LayoutInflater) WallPage.this 
//				 .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
//				 View layout = inflater.inflate(R.layout.datetime_selection,(ViewGroup)
//
//				 findViewById(R.id.DateTimePicker)); 
//				 pwindo = new PopupWindow(layout, 450, 600, true); 
//				 pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
//				 }
			 
			 
			 //call popup
		  public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l){ 
			  
				if(arrList.get(i) == "check-in"){
					Toast.makeText(getApplication(), "Check-in",Toast.LENGTH_LONG).show();
				}
				else if(arrList.get(i) == "create activity"){
					Toast.makeText(getApplication(), "Create Activity",Toast.LENGTH_LONG).show();
					 showCreateActivitys(v);
				}
				else if(arrList.get(i) == "Profile"){
					  Toast.makeText(getApplication(), "Profiles",Toast.LENGTH_LONG).show();		
				}
		  }
		  
		  public void onNothingSelected(AdapterView<?> arg0) { } }); 
		  //check internet 
		  if (isConnectInternet()) {
			  Toast.makeText(getApplication(), "Internet Error",
			  Toast.LENGTH_LONG).show(); Log.d(TAG, "Internet Can not.."); 
		  }
		  else{ 
			  Toast.makeText(getApplication(), "connected",Toast.LENGTH_LONG).show(); 
		  }
		  
		  if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy
		  policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
		  StrictMode.setThreadPolicy(policy); }
		  // end
	} 
	
	private boolean isConnectInternet() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	
	//get data
	protected class GetDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpPhotoCheckIn httpPhotoCheckIn = new HttpPhotoCheckIn();
			String result = httpPhotoCheckIn.connect("http://www.checkinphoto.com/android/activity/selectActivity.php");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONArray jsonArray = new JSONArray(result);
				Log.d("JSON Array", jsonArray.toString());
				
				for (int i = 0; i < jsonArray.length(); i++) {
					hashMap = new HashMap<String, String>();
					JSONObject jsonObject = new JSONObject(
					jsonArray.getString(i));
					
					//set string tail
					String textMore = "    ´ÙµèÍ..";		
					
					
					//title
					hashMap.put("title",jsonObject.getString("activityname")); 
					//detail
					int countdetail = jsonObject.getString("activitydetail").length();
					 // if detail >= 120  show button more
					if(countdetail >= 120){ 
						 //set maxtext 120							
						String cuttext= jsonObject.getString("activitydetail");
						StringBuilder strResults = new StringBuilder(cuttext);
						strResults.setLength(120);
						hashMap.put("detail",strResults+textMore);
					 }
					 else{
						 hashMap.put("detail",jsonObject.getString("activitydetail"));
					 }
					//last time
					hashMap.put("time",jsonObject.getString("lasttime"));
					arrayList.add(hashMap);
				}
				SimpleAdapter adapter = new SimpleAdapter(
						WallPage.this, arrayList, R.layout.list_box,
						new String[] {"title", "detail","time" },
						new int[] {R.id.Title, R.id.Detail,R.id.Time});
						listview.setAdapter(adapter);
			} catch (JSONException e) {
				Log.e("ERROR JSON", e.toString());
			}

			super.onPostExecute(result);
		}
	}


	@Override
	public void onDateChanged(Calendar c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
//	
	
	
}