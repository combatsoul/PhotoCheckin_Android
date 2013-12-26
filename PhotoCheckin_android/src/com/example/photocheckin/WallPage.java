package com.example.photocheckin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterViewAnimator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class WallPage extends Activity implements OnItemSelectedListener {

	// title icon
	public String[] navMenu = { "Checkin", "CreateActivity", "Profile" };
	// meni icon
	int[] imag = new int[] { R.drawable.ic_launcher,
			R.drawable.ic_plusone_medium_off_client,
			R.drawable.mini_checkin_icon };

	// images for test
	Integer[] arrImg = { R.drawable.test1, R.drawable.test2, R.drawable.test3,
			R.drawable.test4, R.drawable.test5, R.drawable.test6, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.index_wallpage);

		// Each row in the list stores country name, currency and flag
		List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 3; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("text", "" + navMenu[i]);
			hm.put("icon", Integer.toString(imag[i]));
			aList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { "icon", "text" };
		// Ids of views in listview_layout
		int[] to = { R.id.checkin_img, R.id.checkin_textbtn };
		// set defluat
		Spinner spn = (Spinner) findViewById(R.id.spinner1); // when clickr
		spn.setOnItemSelectedListener(this);
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
				R.layout.spinnerlist, from, to);
		spn.setAdapter(adapter);
	}
		//

//		// detail box list view
//		final ListView lstViewdetail = (ListView) findViewById(R.id.listViewDetail);
//		lstViewdetail.setAdapter(new ImageAdapter(this));
//
//		lstViewdetail.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View v,
//					int position, long id) {
//				Toast.makeText(
//						WallPage.this,
//						"Your selected position = "
//								+ getResources().getResourceName(
//										arrImg[position]), Toast.LENGTH_SHORT)
//						.show();
//			}
//		});
//	}

//	// class img
//	public class ImageAdapter extends BaseAdapter {
//		private Context context;
//
//		public ImageAdapter(Context c) {
//			context = c;
//		}
//
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return arrImg.length;
//		}
//
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			ImageView imageView;
//			// imageView
//			if (convertView == null) {
//				imageView = new ImageView(context);
//				imageView.setLayoutParams(new ListView.LayoutParams(110, 110));
//				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//				imageView.setPadding(2, 2, 2, 2);
//			} else {
//				imageView = (ImageView) convertView;
//			}
//			imageView.setImageResource(arrImg[position]);
//			return imageView;
//		}
//	}

	// end
	
	
	
	//add border listview for connetn and picture
	
	
	

	public void onNothingSelected(AdapterView<?> age0) {
		// Toast.makeText(WallPage.this,
		// "register complete",Toast.LENGTH_SHORT).show();
	}

	public boolean onCreateOptionMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.intro_main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

}