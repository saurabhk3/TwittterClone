/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{

  EditText username ,password;

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {
    if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
      submit(view);
    }
    return false;
  }

  @Override
  public void onClick(View view) {
    if(view.getId()==R.id.imageView || view.getId()==R.id.background){
      Log.i("Imge","Clicked");
      InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    username = (EditText) findViewById(R.id.username);
    password = (EditText) findViewById(R.id.password);
    RelativeLayout layout = (RelativeLayout) findViewById(R.id.background);
    ImageView logo = (ImageView) findViewById(R.id.imageView);
    username.setOnKeyListener(this);
    password.setOnKeyListener(this);
    layout.setOnClickListener(this);
    logo.setOnClickListener(this);


    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
  public void submit(View view){
    Log.i("username:",username.getText().toString());
    Log.i("pass:",password.getText().toString());

    String user = username.getText().toString();
    String pass = password.getText().toString();

    ParseUser parseUser = new ParseUser();
    parseUser.setUsername(user);
    parseUser.setPassword(pass);

    parseUser.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null){
          Toast.makeText(MainActivity.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
        }else{
          e.printStackTrace();
        }
      }
    });

  }

}