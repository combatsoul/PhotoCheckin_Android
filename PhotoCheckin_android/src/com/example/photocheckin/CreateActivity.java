package com.example.photocheckin;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CreateActivity extends Activity implements View.OnClickListener,LocationListener {

	private GoogleMap map;
	private TextView output;
	private LocationManager lm;
	private PopupWindow pwindo;
	private ImageView ClosePopup; 
	
	private EditText activityname;
	private EditText activitydetail;
	private EditText location;
	private EditText startcalendar;
	private EditText endcalendar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_createactivity);
		
		// edit text for register
		activityname = (EditText) findViewById(R.id.activityname_texf);
		activitydetail = (EditText) findViewById(R.id.activitydetail_texa);
		location = (EditText) findViewById(R.id.location_texf);
		startcalendar = (EditText) findViewById(R.id.calendar1_texf);
		endcalendar = (EditText) findViewById(R.id.calendar2_texf);
		
//		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
		
		output = (TextView) findViewById(R.id.output);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		
	}
	
	protected void showdatePicker(View v){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("eewe");
		
		dialog.show();
	}
	
//	private OnClickListener cancel_button_click_listener = new OnClickListener() { 
//		public void onClick(View v) { 
//			pwindo.dismiss();
//		} 
//		};
		 
		 // We need to get the instance of the LayoutInflater 
//		 LayoutInflater inflater = (LayoutInflater) CreateActivity.this 
//		 .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
//		 View layout = inflater.inflate(R.layout.calendar_selection,(ViewGroup)
//
//		 findViewById(R.id.calendarMenu)); 
//		 pwindo = new PopupWindow(layout, 300, 300, true); 
//		 pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

//		 ClosePopup = (ImageView) layout.findViewById(R.id.imageCross); 
//		 ClosePopup.setOnClickListener(cancel_button_click_listener);
	
	@Override
	protected void onResume(){
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 1, this);
	}
	
	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_close_popup:
//			System.out.println("close_popup");
//			showdatePicker(v);
//			break;
//		}
		
	}
	
	@Override
	public void onLocationChanged(Location loc) {
		if(loc == null){
			output.append("*** ไม่สามารถระบุตำแหน่งได้  ***"+"\n");
		}else{
			output.append("ละติจูด: "+ loc.getLatitude() + "\n");
			output.append("ลองจิจูด: "+ loc.getLongitude() + "\n");
			output.append("----------------\n");
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
