package com.suhasjoshi.android.samples.twitterapp;

import java.util.Calendar;

import com.suhasjoshi.android.samples.twitterapp.models.Tweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ComposeTweetActivity extends Activity {
	
	public static final String DATA_TWEET="data_tweet";
	
	EditText etTweet = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		etTweet = (EditText)findViewById(R.id.etTweet);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}
	
	public void onTweetClick(View view) {
		
		String tweetString = etTweet.getText().toString();
		Intent data = null;
		
		if(TwitterClientApp.getUserCredentials() != null) {
			data = new Intent();
			TwitterClientApp.getClient().tweet(tweetString);
			Tweet tweet = new Tweet();
			tweet.setBody(tweetString);		
			tweet.setCreatedAt(Calendar.getInstance().getTime());
			tweet.setUser(TwitterClientApp.getUserCredentials());
			data.putExtra(DATA_TWEET, tweet);
		}
		
		setResult(RESULT_OK, data);
		this.finish();
		
	}

}
