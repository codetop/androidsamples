package com.suhasjoshi.android.samples.twitterapp.fragments;

import com.suhasjoshi.android.samples.twitterapp.ComposeTweetActivity;
import com.suhasjoshi.android.samples.twitterapp.TwitterClient;
import com.suhasjoshi.android.samples.twitterapp.models.Tweet;
import com.suhasjoshi.android.samples.twitterapp.fragments.TweetsListFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

        
	}


	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK ) {
	    	  //Refresh the tweets.
	    	  if(data != null) {
	    		  Tweet tweet = (Tweet)data.getSerializableExtra(ComposeTweetActivity.DATA_TWEET);
	    		  getAdapter().insert(tweet, 0);

	    		  
	    	  }
	      }
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return TwitterClient.URL_HOME_TIMELINE;
	}




}



	


