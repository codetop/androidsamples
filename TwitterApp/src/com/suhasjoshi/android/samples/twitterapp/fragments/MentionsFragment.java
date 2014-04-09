package com.suhasjoshi.android.samples.twitterapp.fragments;

import com.suhasjoshi.android.samples.twitterapp.TwitterClient;
import android.content.Intent;
import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);


	}	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return TwitterClient.URL_MENTIONS_TIMELINE;
	}
}
