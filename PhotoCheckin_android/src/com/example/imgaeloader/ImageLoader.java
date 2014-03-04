package com.example.imgaeloader;

import java.io.IOException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {
	
	private Thread thread;
	private Handler handler;

	@SuppressLint("HandlerLeak")
	public ImageLoader(final String imgURL,final ImageView image){
		handler = new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					image.setImageBitmap(null);
					Log.d("attrangtown","in handler fail");
					break;
					
				case 1:
					image.setImageBitmap((Bitmap) msg.obj);
					Log.d("attrangtown","in handler pass");
					break;

				}
			}
		};
		thread = new Thread(){
			@Override
			public void run() {
				Message msg = new Message();
				try {
					
					URL url = new URL(imgURL);
					Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
					msg.what = 1;
					msg.obj = bitmap;
					handler.sendMessage(msg);
				} catch (IOException e) {
					msg.what = 0;
					handler.sendMessage(msg);
				}
			}
		};
		thread.start();
	}
}
