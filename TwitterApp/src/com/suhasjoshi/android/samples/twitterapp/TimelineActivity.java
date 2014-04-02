package com.suhasjoshi.android.samples.twitterapp;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.suhasjoshi.android.samples.twitterapp.models.Tweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineActivity extends Activity {
 
	
	public static final int TWEET_REQUEST = 1;
	private TweetAdapter adapter = null;
	private ListView lvTweets = null;
	

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        lvTweets = (ListView)findViewById(R.id.lvTweets);
        
        adapter = new TweetAdapter(getBaseContext(), tweets) ; 
        lvTweets.setAdapter(adapter);
        JsonHttpResponseHandler localJsonResponseHandler = new LocalHttpResponseHandler(adapter,this);
        
        
        TwitterClientApp.getClient().getHomeTimeline(localJsonResponseHandler);
        
        //ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {

         	long maxId = Long.parseLong(((Tweet)adapter.getItem(adapter.getCount() -1)).getTweetId()) + 1;
        	TwitterClientApp.getClient().loadData(maxId, TwitterClient.VALUE_COUNT);
        }
        });
    }   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	    	
      if (resultCode == RESULT_OK ) {
    	  //Refresh the tweets.
    	  Toast.makeText(this, "ResultOk", Toast.LENGTH_SHORT).show();
    	  if(data != null) {
    		  Tweet tweet = (Tweet)data.getSerializableExtra(ComposeTweetActivity.DATA_TWEET);
    		  adapter.insert(tweet, 0);
    	  }
      }
    } 
	public void onComposePress(MenuItem mi) {
		
		Toast.makeText(this, "Compose launched", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this,ComposeTweetActivity.class);		
		startActivityForResult(i,TWEET_REQUEST);		
		
	} 
	
	public void onRefreshPress(MenuItem mi){
		
		Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
		//TwitterClientApp.getClient().loadData(0, TwitterClient.VALUE_COUNT);
		refresh();
		
	}
	
	private void refresh() {
		this.adapter.clear();
		TwitterClientApp.getClient().loadData(0, TwitterClient.VALUE_COUNT);
		
	}
    
}

class LocalHttpResponseHandler extends JsonHttpResponseHandler {
	
	private TweetAdapter adapter = null;
	private Context context = null;

	
	public LocalHttpResponseHandler(TweetAdapter adapter, Context context) {
		
		this.adapter = adapter;
		this.context = context;
	}
	
	@Override 
	public void onSuccess(JSONArray jsonTweets) {
		ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
		adapter.addAll(tweets);
		Log.d("DEBUG",jsonTweets.toString());		
       	}
	
	@Override
	public void onSuccess(JSONObject jsonObject) {
		Log.d("DEBUG",jsonObject.toString());
	}
	
	@Override
	public void onFailure(java.lang.Throwable error, org.json.JSONObject jsonError){
		Log.d("ERROR", error.toString() + " JSON Error : " + jsonError);
		Toast.makeText(context, "Communications Error", Toast.LENGTH_LONG).show();
	}	

	@Override
    public void onFailure(java.lang.Throwable error, org.json.JSONArray jsonError){
		Log.d("ERROR", error.toString() + " JSON Error : " + jsonError);
		Toast.makeText(context, "Communications Error" , Toast.LENGTH_LONG).show();
    }
	

}
