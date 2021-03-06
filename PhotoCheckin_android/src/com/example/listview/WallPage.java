package com.example.listview;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
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
import android.widget.BaseAdapter;
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
import com.example.photocheckin.CreateActivity;
import com.example.photocheckin.DateTimePicker;
import com.example.photocheckin.GeocodeJSONParser;
import com.example.photocheckin.LoginForm;
import com.example.photocheckin.Profile;
import com.example.photocheckin.TakePhotoCheckin;
import com.example.photocheckin.chkRegister;
import com.example.photocheckin.DateTimePicker.DateWatcher;
import com.example.photocheckin.R;
import com.example.photocheckin.http.HttpPhotoCheckIn;
import com.google.android.gms.internal.ar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WallPage extends FragmentActivity implements View.OnClickListener,
		DateWatcher, OnMapClickListener, OnMapLongClickListener,
		OnInfoWindowClickListener {

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
	ImageView getimageView;
	String urlImages;
	private ProgressDialog pDialog;
	private String response = "";
	private String strName;
	private String strLocation;
	private LinearLayout showDialogView;
	public LinearLayout showDialogProfile;
	public LinearLayout showDialogTakePhoto;
	public LinearLayout createDialogView, showDialogViewImg;
	String UserNamelogin;
	private EditText activityname;
	private EditText activitydetail;
	private EditText location;
	private EditText startcalendar;
	private EditText endcalendar;
	private EditText namechk;
	private EditText locationchk;
	public String sentName;
	public String sentImg;
	private GoogleMap mMap;
	private ImageView searchMap;
	private EditText etPlace;
	private Button confrimlocation;
	private ImageView mapSearch;
	private EditText mapsearchtext;
	private Dialog createDialog;
	private ImageAdapter imaAdap;

	JSONObject jsonObject;
	JSONArray jsonArray;
	// Json api
	public static final String TAG_ID = "id";
	public static final String TAG_LASTTIME = "lasttime";
	public static final String TAG_ACTIVITYNAME = "activityname";
	public static final String TAG_ACTIVITYDETAIL = "activitydetail";
	public static final String TAG_CREATEBY = "createby";
	public static final String TAG_CONDITION = "condition";
	public static final String TAG_USERTYPE = "usertype";
	public static final String TAG_BLOCK = "block";
	public static final String TAG_REGISTERDATE = "registerdate";
	public static final String TAG_STARTDATE = "startdate";
	public static final String TAG_ENDDATE = "enddate";
	public static final String TAG_PICTURE = "picture";
	public static final String TAG_LATITUDE = "latitude";
	public static final String TAG_LONGITUDE = "longitude";
	public static final String TAG_QRCODE = "qrcode";

	ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hashMap;
	public String name;
	public int pic;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_wallpage);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		// hiding default app icon
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.topbar));
		// displaying custom ActionBar
		View mActionBarView = getLayoutInflater().inflate(
				R.layout.my_action_bar, null);
		actionBar.setCustomView(mActionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		// get username form login
		UserNamelogin = getIntent().getStringExtra("username");

		// sent to wallpage
		ImageView imageViewProfile = (ImageView) findViewById(R.id.pictureprofile);
		TextView textViewName = (TextView) findViewById(R.id.nameuser_text);
		listview = (ListView) findViewById(android.R.id.list);
		// imageViewProfile2 = (ImageView)
		// findViewById(R.id.PictureProfilePage);

		String imageJson = selDataUser(
				"http://www.checkinphoto.com/android/userprofile/seluser.php?username=",
				UserNamelogin);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(imageJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

				ImageLoader imageLoader = new ImageLoader(
				jsonObject.getString("pic"), imageViewProfile);// ภาพ
				textViewName.setText(jsonObject.getString("name"));// ชื่อ
				// sent go profile
				sentImg = jsonObject.getString("pic");// ภาพ
				// name
				sentName = jsonObject.getString("name");

			}

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		hashMap = new HashMap<String, String>();
		new GetDataTask().execute();
		new getSelCheckinphoto().execute();

		// end

	}

	// Button Dialog Popup to Search Google Map
	private OnClickListener showMapSearch = new OnClickListener() {
		@Override
		public void onClick(View v) {
			showMapSearchResult();
		}
	};

	// Button Dialog Popup to Search Google Map
	private OnClickListener searchMapp = new OnClickListener() {
		@Override
		public void onClick(View v) {
			searchMappResult();
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
			((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							try {
								mDateTimePicker.clearFocus();
								// Check month from character to number
								String month = "";
								if (mDateTimePicker.getMonth().equals("Jan")) {
									month = "01";
								} else if (mDateTimePicker.getMonth().equals(
										"Feb")) {
									month = "02";
								} else if (mDateTimePicker.getMonth().equals(
										"Mar")) {
									month = "03";
								} else if (mDateTimePicker.getMonth().equals(
										"Apr")) {
									month = "04";
								} else if (mDateTimePicker.getMonth().equals(
										"May")) {
									month = "05";
								} else if (mDateTimePicker.getMonth().equals(
										"Jun")) {
									month = "06";
								} else if (mDateTimePicker.getMonth().equals(
										"Jul")) {
									month = "07";
								} else if (mDateTimePicker.getMonth().equals(
										"Aug")) {
									month = "08";
								} else if (mDateTimePicker.getMonth().equals(
										"Sep")) {
									month = "09";
								} else if (mDateTimePicker.getMonth().equals(
										"Oct")) {
									month = "10";
								} else if (mDateTimePicker.getMonth().equals(
										"Nov")) {
									month = "11";
								} else if (mDateTimePicker.getMonth().equals(
										"Dec")) {
									month = "12";
								}

								// get hour to 2 position ex. 01,02,03
								// not 1,2,3
								String hour = "";
								if (String.valueOf(mDateTimePicker.getHour())
										.length() == 1) {
									hour = "0"
											+ String.valueOf(mDateTimePicker
													.getHour());
								} else {
									hour = String.valueOf(mDateTimePicker
											.getHour());
								}

								// get minute to 2 position ex. 01,02,03
								// not 1,2,3
								String minute = "";
								if (String.valueOf(mDateTimePicker.getMinute())
										.length() == 1) {
									minute = "0"
											+ String.valueOf(mDateTimePicker
													.getMinute());
								} else {
									minute = String.valueOf(mDateTimePicker
											.getMinute());
								}

								String result_string = String
										.valueOf(mDateTimePicker.getYear())
										+ "-"
										+ month
										+ "-"
										+ String.valueOf(mDateTimePicker
												.getDay())
										+ "  "
										+ hour
										+ ":"
										+ minute + ":00";
								System.out.print("Result2" + result_string);

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
			((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							mDateTimeDialog.cancel();
						}
					});

			// Reset Date and Time pickers when the "Reset" button is
			// clicked

			((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime))
					.setOnClickListener(new OnClickListener() {

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
			((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							try {
								mDateTimePicker.clearFocus();
								// Check month from character to number
								String month = "";
								if (mDateTimePicker.getMonth().equals("Jan")) {
									month = "01";
								} else if (mDateTimePicker.getMonth().equals(
										"Feb")) {
									month = "02";
								} else if (mDateTimePicker.getMonth().equals(
										"Mar")) {
									month = "03";
								} else if (mDateTimePicker.getMonth().equals(
										"Apr")) {
									month = "04";
								} else if (mDateTimePicker.getMonth().equals(
										"May")) {
									month = "05";
								} else if (mDateTimePicker.getMonth().equals(
										"Jun")) {
									month = "06";
								} else if (mDateTimePicker.getMonth().equals(
										"Jul")) {
									month = "07";
								} else if (mDateTimePicker.getMonth().equals(
										"Aug")) {
									month = "08";
								} else if (mDateTimePicker.getMonth().equals(
										"Sep")) {
									month = "09";
								} else if (mDateTimePicker.getMonth().equals(
										"Oct")) {
									month = "10";
								} else if (mDateTimePicker.getMonth().equals(
										"Nov")) {
									month = "11";
								} else if (mDateTimePicker.getMonth().equals(
										"Dec")) {
									month = "12";
								}

								// get hour to 2 position ex. 01,02,03
								// not 1,2,3
								String hour = "";
								if (String.valueOf(mDateTimePicker.getHour())
										.length() == 1) {
									hour = "0"
											+ String.valueOf(mDateTimePicker
													.getHour());
								} else {
									hour = String.valueOf(mDateTimePicker
											.getHour());
								}

								// get minute to 2 position ex. 01,02,03
								// not 1,2,3
								String minute = "";
								if (String.valueOf(mDateTimePicker.getMinute())
										.length() == 1) {
									minute = "0"
											+ String.valueOf(mDateTimePicker
													.getMinute());
								} else {
									minute = String.valueOf(mDateTimePicker
											.getMinute());
								}

								String result_string = String
										.valueOf(mDateTimePicker.getYear())
										+ "-"
										+ month
										+ "-"
										+ String.valueOf(mDateTimePicker
												.getDay())
										+ "  "
										+ hour
										+ ":"
										+ minute + ":00";
								System.out.print("Result2" + result_string);

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
			((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// TODO Auto-generated method stub
							mDateTimeDialog.cancel();
						}
					});

			// Reset Date and Time pickers when the "Reset" button is
			// clicked

			((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime))
					.setOnClickListener(new OnClickListener() {

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

	private void showCreateActivitys(View v) {
		createDialog = new Dialog(WallPage.this);
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
						if (btnValidateName(v) && btnValidateLocation(v)
								&& btnValidateDateTime(v)) {
							new Createactivitys().execute();
							createDialog.cancel();
						} else {

						}

					}
				});
		// generate a 150x150 QR code

		// set cannot focus edittext
		location = (EditText) showDialogView.findViewById(R.id.location_texf);
		startcalendar = (EditText) showDialogView
				.findViewById(R.id.calendar1_texf);
		endcalendar = (EditText) showDialogView
				.findViewById(R.id.calendar2_texf);
		mapsearchtext = (EditText) showDialogView
				.findViewById(R.id.search_texf);
		// location.setFocusable(false);
		startcalendar.setFocusable(false);
		endcalendar.setFocusable(false);
		mapSearch = (ImageView) showDialogView.findViewById(R.id.imageSearch);
		mapsearchtext.setFocusable(false);

		
		mapSearch.setOnClickListener(showMapSearch);

		
		mapsearchtext.setOnClickListener(showMapSearch);

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
		// fix size dialog
		// createDialog.getWindow().setLayout(450, 700);
		// Display the dialog
		createDialog.show();
	}

	// Button click to Search Google Map
	private void searchMappResult() {
		// Getting the place entered
		String location = etPlace.getText().toString();

		if (location == null || location.equals("")) {
			Toast.makeText(getBaseContext(), "No Place is entered",
					Toast.LENGTH_SHORT).show();
			return;
		}

		String url = "https://maps.googleapis.com/maps/api/geocode/json?";

		try {
			// encoding special characters like space in the user input place
			location = URLEncoder.encode(location, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String address = "address=" + location;

		String sensor = "sensor=false";

		// url , from where the geocoding data is fetched
		url = url + address + "&" + sensor;

		// Instantiating DownloadTask to get places from Google Geocoding
		// service
		// in a non-ui thread
		DownloadTask downloadTask = new DownloadTask();

		// Start downloading the geocoding places
		downloadTask.execute(url);

	};

	// Dialog Popup to Search Google Map
	private void showMapSearchResult() {
		final Dialog mapDialog = new Dialog(WallPage.this);
		// showDialogView = (LinearLayout) getLayoutInflater().inflate(
		// R.layout.index_googlemap, null);

		createDialogView = (LinearLayout) getLayoutInflater().inflate(
				R.layout.index_googlemap, null);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		// Getting reference to the Google Map
		mMap = mapFragment.getMap();
		mMap.setMyLocationEnabled(true);
		etPlace = (EditText) createDialogView.findViewById(R.id.searchmap_texf);
		confrimlocation = (Button) createDialogView
				.findViewById(R.id.btn_ok_popup);
		confrimlocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String et = etPlace.getText().toString();
				EditText text = (EditText) showDialogView
						.findViewById(R.id.location_texf);
				text.setText(et);
				mapDialog.cancel();
			}
		});
		// Getting the place entered

		searchMap = (ImageView) createDialogView
				.findViewById(R.id.imageSearchMap);
		searchMap.setOnClickListener(searchMapp);

		// No title on the dialog window
		mapDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mapDialog.setContentView(createDialogView);
		// fix size dialog
		mapDialog.getWindow().setLayout(450, 500);
		// Display the dialog
		mapDialog.show();
	}

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();
			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}

	/** A class, to download Places from Geocoding webservice */
	class DownloadTask extends AsyncTask<String, Integer, String> {

		String data = null;

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {

			// Instantiating ParserTask which parses the json data from
			// Geocoding webservice
			// in a non-ui thread
			ParserTask parserTask = new ParserTask();

			// Start parsing the places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
	}

	/** A class to parse the Geocoding Places in non-ui thread */
	class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			List<HashMap<String, String>> places = null;
			GeocodeJSONParser parser = new GeocodeJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				/** Getting the parsed data as a an ArrayList */
				places = parser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {

			// Clears all the existing markers
			mMap.clear();

			for (int i = 0; i < list.size(); i++) {

				// Creating a marker
				MarkerOptions markerOptions = new MarkerOptions();

				// Getting a place from the places list
				HashMap<String, String> hmPlace = list.get(i);

				// Getting latitude of the place
				double lat = Double.parseDouble(hmPlace.get("lat"));

				// Getting longitude of the place
				double lng = Double.parseDouble(hmPlace.get("lng"));

				// Getting name
				String name = hmPlace.get("formatted_address");

				LatLng latLng = new LatLng(lat, lng);

				// Setting the position for the marker
				markerOptions.position(latLng);

				// Setting the title for the marker
				markerOptions.title(name);

				// Placing a marker on the touched position
				mMap.addMarker(markerOptions);

				// Locate the first location
				if (i == 0)
					mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
			}
		}
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
						"Your Activity name must not empty", Toast.LENGTH_SHORT)
						.show();
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
				Toast.makeText(v.getContext(), "Your Location must not empty",
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

			if (!startdate.equals("") && !enddate.equals("")) {
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

	// take a photo checkin--
	public void checkinPhoto() {

		Intent goTakePhotoCheckin = new Intent(getApplicationContext(),
				TakePhotoCheckin.class);
		startActivity(goTakePhotoCheckin);

	}// end

	// profile view
	public void profile() {

		final Dialog createDialogProfile = new Dialog(WallPage.this);
		showDialogProfile = (LinearLayout) getLayoutInflater().inflate(
				R.layout.index_profile, null);

		// click image close windowns
		((ImageView) showDialogProfile.findViewById(R.id.closeprofile))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						createDialogProfile.cancel();
					}
				});

		// sent name to profile
		TextView varName = (TextView) showDialogProfile.findViewById(R.id.profilename_text);
		varName.setText(sentName);

		// sent image to profile
		ImageView imageViewProfile2 = (ImageView) showDialogProfile
				.findViewById(R.id.PictureProfilePage);
		imageViewProfile2.getDrawable();

		ImageLoader imageLoader = new ImageLoader(sentImg, imageViewProfile2);// ภาพ

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
		View v = null;
		int id = item.getItemId();

		if (id == R.id.createactivity) {
			showCreateActivitys(v);
		} else if (id == R.id.profile) {
			profile();
		} else if (id == R.id.Logout) {
			finish();
			Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
			startActivity(goMain);

		} else if (id == R.id.checkin_photo) {
			// checkinPhoto();
			Intent newActivity = new Intent(WallPage.this,
					TakePhotoCheckin.class);
			newActivity.putExtra("UserName", UserNamelogin);
			startActivity(newActivity);
		}

		return true;
	}

	// Create activity --
	protected class Createactivitys extends AsyncTask<String, Void, String> {
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

				nameValuePairs.add(new BasicNameValuePair("activityname_texf",
						activityname.getText().toString()));
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
				createDialog.dismiss();
			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
			pDialog.dismiss();
		}

	}

	// get data
	@SuppressLint("SimpleDateFormat")
	protected class GetDataTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				jsonArray = new JSONArray(result);
				Log.d("JSON Array", jsonArray.toString());

				for (int i = 0; i <= 10; i++) {
					hashMap = new HashMap<String, String>();
					jsonObject = new JSONObject(jsonArray.getString(i));

					// set string tail
					String textMore = "  อ่านต่อ..";

					// title
					hashMap.put("title", jsonObject.getString("activityname"));

					hashMap.put("id", jsonObject.getString(TAG_ID));
					hashMap.put("lasttime", jsonObject.getString(TAG_LASTTIME));
					hashMap.put("activityname",
							jsonObject.getString(TAG_ACTIVITYNAME));
					hashMap.put("activitydetail",
							jsonObject.getString(TAG_ACTIVITYDETAIL));
					hashMap.put("createby", jsonObject.getString(TAG_CREATEBY));
					hashMap.put("condition",
							jsonObject.getString(TAG_CONDITION));
					hashMap.put("usertype", jsonObject.getString(TAG_USERTYPE));
					hashMap.put("block", jsonObject.getString(TAG_BLOCK));
					hashMap.put("registerdate",
							jsonObject.getString(TAG_REGISTERDATE));
					hashMap.put("startdate",
							jsonObject.getString(TAG_STARTDATE));
					hashMap.put("enddate", jsonObject.getString(TAG_ENDDATE));
					hashMap.put("picture", jsonObject.getString(TAG_PICTURE));
					hashMap.put("latitude", jsonObject.getString(TAG_LATITUDE));
					hashMap.put("longitude",
							jsonObject.getString(TAG_LONGITUDE));
					hashMap.put("qrcode", jsonObject.getString(TAG_QRCODE));

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

				// Pass the results into ListViewAdapter.java
				adapter = new LazyAdapter(WallPage.this, arrayList);
				// Set the adapter to the ListView
				listview.setAdapter(adapter);

			} catch (JSONException e) {
				Log.e("ERROR JSON", e.toString());
			}

			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpPhotoCheckIn httpPhotoCheckIn = new HttpPhotoCheckIn();
			String result = httpPhotoCheckIn
					.connect("http://www.checkinphoto.com/android/activity/selectActivity.php");

			return result;
		}

	}

	// TODO select data all
	protected class getSelCheckinphoto extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			HttpPhotoCheckIn httpPhotoCheckIn2 = new HttpPhotoCheckIn();
			String result = httpPhotoCheckIn2
					.connect("http://checkinphoto.com/android/listview/listview.php");

			try {
				JSONArray jsonArray = new JSONArray(result);
				Log.d("JSON Array", jsonArray.toString());

				for (int i = 0; i <= 10; i++) {
					// for (int i = 0; i <= jsonArray.length(); i++) {
					hashMap = new HashMap<String, String>();
					JSONObject jsonObject = new JSONObject(
							jsonArray.getString(i));

					// name
					hashMap.put("namelocation", jsonObject.getString("name"));
					// like
					hashMap.put("getLike", jsonObject.getString("like"));
					// image
					hashMap.put("imagePath", jsonObject.getString("image"));
					// image profile
					hashMap.put("imageProfile",
							jsonObject.getString("createby"));

					getimageView = (ImageView) findViewById(R.id.ImagePost);
					urlImages = jsonObject.getString("image");
					// Log.d(">url img>>>>>>>>>>:", urlImages);
					// ImageLoader imgLoader = new
					// ImageLoader(urlImages,getimageView); //path+setview
					// hashMap.put("imagePath",urlImages );

					// set string tail
					String textMore = "  อ่านต่อ..";
					// detail
					int countdetail = jsonObject.getString("detail").length();
					// TODO SHOW VISIBLE
					if (countdetail >= 120) {
						String cuttext = jsonObject.getString("detail");
						StringBuilder strResults = new StringBuilder(cuttext);
						strResults.setLength(120);
						hashMap.put("Detail_text", strResults + textMore);
						// show.setVisibility(View.VISIBLE);
					} else {
						hashMap.put("Detail_text",
								jsonObject.getString("detail"));
						// show.setVisibility(View.VISIBLE);
					}

					// last time format date

					try {
						String dateString = jsonObject.getString("createDate");
						java.util.Date date = null;
						SimpleDateFormat oldFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss"); // beform
						SimpleDateFormat newFormat = new SimpleDateFormat(
								"เมื่อ: HH:mm:ss    วันที่: dd-MMM-yyyy"); // after
						date = oldFormat.parse(dateString);
						hashMap.put("time", newFormat.format(date));

					} catch (ParseException e) {
						e.printStackTrace();
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					// add array
					arrayList.add(hashMap);
				}

				// final ArrayList<HashMap<String, String>> MyArrList = new
				// ArrayList<HashMap<String, String>>();
				// HashMap<String, String> map;
				// final AlertDialog.Builder imageDialog = new
				// AlertDialog.Builder(this);

				// click img listview

			}

			catch (JSONException e) {
				Log.e("ERROR JSON", e.toString());
			}

			return null;
		}

		private LayoutInflater getSystemService(String layoutInflaterService) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			listview.setAdapter(new ImageAdapter(WallPage.this, arrayList));

			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//Log.d("ResID aa", "" + arrayList.get(position));
					AlertDialog.Builder imageDialog = new AlertDialog.Builder(
							WallPage.this);
		 

					ImageView image = new ImageView(getApplicationContext());
					
					int ResID = getResources().getIdentifier(arrayList.get(position).get("imagePath"),
							"drawable", WallPage.this.getPackageName());
					//Log.d("ResID", "" + ResID);
					//ResID.getLayoutParams().width = 450;
					//image.getLayoutParams().height = 300;
					
					ImageLoader imageLoader = new ImageLoader(arrayList.get(position).get("imagePath"), image);

					//img
					//imageDialog.setIcon(android.R.drawable.btn_star_big_on);
					imageDialog.setIcon(android.R.drawable.btn_star_big_on);
					imageDialog.setTitle(""+ arrayList.get(position).get("Detail_text"));
					imageDialog.setView(image);
					imageDialog.setPositiveButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}

							});
	 

					imageDialog.create();
					imageDialog.show();

				}

				private LayoutInflater getSystemService(
						String layoutInflaterService) {
					return null;
				}

			});

			super.onPostExecute(result);
		}
	}

	// e

	// TODO IMGVIEW
	public void DialogViewImages() {
		// TODO IMGVIEW

	}

	// e

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();

		public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list) {
			// TODO Auto-generated method stub
			context = c;
			MyArr = list;
		}

		public ImageAdapter(getSelCheckinphoto getSelCheckinphoto) {
			// TODO Auto-generated constructor stub
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return MyArr.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_box, null);
			}

			// ColImageProfile
			ImageView imageDisplay = (ImageView) convertView
					.findViewById(R.id.imagePrifile);
			// imageDisplay.getLayoutParams().width = 60;
			// imageDisplay.getLayoutParams().height = 60;
			imageDisplay.setScaleType(ImageView.ScaleType.CENTER_CROP);

			try {
				imageDisplay.setImageBitmap(loadBitmap(MyArr.get(position).get(
						"imageProfile")));
			} catch (Exception e) {
				// When Error
				imageDisplay
						.setImageResource(android.R.drawable.ic_menu_report_image);
			}

			// ColImage
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.ImagePost);
			imageView.getLayoutParams().width = 450;
			imageView.getLayoutParams().height = 300;
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			try {
				imageView.setImageBitmap(loadBitmap(MyArr.get(position).get(
						"imagePath")));
			} catch (Exception e) {
				// When Error
				imageView
						.setImageResource(android.R.drawable.ic_menu_report_image);
			}

			// ColPosition
			TextView txtPosition = (TextView) convertView
					.findViewById(R.id.Title);
			txtPosition.setPadding(10, 0, 0, 0);
			txtPosition.setText(MyArr.get(position).get("namelocation"));

			// ColPicname
			TextView txtPicName = (TextView) convertView
					.findViewById(R.id.Detail);
			txtPicName.setPadding(10, 0, 0, 0);
			txtPicName.setText(MyArr.get(position).get("Detail_text"));

			// ColLike
			TextView txtLike = (TextView) convertView
					.findViewById(R.id.heartlike_count);
			txtLike.setPadding(10, 0, 0, 0);
			txtLike.setText(MyArr.get(position).get("getLike"));

			// ColTime
			TextView txtDate = (TextView) convertView.findViewById(R.id.Time);
			txtDate.setPadding(10, 0, 0, 0);
			txtDate.setText(MyArr.get(position).get("time"));

			return convertView;

		}

	}

	/***** Get Image Resource from URL (Start) *****/
	private static final String TAG_R = "ERROR";
	private static final int IO_BUFFER_SIZE = 4 * 1024;

	public static Bitmap loadBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new URL(url).openStream(),
					IO_BUFFER_SIZE);

			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
			copy(in, out);
			out.flush();

			final byte[] data = dataStream.toByteArray();
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inSampleSize = 1;

			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
					options);
		} catch (IOException e) {
			Log.e("TAG", "Could not load Bitmap from: " + url);
		} finally {
			closeStream(in);
			closeStream(out);
		}

		return bitmap;
	}
 

	private static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				android.util.Log.e(TAG_R, "Could not close stream", e);
			}
		}
	}

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
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

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

}