package com.example.photocheckin;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class GoogleMaps extends FragmentActivity implements OnMapClickListener, OnMapLongClickListener, OnInfoWindowClickListener  {
	private GoogleMap myMap;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_googlemap);
//        
        //String strLa = getIntent().getStringExtra("7.918133");
        //String strLo = getIntent().getStringExtra("98.345767");
        
        double lat = 7.918133;
		double lot = 98.345767;
		
		SupportMapFragment mySupportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
    
		myMap = mySupportMapFragment.getMap();		 

		myMap.setOnMapClickListener(this);
		myMap.setOnMapLongClickListener(this);
		myMap.setOnInfoWindowClickListener(this);
		myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		myMap.setMyLocationEnabled(true);
//		
		myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lot),17));
		myMap.addMarker(new MarkerOptions().position(new LatLng(lat,lot)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//		
    }
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
//		
	}
//
//
	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		
	}
}


