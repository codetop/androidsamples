package com.suhasjoshi.android.samples.twitterapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.suhasjoshi.android.samples.twitterapp.models.User;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
   
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "dIg3ikMyNdv1EJcqf5EHcA";       // Change this
    public static final String REST_CONSUMER_SECRET = "mrDy1jZEeoXyVCGZvaG25r0Zr3Ko3sNQ7XeTwDcM"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://twitterapp"; // Change this (here and in manifest)
    
    //Home Timeline 
    public static final String URL_HOME_TIMELINE = "statuses/home_timeline.json";
    public static final String REQ_PARAM_MAX_ID = "max_id";    
    public static final String REQ_PARAM_COUNT = "count";
    public static final int VALUE_COUNT= 50;
    
    //Tweet post
    public static final String URL_POST_STATUS_UPDATE = "statuses/update.json";
    public static final String REQ_PARAM_STATUS = "status";
    
    //Verify Credentials 
    public static final String URL_VERIFY_CREDENTIALS = "account/verify_credentials.json";
    
    private AsyncHttpResponseHandler handler = null; 
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
     
    public void getHomeTimeline(AsyncHttpResponseHandler handler) { 
    	
    	this.handler = handler;
    	loadData(0,VALUE_COUNT);

    }
    
    public void loadData(long maxId, int count) {
    	
    	RequestParams requestParams = new RequestParams();
    	requestParams.put(REQ_PARAM_COUNT, ""+VALUE_COUNT);
    	if(maxId != 0) {
    		requestParams.put(REQ_PARAM_MAX_ID, ""+maxId);
    	}
    	client.get(getApiUrl(URL_HOME_TIMELINE), requestParams, handler);  
    }
    
    public void tweet(String tweetString) { 
    	
    	RequestParams requestParams = new RequestParams();
    	requestParams.put(REQ_PARAM_STATUS, tweetString);
    	client.post(getApiUrl(URL_POST_STATUS_UPDATE),requestParams,new JsonHttpResponseHandler(){
    		@Override
    		public void onSuccess(JSONObject jsonObject) {
    			Log.d("DEBUG",jsonObject.toString());
    		}
    		@Override
    		public void onSuccess(JSONArray jsonArray) {
    			Log.d("DEBUG",jsonArray.toString());
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
    	}); 
    }
    
    public void verifyCredentials(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl(URL_VERIFY_CREDENTIALS);
        client.get(apiUrl, handler);
    }


     
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}