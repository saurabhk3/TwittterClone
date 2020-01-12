package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class TweetActivity extends AppCompatActivity {

    EditText tweetText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        tweetText =(EditText) findViewById(R.id.tweetText);
        tweetText.setText("");
    }
    public void tweet(View view){
        ParseObject object = new ParseObject("Tweet");
        String tweet = tweetText.getText().toString();
        if(!tweet.equals("") ){
            object.put("tweet",tweet);
            object.put("username",ParseUser.getCurrentUser().getUsername());
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(TweetActivity.this,"Tweet Successful",Toast.LENGTH_SHORT).show();
                    }else{
                        e.printStackTrace();
                    }
                }
            });

        }else{
            Toast.makeText(this,"Failed to tweet",Toast.LENGTH_SHORT).show();
        }

    }

}
