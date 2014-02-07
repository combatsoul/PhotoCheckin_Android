package com.example.photocheckin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TakePhotoCheckin <export_main_activity> extends Activity implements View.OnClickListener{
	protected Button btn_upload;
	protected ImageView imageview,btn_image;
	protected TextView _field;
	protected String _path_pic=null;
	protected boolean _taken;
	protected static final String PHOTO_TAKEN	= "photo_taken";
	String up_name;
	Bitmap bitmap=null;
	String lat=null,lon=null;
	TextView tv_latlon;
	
	final String PHP_URL="http://203.157.10.11/droid/uploadimg.php"; // Change to Your Host.
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index_takephotocheckin);
       
        tv_latlon = (TextView)findViewById(R.id.tv_latlon);
        imageview = ( ImageView ) findViewById( R.id.image );
        _field = ( TextView ) findViewById( R.id.field );
        
        btn_image = ( ImageView ) findViewById( R.id.photo_imgbtn);
        btn_image.setOnClickListener( this);
        
        
        btn_upload = ( Button ) findViewById( R.id.save_btn);
        btn_upload.setOnClickListener(this);
        
      
        File file  = new File(Environment.getExternalStorageDirectory() + "/myPhoto/");
        if (!file.exists()) {
            try{
            	file.mkdirs();
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
    }/////   end onCreate  //////
    
    @Override
	public void onClick(View v) {
    	if(v.getId()==R.id.photo_imgbtn ||v.getId()==R.id.image){
    		
    		startCameraActivity();
    	}	
    	if(v.getId()==R.id.save_btn){
    		if(bitmap!=null){
    			new ImageUploadTask().execute(bitmap);
    		}else{
    			Toast.makeText(TakePhotoCheckin.this, "โปรดกรอกข้อมูลให้นครบถ้วน",Toast.LENGTH_SHORT).show();
    		}
    	}
	}
    
	protected void startCameraActivity(){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String picTime = sdf.format(new Date());
			_path_pic = Environment.getExternalStorageDirectory() + "/myPhoto/"+picTime+".jpg";
			up_name = picTime+".jpg";
    	 	File file = new File(_path_pic);
    	    Uri outputFileUri = Uri.fromFile( file );    	    	
    	    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
    	    intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );    	    	
    	    startActivityForResult( intent, 0 );
    }
    
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
    	//Log.i( "resultcode", "resultCode: " + resultCode );
    	switch( resultCode )
    	{
    		case 0:
    			Log.i( "Tag", "User cancelled" );
    			break;
    			
    		case -1:    	
    			onPhotoTaken();
    			break;
    	}
    }
   
	
    
    
    
    protected void onPhotoTaken(){
    	
    	_taken = true;
    	
    	//BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 4;    	
    	//Bitmap bitmap = BitmapFactory.decodeFile(_path_pic, options );
    	
    	try{
    		String[] ll=getImageLatLon(_path_pic);
    		lat=ll[0]; lon=ll[1];
    		tv_latlon.setText(lat+","+lon);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		tv_latlon.setText("Err on get location.");
    		lat=null;lon=null;
    	}
    	
        bitmap = decodeFile(_path_pic);
    	
    	imageview.setImageBitmap(bitmap);
    	
    	_field.setVisibility( View.GONE );
    }
    
    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        outState.putBoolean( TakePhotoCheckin.PHOTO_TAKEN, _taken );
    }
    @Override 
    protected void onRestoreInstanceState( Bundle savedInstanceState){
        Log.i( "Tag", "onRestoreInstanceState()");
        if( savedInstanceState.getBoolean( TakePhotoCheckin.PHOTO_TAKEN ) ) {
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
		//imgView.setImageBitmap(bitmap);

	}
    
    /// Method  ดึงพิกัดจากรูปถ่าย
    public String[] getImageLatLon(String filename) throws Exception{
    	
    	ExifInterface exif = new ExifInterface(filename);
    	String[] lt = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE).split(",");
    	String[] ln = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE).split(",");
    	
    	Double lat = Double.valueOf(lt[0].split("/")[0])
    			+Double.valueOf(lt[1].split("/")[0])/60
    			+Double.valueOf(lt[2].split("/")[0])/3600;
    	
    	Double lon = Double.valueOf(ln[0].split("/")[0])
    			+Double.valueOf(ln[1].split("/")[0])/60
    			+Double.valueOf(ln[2].split("/")[0])/3600;
    	
    	return new String[]{String.format("%.6f",lat),String.format("%.6f",lon)};
    }
    
    
    ///  AsyncTask  Upload Image
	class ImageUploadTask extends AsyncTask <Bitmap, Integer, String>{
		private ProgressDialog progressDialog = new ProgressDialog(TakePhotoCheckin.this);
		String err=null;
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
		@Override
		protected String doInBackground(Bitmap... arg) {
			
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				arg[0].compress(CompressFormat.JPEG, 75, bos);
				byte[] data = bos.toByteArray();
				HttpClient httpClient = new DefaultHttpClient();				
				HttpPost postRequest = new HttpPost(PHP_URL);				
				ByteArrayBody bab = new ByteArrayBody(data,up_name);				
				MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				reqEntity.addPart("uploadedfile", bab);
				
				if(lat!=null && lon!=null){
					reqEntity.addPart("lat", new StringBody(lat));
					reqEntity.addPart("lon", new StringBody(lon));
				}
			
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
				if (progressDialog.isShowing())progressDialog.dismiss();
				AlertDialog.Builder alertbox = new AlertDialog.Builder(TakePhotoCheckin.this);
				alertbox.setTitle("Information");
				alertbox.setNeutralButton("Ok",null);				
				if(err!=null){
					 alertbox.setMessage("เกิดข้อผิดพลาด!!!\n"+res);	
				}else{					 		          
					 alertbox.setMessage(res);		          
				}
				 alertbox.show();
		}
	}//// end task///
    
   
}/////// end Class /////////////