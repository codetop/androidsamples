package com.suhasjoshi.android.samples.twitterapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import com.suhasjoshi.android.samples.twitterapp.ProfileActivity;
import com.suhasjoshi.android.samples.twitterapp.TwitterClient;

public class UserTimelineFragment extends TweetsListFragment {

 private String screenName = null;
	
  @Override
  public android.view.View onCreateView(android.view.LayoutInflater inf, android.view.ViewGroup parent, Bundle savedInstanceState) {
		Bundle bundle = this.getArguments();
		this.screenName = bundle.getString("screenName");
		//this.screenName = ((ProfileActivity)getActivity()).getScreenName();
		return super.onCreateView(inf, parent, savedInstanceState);
  }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);


	}	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}
	
	@Override
	public String getUrl() {

		return TwitterClient.URL_USER_TIMELINE;
	}
	
	public String getScreenName() {
		return screenName;
	}

}
