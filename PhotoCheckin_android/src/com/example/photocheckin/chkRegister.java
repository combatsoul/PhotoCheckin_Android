package com.example.photocheckin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


        public class chkRegister extends Activity {

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        requestWindowFeature(Window.FEATURE_NO_TITLE);
                        setContentView(R.layout.result_register);
                        
                        //splash windows
                        Handler myHandler = new Handler();
                        myHandler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                        
                                        finish();
                                        Intent goMain = new Intent(getApplicationContext(),LoginForm.class);
                                        startActivity(goMain);
                                }
                        }, 3000);
                        
                }

        }

        
