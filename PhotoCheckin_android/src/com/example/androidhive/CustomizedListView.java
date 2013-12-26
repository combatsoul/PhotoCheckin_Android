package com.example.androidhive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photocheckin.R;
import com.example.photocheckin.http.HttpPhotoCheckIn;
import com.google.android.gms.internal.ar;

public class CustomizedListView extends Activity {

	public String TAG = "CustomizedList";
	ListView listview;
	LazyAdapter adapter;
	ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hashMap;
	
	
	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_wallpage);
		
		//test tail
		  //TextView label = (TextView) this.findViewById (R.id.Detail); //ต่อจาก Detail text view
		//ข้อความต่อท้าย
		  //label.append ( "..ดูอีก" );
		
		listview = (ListView) findViewById(android.R.id.list);
		hashMap = new HashMap<String, String>();
		new GetDataTask().execute();
		  
		  // spinner1 -- 
		  List<String> arrList = new ArrayList<String>();
		  Spinner spin = (Spinner) findViewById(R.id.spinner1);
		  arrList.add("check-in"); 
		  arrList.add("create activity");
		  arrList.add("Profile"); 
		  ArrayAdapter<String> arrAd = new ArrayAdapter<String>( 
		  CustomizedListView.this,android.R.layout.simple_spinner_item, arrList);
		  spin.setAdapter(arrAd); spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		  {
		  
		  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){ 
			  
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
					String textMore = "    ดูต่อ..";		
					

					textMore.setTextColor(Color.parseColor("#FFFFFF"));
					myTextView.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
					
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
						CustomizedListView.this, arrayList, R.layout.list_box,
						new String[] {"title", "detail","time" },
						new int[] {R.id.Title, R.id.Detail,R.id.Time});
						listview.setAdapter(adapter);
			} catch (JSONException e) {
				Log.e("ERROR JSON", e.toString());
			}

			super.onPostExecute(result);
		}
	}
}