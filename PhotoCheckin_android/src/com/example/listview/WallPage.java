
package com.example.listview;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
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
import com.example.imgaeloader.ImageLoader;
import com.example.photocheckin.Base64;
import com.example.photocheckin.DateTimePicker;
import com.example.photocheckin.LoginForm;
import com.example.photocheckin.Profile;
//import com.example.photocheckin.Profile;
import com.example.photocheckin.chkRegister;
import com.example.photocheckin.DateTimePicker.DateWatcher;
import com.example.photocheckin.R;
import com.example.photocheckin.http.HttpPhotoCheckIn;
import com.google.android.gms.internal.ar;

public class WallPage extends Activity implements View.OnClickListener,DateWatcher {
	
	private String Name;
	private String TAG = "WallPage";
	private ListView listview;
	private LazyAdapter adapter;  
	private ImageView ClosePopup;
	private Button btnCreate;
	private PopupWindow pwindo;
	private ImageView calendarStart;
	private ImageView calendarEnd;
	private TextView nameofProfile;

	private ProgressDialog pDialog;
	private String response = "";
	private String strName;
	private String strLocation;
	private LinearLayout showDialogView;
	public LinearLayout showDialogProfile;

	private EditText activityname;
	private EditText activitydetail;
	private EditText location;
	private EditText startcalendar;
	private EditText endcalendar;
	private EditText namechk;
	private EditText locationchk;
	public String sentName;
	public String sentImg;
	
	ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hashMap;
	public String name;
	public int pic;
	 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_wallpage); 
 
		//hiding default app icon
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbar));
		
		//displaying custom ActionBar
		View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
		actionBar.setCustomView(mActionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		//get username form login
		String UserNamelogin = getIntent().getStringExtra("username");
		
		// sent to wallpage
		ImageView imageViewProfile = (ImageView) findViewById(R.id.pictureprofile);
		TextView textViewName = (TextView) findViewById(R.id.nameuser_text);
			
		//imageViewProfile2 = (ImageView) findViewById(R.id.PictureProfilePage);
		
		String imageJson = selDataUser("http://www.checkinphoto.com/android/userprofile/seluser.php?username=",UserNamelogin);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(imageJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

				ImageLoader imageLoader = new ImageLoader(jsonObject.getString("pic"),imageViewProfile);//ภาพ
				textViewName.setText(jsonObject.getString("name"));//ชื่อ	
				
				
				//sent go profile
				sentImg = jsonObject.getString("pic");//ภาพ
				
				//name
				sentName = jsonObject.getString("name");
			}
			
		
 
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		listview = (ListView) findViewById(android.R.id.list);
		hashMap = new HashMap<String, String>();
		new GetDataTask().execute();
	
	

	 
		// Create activity --
		class createactivity extends AsyncTask<String, String, String>{
			private HttpResponse httpPost;

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

			// send activity create
			protected String doInBackground(String... args) {
				// Building Parameters
				HttpClient httpclient = new DefaultHttpClient();
				// httpPost.setEntity(new
				// UrlEncodedFormEntity(params,"UTF-8"));

				HttpPost httppost = new HttpPost(
						"http://www.checkinphoto.com/android/createactivity/chkCreate.php");

				try {

					activityname = (EditText) showDialogView
							.findViewById(R.id.activityname_texf);
					activitydetail = (EditText) showDialogView
							.findViewById(R.id.activitydetail_texa);
					location = (EditText) showDialogView
							.findViewById(R.id.location_texf);
					startcalendar = (EditText) showDialogView
							.findViewById(R.id.calendar1_texf);
					endcalendar = (EditText) showDialogView
							.findViewById(R.id.calendar2_texf);

					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);

					nameValuePairs.add(new BasicNameValuePair(
							"activityname_texf", activityname.getText()
									.toString()));
					nameValuePairs.add(new BasicNameValuePair(
							"activitydetail_texa", activitydetail.getText()
									.toString()));
					nameValuePairs.add(new BasicNameValuePair("location_texf",
							location.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("calendar1_texf",
							startcalendar.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("calendar2_texf",
							endcalendar.getText().toString()));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
							"UTF-8"));

					// Execute HTTP Post Request
					HttpResponse execute = httpclient.execute(httppost);
					InputStream content = execute.getEntity().getContent();
					// BufferedReader buffer = new BufferedReader(new
					// InputStreamReader(content));
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content, "UTF-8"));

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

			// 4
			private OnClickListener cancel_button_click_listener = new OnClickListener() {
				public void onClick(View v) {
					pwindo.dismiss();
				}
			};

			private OnClickListener create_button_click_listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					new createactivity().execute();
					pwindo.dismiss();

				}
			};

			// pop up DateTimepicker for Start date
			private OnClickListener showStartdatePicker = new OnClickListener() {
				@Override
				public void onClick(View v) {

					// Create the dialog
					final Dialog mDateTimeDialog = new Dialog(WallPage.this);
					// Inflate the root layout
					final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater()
							.inflate(R.layout.datetimepicker, null);
					final LinearLayout createDialogView = (LinearLayout) getLayoutInflater()
							.inflate(R.layout.index_createactivity, null);
					// Grab widget instance
					final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView
							.findViewById(R.id.DateTimePicker);
					mDateTimePicker.setDateChangedListener(WallPage.this);

					// Update demo edittext when the "OK" button is clicked
					((Button) mDateTimeDialogView
							.findViewById(R.id.SetDateTime))
							.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									try {
										mDateTimePicker.clearFocus();
										// Check month from character to number
										String month = "";
										if (mDateTimePicker.getMonth().equals(
												"Jan")) {
											month = "01";
										} else if (mDateTimePicker.getMonth()
												.equals("Feb")) {
											month = "02";
										} else if (mDateTimePicker.getMonth()
												.equals("Mar")) {
											month = "03";
										} else if (mDateTimePicker.getMonth()
												.equals("Apr")) {
											month = "04";
										} else if (mDateTimePicker.getMonth()
												.equals("May")) {
											month = "05";
										} else if (mDateTimePicker.getMonth()
												.equals("Jun")) {
											month = "06";
										} else if (mDateTimePicker.getMonth()
												.equals("Jul")) {
											month = "07";
										} else if (mDateTimePicker.getMonth()
												.equals("Aug")) {
											month = "08";
										} else if (mDateTimePicker.getMonth()
												.equals("Sep")) {
											month = "09";
										} else if (mDateTimePicker.getMonth()
												.equals("Oct")) {
											month = "10";
										} else if (mDateTimePicker.getMonth()
												.equals("Nov")) {
											month = "11";
										} else if (mDateTimePicker.getMonth()
												.equals("Dec")) {
											month = "12";
										}

										// get hour to 2 position ex. 01,02,03
										// not 1,2,3
										String hour = "";
										if (String.valueOf(
												mDateTimePicker.getHour())
												.length() == 1) {
											hour = "0"
													+ String.valueOf(mDateTimePicker
															.getHour());
										} else {
											hour = String
													.valueOf(mDateTimePicker
															.getHour());
										}

										// get minute to 2 position ex. 01,02,03
										// not 1,2,3
										String minute = "";
										if (String.valueOf(
												mDateTimePicker.getMinute())
												.length() == 1) {
											minute = "0"
													+ String.valueOf(mDateTimePicker
															.getMinute());
										} else {
											minute = String
													.valueOf(mDateTimePicker
															.getMinute());
										}

										String result_string = String
												.valueOf(mDateTimePicker
														.getYear())
												+ "-"
												+ month
												+ "-"
												+ String.valueOf(mDateTimePicker
														.getDay())
												+ "  "
												+ hour + ":" + minute + ":00";
										System.out.print("Result2"
												+ result_string);

										EditText text = (EditText) showDialogView
												.findViewById(R.id.calendar1_texf);
										text.setText(result_string);
										// ((EditText)findViewById(R.id.calendar1_texf)).setText("123453");
										mDateTimeDialog.cancel();
									} catch (Exception e) {
										Log.i("Log", e.getMessage() + "Error!");
									}
								}
							});

					// Cancel the dialog when the "Cancel" button is clicked
					((Button) mDateTimeDialogView
							.findViewById(R.id.CancelDialog))
							.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mDateTimeDialog.cancel();
								}
							});

					// Reset Date and Time pickers when the "Reset" button is
					// clicked

					((Button) mDateTimeDialogView
							.findViewById(R.id.ResetDateTime))
							.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									// TODO Auto-generated method stub
									mDateTimePicker.reset();
								}
							});

					// Setup TimePicker
					// No title on the dialog window
					mDateTimeDialog
							.requestWindowFeature(Window.FEATURE_NO_TITLE);
					// Set the dialog content view
					mDateTimeDialog.setContentView(mDateTimeDialogView);
					// Display the dialog
					mDateTimeDialog.show();
				}
			};

			// pop up DateTimepicker for End date
			private OnClickListener showEnddatePicker = new OnClickListener() {
				@Override
				public void onClick(View v) {

					// Create the dialog
					final Dialog mDateTimeDialog = new Dialog(WallPage.this);
					// Inflate the root layout
					final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater()
							.inflate(R.layout.datetimepicker, null);
					// Grab widget instance
					final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView
							.findViewById(R.id.DateTimePicker);
					mDateTimePicker.setDateChangedListener(WallPage.this);

					// Update demo edittext when the "OK" button is clicked
					((Button) mDateTimeDialogView
							.findViewById(R.id.SetDateTime))
							.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									try {
										mDateTimePicker.clearFocus();
										// Check month from character to number
										String month = "";
										if (mDateTimePicker.getMonth().equals(
												"Jan")) {
											month = "01";
										} else if (mDateTimePicker.getMonth()
												.equals("Feb")) {
											month = "02";
										} else if (mDateTimePicker.getMonth()
												.equals("Mar")) {
											month = "03";
										} else if (mDateTimePicker.getMonth()
												.equals("Apr")) {
											month = "04";
										} else if (mDateTimePicker.getMonth()
												.equals("May")) {
											month = "05";
										} else if (mDateTimePicker.getMonth()
												.equals("Jun")) {
											month = "06";
										} else if (mDateTimePicker.getMonth()
												.equals("Jul")) {
											month = "07";
										} else if (mDateTimePicker.getMonth()
												.equals("Aug")) {
											month = "08";
										} else if (mDateTimePicker.getMonth()
												.equals("Sep")) {
											month = "09";
										} else if (mDateTimePicker.getMonth()
												.equals("Oct")) {
											month = "10";
										} else if (mDateTimePicker.getMonth()
												.equals("Nov")) {
											month = "11";
										} else if (mDateTimePicker.getMonth()
												.equals("Dec")) {
											month = "12";
										}

										// get hour to 2 position ex. 01,02,03
										// not 1,2,3
										String hour = "";
										if (String.valueOf(
												mDateTimePicker.getHour())
												.length() == 1) {
											hour = "0"
													+ String.valueOf(mDateTimePicker
															.getHour());
										} else {
											hour = String
													.valueOf(mDateTimePicker
															.getHour());
										}

										// get minute to 2 position ex. 01,02,03
										// not 1,2,3
										String minute = "";
										if (String.valueOf(
												mDateTimePicker.getMinute())
												.length() == 1) {
											minute = "0"
													+ String.valueOf(mDateTimePicker
															.getMinute());
										} else {
											minute = String
													.valueOf(mDateTimePicker
															.getMinute());
										}

										String result_string = String
												.valueOf(mDateTimePicker
														.getYear())
												+ "-"
												+ month
												+ "-"
												+ String.valueOf(mDateTimePicker
														.getDay())
												+ "  "
												+ hour + ":" + minute + ":00";
										System.out.print("Result2"
												+ result_string);

										// EditText text = (EditText)
										// pwindo.getContentView().findViewById(R.id.calendar2_texf);
										EditText text = (EditText) showDialogView
												.findViewById(R.id.calendar2_texf);

										text.setText(result_string);
										// ((EditText)findViewById(R.id.calendar1_texf)).setText("123453");
										mDateTimeDialog.cancel();
									} catch (Exception e) {
										Log.i("Log", e.getMessage() + "Error!");
									}
								}
							});

					// Cancel the dialog when the "Cancel" button is clicked
					((Button) mDateTimeDialogView
							.findViewById(R.id.CancelDialog))
							.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mDateTimeDialog.cancel();
								}
							});

					// Reset Date and Time pickers when the "Reset" button is
					// clicked

					((Button) mDateTimeDialogView
							.findViewById(R.id.ResetDateTime))
							.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									// TODO Auto-generated method stub
									mDateTimePicker.reset();
								}
							});

					// Setup TimePicker
					// No title on the dialog window
					mDateTimeDialog
							.requestWindowFeature(Window.FEATURE_NO_TITLE);
					// Set the dialog content view
					mDateTimeDialog.setContentView(mDateTimeDialogView);
					// Display the dialog
					mDateTimeDialog.show();
				}
			};

			private void showCreateActivitys(View v) {
				final Dialog createDialog = new Dialog(WallPage.this);
				// Inflate the root layout
				showDialogView = (LinearLayout) getLayoutInflater().inflate(
						R.layout.index_createactivity, null);
				// Setup TimePicker

				((ImageView) showDialogView.findViewById(R.id.imageCross))
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								createDialog.cancel();

							}
						});

				((Button) showDialogView.findViewById(R.id.btn_create_popup))
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (btnValidateName(v)
										&& btnValidateLocation(v)
										&& btnValidateDateTime(v)) {
									new createactivity().execute();
									createDialog.cancel();
								} else {

								}

							}
						});
				// generate a 150x150 QR code

				calendarStart = (ImageView) showDialogView
						.findViewById(R.id.imagecalendar1);
				calendarStart.setOnClickListener(showStartdatePicker);

				calendarEnd = (ImageView) showDialogView
						.findViewById(R.id.imagecalendar2);
				calendarEnd.setOnClickListener(showEnddatePicker);

				// No title on the dialog window
				createDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// Set the dialog content view
				createDialog.setContentView(showDialogView);
				// Display the dialog
				createDialog.show();
			}

			// Check validate of Name ---
			public boolean btnValidateName(View v) {
				boolean value = true;
				try {
					namechk = (EditText) showDialogView
							.findViewById(R.id.activityname_texf);
					// Get value converted to a string
					strName = namechk.getText().toString();
					// Empty value checking
					if (strName.isEmpty()) {
						Toast.makeText(v.getContext(),
								"Your Activity name must not empty",
								Toast.LENGTH_SHORT).show();
						value = false;
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
				return value;
			}

			// Check validate of Name ---
			public boolean btnValidateLocation(View v) {
				boolean value = true;
				try {
					locationchk = (EditText) showDialogView
							.findViewById(R.id.location_texf);
					// Get value converted to a string
					strLocation = locationchk.getText().toString();
					// Empty value checking
					if (strLocation.isEmpty()) {
						Toast.makeText(v.getContext(),
								"Your Location must not empty",
								Toast.LENGTH_SHORT).show();
						value = false;
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
				return value;
			}

			// Check validate of DateTime ---
			@SuppressLint("SimpleDateFormat")
			public boolean btnValidateDateTime(View v) {
				boolean value = true;
				try {
					// Get value converted to a string
					startcalendar = (EditText) showDialogView
							.findViewById(R.id.calendar1_texf);
					endcalendar = (EditText) showDialogView
							.findViewById(R.id.calendar2_texf);
					String startdate = startcalendar.getText().toString();
					String enddate = endcalendar.getText().toString();

					String myFormatString = "yyyy-MM-dd HH:mm:ss"; // for
																	// example
					SimpleDateFormat df = new SimpleDateFormat(myFormatString);
					java.util.Date date1 = df.parse(enddate);
					java.util.Date startingDate = df.parse(startdate);

					if (date1.after(startingDate)) {
						value = true;
					} else {
						Toast.makeText(
								v.getContext(),
								"End date must not less than start date, please check again.",
								Toast.LENGTH_SHORT).show();
						value = false;
					}

				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return value;
			}
 
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
			
		}

		// check internet
		if (isConnectInternet()) {
			Toast.makeText(getApplication(), "Internet Error",
					Toast.LENGTH_LONG).show();
			Log.d(TAG, "Internet Can not..");
		} else {
			Toast.makeText(getApplication(), "connected", Toast.LENGTH_LONG)
					.show();
		}

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
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

	// 1. check username of username form server and select ---
	private String selDataUser(String url, String UserNamelogin) {

		// sent to check
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url + UserNamelogin); // url

		try {
			List<NameValuePair> datauser = new ArrayList<NameValuePair>(2);
			datauser.add(new BasicNameValuePair("chkUsername_text",
					UserNamelogin));
			httppost.setEntity(new UrlEncodedFormEntity(datauser));

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
			Log.d("result", text);
			return text;
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return "";
	}
 

 
	//profile view
	public void profile() {
				
			final Dialog createDialogProfile = new Dialog(WallPage.this);
			showDialogProfile = (LinearLayout) getLayoutInflater().inflate(R.layout.index_profile, null);
			
			// click image close windowns
			((ImageView) showDialogProfile.findViewById(R.id.closeprofile))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							createDialogProfile.cancel();
						}
					});
			
			
			//sent name to profile
			TextView varName = (TextView)showDialogProfile.findViewById(R.id.profilename_text);
			varName.setText(sentName);
			
			//sent image to profile
			ImageView imageViewProfile2 = (ImageView) showDialogProfile.findViewById(R.id.PictureProfilePage);
			imageViewProfile2.getDrawable();
			
			ImageLoader imageLoader = new ImageLoader(sentImg,imageViewProfile2);//ภาพ
			
			// No title on the dialog window
			createDialogProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
			createDialogProfile.setContentView(showDialogProfile);
			// Display the dialog
			createDialogProfile.show();
		}

	
// action menu
@Override
 public boolean onCreateOptionsMenu(Menu menu) {
   MenuInflater inflater = getMenuInflater();
   inflater.inflate(R.menu.actionmenu, menu);
 
   return true;
 } 
 
	@Override
 public boolean onOptionsItemSelected(MenuItem item) {
   
		int id = item.getItemId(); 
		
		if(id == R.id.createactivity){
		 
			//showCreateActivitys(v);
		}
		else if(id == R.id.profile){
			profile();
		}
		
   return true;
 } 
	


 



	// get data
	protected class GetDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpPhotoCheckIn httpPhotoCheckIn = new HttpPhotoCheckIn();
			String result = httpPhotoCheckIn
					.connect("http://www.checkinphoto.com/android/activity/selectActivity.php");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONArray jsonArray = new JSONArray(result);
				Log.d("JSON Array", jsonArray.toString());

				for (int i = 0; i <= 10; i++) {
					hashMap = new HashMap<String, String>();
					JSONObject jsonObject = new JSONObject(
							jsonArray.getString(i));

					// set string tail
					String textMore = "  อ่านต่อ..";

					// title
					hashMap.put("title", jsonObject.getString("activityname"));
					// detail
					int countdetail = jsonObject.getString("activitydetail")
							.length();

					// if detail >= 120 show button more
					if (countdetail >= 120) {
						// set maxtext 120
						String cuttext = jsonObject.getString("activitydetail");
						StringBuilder strResults = new StringBuilder(cuttext);
						strResults.setLength(120);
						hashMap.put("detail", strResults + textMore);
					} else {
						hashMap.put("detail",
								jsonObject.getString("activitydetail"));
					}

					// last time format date

					try {
						String dateString = jsonObject
								.getString("registerdate");

						java.util.Date date = null;
						SimpleDateFormat oldFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss"); // beform
						SimpleDateFormat newFormat = new SimpleDateFormat(
								"เมื่อ: HH:mm:ss    วันที่: dd-MMM-yyyy"); // after

						date = oldFormat.parse(dateString);
						hashMap.put("time", newFormat.format(date));

					} catch (ParseException e) {
						// TODO Auto-generated catch block
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// add array
					arrayList.add(hashMap);

				}

				SimpleAdapter adapter = new SimpleAdapter(WallPage.this,
						arrayList, R.layout.list_box, new String[] { "title",
								"detail", "time" }, new int[] { R.id.Title,
								R.id.Detail, R.id.Time });
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
 
}