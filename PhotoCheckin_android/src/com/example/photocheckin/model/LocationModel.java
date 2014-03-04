package com.example.photocheckin.model;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationModel {

	private ArrayList<HashMap<String, String>> arrayListLocation = new ArrayList<HashMap<String, String>>();
	LocationModel instance = null;

	private LocationModel() {

	}

	public LocationModel getInstance() {
		if (instance == null) {

			instance = new LocationModel();
		}
		return instance;
	}

	public ArrayList<HashMap<String, String>> getArrayListLocation() {
		return arrayListLocation;
	}

	public void setArrayListLocation(
			ArrayList<HashMap<String, String>> arrayListLocation) {
		this.arrayListLocation = arrayListLocation;
	}

}
