package com.suhasjoshi.android.samples.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.suhasjoshi.android.samples.twitterapp.models.Tweet;

public class TwitterHttpReponseHandler  extends JsonHttpResponseHandler {
	
	   private Context context = null;
	   private TweetAdapter adapter = null;
		
		public TwitterHttpReponseHandler(TweetAdapter adapter, Context context) {
			
			this.adapter = adapter;
			this.context = context;
		}
		
		@Override 
		public void onSuccess(JSONArray jsonTweets) {
			
			ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
			Log.d("DEBUG",jsonTweets.toString());
			adapter.addAll(tweets);
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
		