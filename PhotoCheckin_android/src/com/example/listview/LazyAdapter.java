package com.example.listview;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.photocheckin.ActivityRoomla;
import com.example.photocheckin.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.view.View.OnClickListener;

public class LazyAdapter extends BaseAdapter {

	private String activity_id;
	private Activity activity;
	private Context context;
	private static final int IO_BUFFER_SIZE = 4 * 1024;
	private ArrayList<HashMap<String, String>> data;
//	HashMap<String, String> resultp = new HashMap<String, String>();
//	private static LayoutInflater inflater = null;
	//public ImageLoader imageLoader;
	HashMap<String, String> song = new HashMap<String, String>();

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> arraylist) {
		data = arraylist;
		context = a;
		this.activity = a;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		TextView title;
		TextView detail;
		TextView time;
		
		
		song = data.get(position);
//		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		convertView = inflater.inflate(R.layout.list_box, null);
		title = (TextView) convertView.findViewById(R.id.Title);
		detail = (TextView) convertView.findViewById(R.id.Detail);
		time = (TextView) convertView.findViewById(R.id.Time);
		
		TextView addactivity = (TextView) convertView.findViewById(R.id.Add_Activity);
		
		ImageView ImagePost = (ImageView) convertView
				.findViewById(R.id.ImagePost);
		// ColImageProfile
		ImageView imageDisplay = (ImageView) convertView
				.findViewById(R.id.imagePrifile);
		// imageDisplay.getLayoutParams().width = 60;
		// imageDisplay.getLayoutParams().height = 60;
		imageDisplay.setScaleType(ImageView.ScaleType.CENTER_CROP);

		try {
			imageDisplay.setImageBitmap(loadBitmap("http://checkinphoto.com/images/android/user/admin/display.jpg"));
		} catch (Exception e) {
			// When Error
			imageDisplay
					.setImageResource(android.R.drawable.ic_menu_report_image);
		}
		imageDisplay.setClickable(false);
		ImagePost.setClickable(false);
		
		title.setText(song.get(WallPage.TAG_ACTIVITYNAME));
		detail.setText(song.get(WallPage.TAG_ACTIVITYDETAIL));
		time.setText(song.get(WallPage.TAG_LASTTIME));
		
		addactivity.setClickable(true);
		
		addactivity.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Get the position
				song = data.get(position);
				Intent intent = new Intent(activity, ActivityRoomla.class);

				intent.putExtra("id", song.get(WallPage.TAG_ID));
				intent.putExtra("lasttime", song.get(WallPage.TAG_LASTTIME));
				intent.putExtra("activityname", song.get(WallPage.TAG_ACTIVITYNAME));
				intent.putExtra("activitydetail", song.get(WallPage.TAG_ACTIVITYDETAIL));
				intent.putExtra("createby", song.get(WallPage.TAG_CREATEBY));
				intent.putExtra("condition", song.get(WallPage.TAG_CONDITION));
				intent.putExtra("usertype", song.get(WallPage.TAG_USERTYPE));
				intent.putExtra("block", song.get(WallPage.TAG_BLOCK));
				intent.putExtra("registerdate", song.get(WallPage.TAG_REGISTERDATE));
				intent.putExtra("startdate", song.get(WallPage.TAG_STARTDATE));
				intent.putExtra("enddate", song.get(WallPage.TAG_ENDDATE));
				intent.putExtra("picture", song.get(WallPage.TAG_PICTURE));
				intent.putExtra("latitude", song.get(WallPage.TAG_LATITUDE));
				intent.putExtra("longitude", song.get(WallPage.TAG_LONGITUDE));
				intent.putExtra("qrcode", song.get(WallPage.TAG_QRCODE));
				
			
				activity.startActivity(intent);
				
				
				CheckTextNull();
			}

			private void CheckTextNull() {
				String activity_id = song.get(WallPage.TAG_ID);
				RequestHTTP(activity_id);
				
			}
			
			private void RequestHTTP(String activity_id){
				try{
				JSONArray jsonArray = new JSONArray(activity_id);
				JSONObject jsonObject = new JSONObject(jsonArray.getString(Integer.parseInt(activity_id)));
				Log.d("NewComment", "id"+ activity_id);
				}catch(JSONException e){
					
				}
			}
		});

		return convertView;
	}
	
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
//				android.util.Log.e(TAG_R, "Could not close stream", e);
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
}