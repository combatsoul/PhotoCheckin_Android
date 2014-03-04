package com.example.photocheckin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

	public class Present extends Activity{
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //no title bar
	        setContentView(R.layout.index_present); //strat page
	        
	        //splash windows
            Handler myHandler = new Handler();
            myHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
            
                            finish();
                            Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
                            startActivity(goMain);
                    }
            }, 1000);
            
	        
	    }
	    
	}