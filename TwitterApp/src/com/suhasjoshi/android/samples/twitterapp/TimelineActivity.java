package com.suhasjoshi.android.samples.twitterapp;


import com.suhasjoshi.android.samples.twitterapp.fragments.HomeTimelineFragment;
import com.suhasjoshi.android.samples.twitterapp.fragments.MentionsFragment;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TimelineActivity extends FragmentActivity  implements TabListener, TweetImageClickListener{
 
	public static final int TWEET_REQUEST = 1;
	public static String HOME_TIMELINE_FRAGMENT = "HomeTimelineFragment";
	public static String MENTIONS_FRAGMENT = "MentionsFragment";
	private HomeTimelineFragment homeTimelineFragment = null;
	private MentionsFragment mentionsFragment = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupNavigationTabs();
       

    }   

    private void setupNavigationTabs() {
		
    	homeTimelineFragment = new HomeTimelineFragment();
    	mentionsFragment = new MentionsFragment();
    	homeTimelineFragment.addImageClickListener(this);
    	mentionsFragment.addImageClickListener(this);
    	
    	ActionBar actionBar = getActionBar();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	actionBar.setDisplayShowTitleEnabled(true);
    	Tab tabHome = actionBar.newTab()
    			.setText("Home").setTag(HOME_TIMELINE_FRAGMENT).setIcon(R.drawable.ic_action_home).setTabListener(this);
    	Tab tabMentions = actionBar.newTab()
    			.setText("Mentions").setTag(MENTIONS_FRAGMENT).setIcon(R.drawable.ic_action_at).setTabListener(this);
    	actionBar.addTab(tabHome);
    	actionBar.addTab(tabMentions);
    	actionBar.selectTab(tabHome);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	 /* TweetsListFragment fragment = (TweetsListFragment)getSupportFragmentManager().findFragmentByTag(HOME_TIMELINE_FRAGMENT);
    	  //TweetsListFragment fragment = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
		  Toast.makeText(this, fragment.toString(), Toast.LENGTH_LONG).show();
    	  fragment.onActivityResult(requestCode, resultCode, data);	*/
    	  homeTimelineFragment.onActivityResult(requestCode, resultCode, data);
    	  	


    } 
	public void onComposePress(MenuItem mi) {
		
		//Toast.makeText(this, "Compose launched", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this,ComposeTweetActivity.class);		
		startActivityForResult(i,TWEET_REQUEST);		
		
	} 
	
	public void onRefreshPress(MenuItem mi){
		
		Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
		refresh();
		
	}
	
	public void onProfilePress(MenuItem mi) {
		//Load Profile
		Intent i = new Intent(this,ProfileActivity.class);
		i.putExtra("screenName", TwitterClientApp.getUserCredentials().getScreenName());
		startActivity(i);
	}
	
	private void refresh() {
		
		homeTimelineFragment.refresh();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();

		if(tab.getTag().equals(HOME_TIMELINE_FRAGMENT)) {
			
			fts.replace(R.id.frame_layout_container, homeTimelineFragment);
			
		}else {
			
			fts.replace(R.id.frame_layout_container, mentionsFragment);
		}
		
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imageClicked(String screenName) {
		
		//Toast.makeText(this, "image clicked : " + screenName, Toast.LENGTH_LONG).show();
		Intent i = new Intent(this,ProfileActivity.class);
		i.putExtra("screenName", screenName);
		startActivity(i);
	}
    
}

