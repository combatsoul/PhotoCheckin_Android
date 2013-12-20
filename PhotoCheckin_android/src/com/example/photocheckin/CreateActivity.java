package com.example.photocheckin;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CreateActivity extends Activity implements View.OnClickListener,LocationListener {

	private GoogleMap map;
	private TextView output;
	private LocationManager lm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_createactivity);
		
//		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		output = (TextView) findViewById(R.id.output);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 1, this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
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

}
