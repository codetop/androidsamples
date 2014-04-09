package com.suhasjoshi.android.samples.twitterapp;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.suhasjoshi.android.samples.twitterapp.fragments.UserTimelineFragment;
import com.suhasjoshi.android.samples.twitterapp.models.User;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends FragmentActivity  {
	
	public static final String USER_TIMELINE_FRAGMENT ="UserTimelineFragment";
	//private String screenName ;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String screenName = getIntent().getStringExtra("screenName");		
		loadProfileInfo(screenName);
		loadFragment(screenName);
		
	}

	private void loadFragment(String screenName) {
		
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		UserTimelineFragment userTimelineFragment = new UserTimelineFragment();		
		Bundle bundle = new Bundle();
		bundle.putString("screenName", screenName);
		userTimelineFragment.setArguments(bundle);		
		fts.replace(R.id.profile_layout_container, userTimelineFragment);		
		fts.commit();
	}
	private void loadProfileInfo(String screenName) {
		
	   TwitterClientApp.getClient().getUserInfo(screenName, new JsonHttpResponseHandler() {

    		@Override
    		public void onSuccess(JSONObject jsonObject) {
    			Log.d("DEBUG","GET USER INFO : "+ jsonObject.toString());

    		}
    		@Override
    		public void onSuccess(JSONArray jsonArray) {
    			Log.d("DEBUG","GET USER INFO : "+ jsonArray.toString());
    		    User user = null;
				try {
					user = User.fromJson(jsonArray.getJSONObject(0));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		    getActionBar().setTitle("@"+user.getScreenName());
    			
    			TextView tvName = (TextView)findViewById(R.id.tvName);
    			TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
    			TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
    			TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
    			ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
    			
    			tvName.setText(user.getName());
    			tvTagline.setText(user.getTagLine());
    			tvFollowers.setText(""+user.getFollowersCount() + " Followers");
    			tvFollowing.setText(""+user.getFriendsCount() + " Following");
    			
    			ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
    			
    			
    		}
    		@Override
            public void onFailure(Throwable arg0, JSONObject arg1) {
    			
    			Toast.makeText(getBaseContext(), "Error :" +arg1.toString(), Toast.LENGTH_LONG).show();
    		}
    		
    		@Override
            public void onFailure(Throwable arg0, JSONArray arg1) {
    			
    			Toast.makeText(getBaseContext(), "Error :" +arg1.toString(), Toast.LENGTH_LONG).show();
    		}

    		@Override
    		public void onFailure(Throwable e) {
    		    e.printStackTrace();
    		}

    		});

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	

}
