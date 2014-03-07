package com.example.photocheckin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityRoomla extends Activity implements View.OnClickListener {
	
	private static final int SELECT_PHOTO = 1;
	private LinearLayout showDialogView;
    private TextView messageText;
    private Button uploadButton, btnselectpic;
    private ImageView imageview;
    private ImageView imageHeader;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    private ProgressDialog pDialog;
    private String upLoadServerUri = null;
    private String imagepath=null;
	private String response = "";
	private Bitmap imageselected;
	private String activityid;
	private String activityname;
	private String activitydetail;
	private String activitylocation;
	private String activitystartdate;
	private String activityenddate;
	private Dialog createDialog;
	private Handler handler;
	private ImageView image;
	private Bitmap bitheader;
	
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);
		image = (ImageView) findViewById(R.id.imageHeader);
		image.setOnClickListener(this); 
		
        upLoadServerUri = "http://www.checkinphoto.com/android/uploadheaderpicture/uploadHeader.php";
        
        Intent intent = getIntent();

		//hiding default app icon
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbar));
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		imageHeader = (ImageView)findViewById(R.id.imageView_pic);
		
		activityid = intent.getStringExtra("id");
		activityname = intent.getStringExtra("activityname");
		activitydetail = intent.getStringExtra("activitydetail");
		activitylocation = intent.getStringExtra("");
		activitystartdate = intent.getStringExtra("startdate");
		activityenddate = intent.getStringExtra("enddate");
		
		TextView txtname = (TextView) findViewById(R.id.result_name);
		TextView txtdetail = (TextView) findViewById(R.id.result_detail);
		TextView txtlocation = (TextView) findViewById(R.id.result_location);
		TextView txtstartdate = (TextView) findViewById(R.id.result_start_activity);
		TextView txtenddate = (TextView) findViewById(R.id.result_edd_activity);
		
		txtname.setText(activityname);
		txtdetail.setText(activitydetail);
		txtlocation.setText(activitylocation);
		txtstartdate.setText(activitystartdate);
		txtenddate.setText(activityenddate);
//		new Listactivitys().execute();
		selectpicture();
		
//		ImageView imageViewProfile = (ImageView) findViewById(R.id.imageHeader);
//		String path = "http://www.checkinphoto.com/android/uploadheaderpicture/upload/"+activityid.toString()+"/display.jpg";
//		ImageLoader imageLoader = new ImageLoader(
//				path, imageViewProfile);// ภาพ
	}

	//show Header Picture popup. It is popup for select activity header.
	private void showUploadPicture(View v) {
		createDialog = new Dialog(ActivityRoomla.this);
		// Inflate the root layout
		showDialogView = (LinearLayout) getLayoutInflater().inflate(
				R.layout.upload_picture_activity, null);
		// Setup TimePicker
		
        uploadButton = (Button) showDialogView.findViewById(R.id.uploadButton);
        messageText  = (TextView)showDialogView.findViewById(R.id.messageText);
        btnselectpic = (Button) showDialogView.findViewById(R.id.button_selectpic);
        imageview = (ImageView)showDialogView.findViewById(R.id.imageView_pic);
        
        btnselectpic.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

		((ImageView) showDialogView.findViewById(R.id.imageCrossUpload))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						createDialog.cancel();

					}
				});

		// No title on the dialog window
		createDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		createDialog.setContentView(showDialogView);
		//fix size dialog
		createDialog.getWindow().setLayout(450, 500);
		// Display the dialog
		createDialog.show();
	}

	public void selectpicture() {
		//		int loader = R.drawable.image_header;
//		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
//		imgLoader.clearCache();
//		imgLoader.DisplayImage(image_url, loader, image);
		try {
//			image = (ImageView) findViewById(R.id.imageHeader);
			String image_url = "http://www.checkinphoto.com/android/uploadheaderpicture/uploads/"+activityid.toString()+"/display.jpg";
			InputStream imageStream;
			imageStream = new URL(image_url).openStream();
			bitheader=BitmapFactory.decodeStream(imageStream);
			image.refreshDrawableState();
			image.setImageBitmap(bitheader);
		} catch (FileNotFoundException e) {
			Log.d(e.toString(), response);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.imageHeader) {
			showUploadPicture(v);
		}
		
		if(v==btnselectpic)
		{            
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, SELECT_PHOTO);
		}
		else if (v==uploadButton) {
			if(imagepath==null){
				
			}else{
	            new uploadfile().execute(); 
			}
		}
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    
    	if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath(); 
    		try {
	            Uri selectedImageUri = data.getData();
	        	InputStream imageStream;
				imageStream = getContentResolver().openInputStream(selectedImageUri);
	            imagepath = selectedImageUri.toString();
				imageselected=BitmapFactory.decodeStream(imageStream);
	            imageview.setImageBitmap(imageselected);
	            messageText.setText("Uploading file path:" +imagepath);
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
	    	
	    }
    }
	 public String getPath(Uri uri) {
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	}
    

	 
	class uploadfile extends AsyncTask<String, String, String> {

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(ActivityRoomla.this);
		pDialog.setTitle("Connect to Server..");
		pDialog.setMessage("Loading ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

		protected String doInBackground(String... args) {
			Bitmap bitmap = imageselected;
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
			byte [] byte_arr = stream.toByteArray();
			String image_str = Base64.encodeBytes(byte_arr);

			// Building Parameters
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.checkinphoto.com/android/uploadheaderpicture/uploadHeader.php");

			try {
				// Add your data
				String aa = activityid.toString();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("activityid", activityid.toString()));
				nameValuePairs.add(new BasicNameValuePair("image",image_str));
				nameValuePairs.add(new BasicNameValuePair("activityname","pic_"+activityname));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse execute = httpclient.execute(httppost);
				InputStream content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));

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
			super.onPostExecute(result);
			pDialog.dismiss();
			selectpicture();
		}
		
	}
    	 
}
