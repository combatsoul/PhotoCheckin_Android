package com.example.photocheckin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import com.example.imgaeloader.ImageLoader;
import com.example.listview.WallPage;
import com.example.photocheckin.http.HttpPhotoCheckIn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class TakePhotoCheckin extends FragmentActivity implements View.OnClickListener {
	ListView listView;
	//AutoCompleteTextView resultSearchLocation;
	protected Button btn_upload;
	protected ImageView imageview, btn_image;
	protected TextView _field;
	protected String _path_pic = null;
	protected boolean _taken;
	protected static final String PHOTO_TAKEN = "photo_taken";
	private TextView nameMap;
	public String name;
	String up_name;
	Bitmap bitmap = null;
	String lat = null, lon = null;
	TextView tv_latlon;
	String getLocationMap;
	
	public EditText saveName;
	public EditText saveLocation;
	public String chkName = "ไม่พบชื่อสถานที่";
	EditText Detail;
	
	ListView DialogSeachView;
	String keyword = "";

	String response;
	private Bitmap yourSelectedImage;
	String getUserName; 
	String Username="";
	
	public EditText setlocation, setname;
	public String locationAdress, locationName;

	GoogleMap mMap;
	Marker mMarker;
	LocationManager lm;
	public double latitude, longitude;
	public LinearLayout showDialogListView;

	private ListView lv;
	ArrayAdapter<String> adapter;
	EditText KeySearch;
	ArrayList<HashMap<String, String>> productList;

	public LinearLayout showDialogLocaltion;
	final String PHP_URL = "http://checkinphoto.com/android/checkin/checkin_photo.php"; 
	
	//TODO TOP
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_takephotocheckin);

		//get UserName
		//Intent g = getIntent();
		Username = getIntent().getStringExtra("UserName");
		
		
		tv_latlon = (TextView) findViewById(R.id.tv_latlon);
		imageview = (ImageView) findViewById(R.id.image);
		_field = (TextView) findViewById(R.id.field);
		nameMap = (TextView) findViewById(R.id.name_location);// detail
		name = nameMap.getText().toString();
		Detail = (EditText) findViewById(R.id.detail_txt);
		
		
		btn_image = (ImageView) findViewById(R.id.photo_imgbtn);
		btn_image.setOnClickListener(this);
		btn_image = (ImageView) findViewById(R.id.image);
		btn_image.setOnClickListener(this);

		btn_upload = (Button) findViewById(R.id.save_btn);
		btn_upload.setOnClickListener(this);

		File file = new File(Environment.getExternalStorageDirectory()
				+ "/myPhoto/");
		if (!file.exists()) {
			try {
				file.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 

	
	@Override
	public void onClick(View v) {		
		//TODO ถ่ายรูป	
		if (v.getId() == R.id.photo_imgbtn || v.getId() == R.id.image) {
			startCameraActivity();
		}
		

		if (v.getId() == R.id.save_btn) {

			if (bitmap != null) { // ถ้าไม่พบชื่อสถานที่	
				try{
				//check data
					if (name.equals(chkName)) {
						LocaltionSet();
						locationName = chkName;
						//setname.setText(locationName); // sent to popup text
					} 
					else{
						new ImageUploadTask().execute(bitmap); // load
					}
					
				}catch (Exception e) {
					Toast.makeText(TakePhotoCheckin.this,"Error2", Toast.LENGTH_SHORT).show();
					new ImageUploadTask().execute(bitmap); // load
				}
			}

		else {
				Toast.makeText(TakePhotoCheckin.this,
						"โปรดกรอกข้อมูลให้นครบถ้วน", Toast.LENGTH_SHORT).show();
				// onSearchRequested();
		}
		}
	}

	public void startCameraActivity() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String picTime = sdf.format(new Date());
		_path_pic = Environment.getExternalStorageDirectory() + "/myPhoto/"
				+ picTime + ".jpg";
		up_name = picTime + ".jpg";
		File file = new File(_path_pic);
		Uri outputFileUri = Uri.fromFile(file);
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 0:
			Log.i("Tag", "User cancelled");
			break;

		case -1:
			onPhotoTaken();
			break;
		}
	}

	public void onPhotoTaken() {

		_taken = true;
		try {
			String[] ll = getImageLatLon(_path_pic);
			lat = ll[0];
			lon = ll[1];
			tv_latlon.setText(lat + "," + lon); // show Latitude and Longtitude
			locationAdress = lat + "," + lon;

		} catch (Exception e) {
			e.printStackTrace();

			tv_latlon.setText("latitude,longitude");
			lat = null;
			lon = null;
			//locationAdress = "latitude,longitude";
			locationAdress = "latitude,longitude";
		}

		bitmap = decodeFile(_path_pic);
		imageview.setImageBitmap(bitmap);
		_field.setVisibility(View.GONE);
	}

	// show map
	public void LocaltionSet() {
		final Dialog createDialogLocaltion = new Dialog(TakePhotoCheckin.this);
		showDialogLocaltion = (LinearLayout) getLayoutInflater().inflate(
				R.layout.location_thishere, null);
		try {

			// close
			((Button) showDialogLocaltion.findViewById(R.id.close_btn))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							createDialogLocaltion.cancel();
						}
					});

			// ok
			((Button) showDialogLocaltion.findViewById(R.id.done_btn))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// after mofifly

							saveName = (EditText) showDialogLocaltion.findViewById(R.id.name_OnMap);
							saveLocation = (EditText) showDialogLocaltion.findViewById(R.id.location_OnMap);
							
							TextView modiflyName = (TextView) findViewById(R.id.name_location);
							TextView modiflyLocation = (TextView) findViewById(R.id.tv_latlon);
							modiflyName.setText(saveName.getText().toString());
							modiflyLocation.setText(saveLocation.getText().toString());

							createDialogLocaltion.cancel();

						}

					});

		} catch (Exception e){ }
		// name  
		setname = (EditText) showDialogLocaltion.findViewById(R.id.name_OnMap);
		// location  
		setlocation = (EditText) showDialogLocaltion.findViewById(R.id.location_OnMap);
		setlocation.setText(locationAdress);

		createDialogLocaltion.requestWindowFeature(Window.FEATURE_NO_TITLE);
		createDialogLocaltion.setContentView(showDialogLocaltion);
		createDialogLocaltion.show();

		KeySearch = (EditText) showDialogLocaltion.findViewById(R.id.search_txt);// ช่องค้นหา
		listView = (ListView)showDialogLocaltion.findViewById(R.id.Search_Showlistview); // แสดงใน listivew

		//click lisview
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				HashMap<String, String> hashMap = (HashMap<String, String>)listView.getItemAtPosition(position);
 
					//name edit last ---		
					String nameResult = hashMap.get("LocationName");	
					String locationResult = hashMap.get("Location");
					
					EditText editname = (EditText)showDialogLocaltion.findViewById(R.id.name_OnMap);
					editname.setText(nameResult);
					EditText editlocation = (EditText)showDialogLocaltion.findViewById(R.id.location_OnMap);
					editlocation.setText(getLocationMap);
			}
		});
		
		
		// Adding items to listview ---
				adapter = new ArrayAdapter<String>(this,
						R.layout.list_item,
						R.id.Search_Showlistview);
				listView.setAdapter(adapter);
				listView.setVisibility(View.VISIBLE);

		KeySearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,int arg3) {
				TakePhotoCheckin.this.adapter.getFilter().filter(cs);
				keyword = KeySearch.getText().toString();
				new SearchLocation().execute();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0){}
		});

	}// end

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(TakePhotoCheckin.PHOTO_TAKEN, _taken);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.i("Tag", "onRestoreInstanceState()");
		if (savedInstanceState.getBoolean(TakePhotoCheckin.PHOTO_TAKEN)) {
			onPhotoTaken();
		}
	}

	public Bitmap decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, o2);
		return bmp;
		// imgView.setImageBitmap(bitmap);

	}

	// / Method ดึงพิกัดจากรูปถ่าย
	public String[] getImageLatLon(String filename) throws Exception {

		ExifInterface exif = new ExifInterface(filename);
		String[] lt = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE).split( // ตรงนี้ไม่คืนค่าตำแหน่งมาให้ครับ
				",");
		String[] ln = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE).split(
				",");

		Double lat = Double.valueOf(lt[0].split("/")[0])
				+ Double.valueOf(lt[1].split("/")[0]) / 60
				+ Double.valueOf(lt[2].split("/")[0]) / 3600;

		Double lon = Double.valueOf(ln[0].split("/")[0])
				+ Double.valueOf(ln[1].split("/")[0]) / 60
				+ Double.valueOf(ln[2].split("/")[0]) / 3600;

		return new String[] { String.format("%.6f", lat),
				String.format("%.6f", lon) };
	}

	// Search location on db
	class SearchLocation extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpPhotoCheckIn httpPhotoCheckIn = new HttpPhotoCheckIn();
			String URL = "http://checkinphoto.com/android/checkin/search.php?keyword="+keyword;
			String result = httpPhotoCheckIn.connect(URL);
			return result;
		}

		@Override
		protected void onPostExecute(String result) { // get reseult
			// TODO Search
			Log.d("JSON",result);
			ArrayList<HashMap<String, String>> arrayListName = new ArrayList<HashMap<String, String>>();
			try {
				final JSONArray jsonarray = new JSONArray(result);
				
				for (int i = 0; i < jsonarray.length(); i++) {
					JSONObject jsonObject = new JSONObject(
							jsonarray.getString(i));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("LocationName",jsonObject.getString("name"));
					String la = jsonObject.getString("latitude"); //la
					String lo =  jsonObject.getString("longitude"); //long
					getLocationMap = la+","+lo;
				//	Log.d(">>>>", getLocationMap);
					arrayListName.add(map);
					
					 
				}

				SimpleAdapter adapter = new SimpleAdapter(
						TakePhotoCheckin.this,arrayListName,R.layout.location_table_view,
						new String[] {"LocationName"},
						new int[]{ R.id.textViewName});
				listView.setAdapter(adapter);
 
				// when clcik
 
			} catch (JSONException e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}

	// / AsyncTask Upload Image to db
	class ImageUploadTask extends AsyncTask<Bitmap, Integer, String> {
		private ProgressDialog progressDialog = new ProgressDialog(
				TakePhotoCheckin.this);
				String err = null;

		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Uploading...");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					ImageUploadTask.this.cancel(true);
				}
			});
		}

		// TODO add data to database
		@Override
		protected String doInBackground(Bitmap... arg) {

		     try {
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                arg[0].compress(CompressFormat.JPEG,75,bos);
	                byte[] data = bos.toByteArray();
	                HttpClient httpClient = new DefaultHttpClient();                
	                HttpPost postRequest = new HttpPost(PHP_URL);               
	                ByteArrayBody bab = new ByteArrayBody(data,up_name);                
	                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	                //sent value
	               
	                //แยกออก
	                String sentLocation = tv_latlon.getText().toString();
	                String[] locationMap = sentLocation.split(",");
	                for(int i=0; i < locationMap.length; i++){  
	                }
 
	                reqEntity.addPart("sentName", new StringBody(nameMap.getText().toString()));
	                reqEntity.addPart("getImage",bab);//sent image
	                reqEntity.addPart("sentUsername", new StringBody(Username));
	                reqEntity.addPart("getDetail", new StringBody(Detail.getText().toString()));
	                reqEntity.addPart("sentlatitude", new StringBody(locationMap[0]));
	                reqEntity.addPart("sentLongitude", new StringBody(locationMap[1]));
	                postRequest.setEntity(reqEntity);	   
	                
	                
	                HttpResponse response = httpClient.execute(postRequest);
	                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
	                String sResponse;
	                StringBuilder s = new StringBuilder();
	 
	                while ((sResponse = reader.readLine()) != null) {
	                    s = s.append(sResponse);
	                }
	                 
	                return s.toString().trim();
	             
	            } catch (Exception e) {
	                 
	                err="error"+e.getMessage();
	                Log.e(e.getClass().getName(), e.getMessage());
	                 
	                return e.getMessage();
	            }
 
		}

		@Override
		protected void onPostExecute(String res) {
			if (progressDialog.isShowing())
				progressDialog.dismiss();
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
					TakePhotoCheckin.this);
			
				//alertbox.setTitle("Information");
				alertbox.setNeutralButton("close",new AlertDialog.OnClickListener(){
				public void onClick(DialogInterface dialog, int arg1) {
					finish();		 
					Toast.makeText(TakePhotoCheckin.this,"โพสภาพของคุณเรียบร้อยแล้ว..", Toast.LENGTH_SHORT).show();
					//dialog.cancel();
				}
				});
								
				
			//alertbox.setNeutralButton("ok", null);
 
			if (err != null) {
				alertbox.setMessage("เกิดข้อผิดพลาด!!!\n" + res);
			} else {
				alertbox.setMessage(res);
			}
			alertbox.show();
			
			//EXIT
			
		 
		}
	} 

}// ///// end Class /////////////