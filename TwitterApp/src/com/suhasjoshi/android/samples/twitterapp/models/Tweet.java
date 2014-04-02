package com.suhasjoshi.android.samples.twitterapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tweet extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1223983064060846445L;


	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
    private User user;
    private Date createdAt;
    private String tweetId; 

    public Tweet(JSONObject json) {
    	super(json);
    }
    public Tweet() {
		// TODO Auto-generated constructor stub
	}
	public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }
    
	public void setBody(String body) {
		this.body = body;
	}

    public long getId() {
        return uid;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }
    
    public Date getCreatedAt() {
    	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

    public String getTweetId() { 
    	return tweetId; 
    }

    //TODO Clean this up. Dont use deprecated methods, replace them with new calls. Remove Log. 
    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet(jsonObject);

        	tweet.body = tweet.getString("text");
        	tweet.uid = tweet.getLong("id");
        	tweet.favorited = tweet.getBoolean("favorited");
        	tweet.retweeted = tweet.getBoolean("retweeted");
        	tweet.tweetId = tweet.getString("id"); 
        	Date date = null;

        		try {
					date = new SimpleDateFormat("EE MMMM dd HH:mm:ss z yyyy").parse(tweet.getString("created_at"));
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            Log.d("DEBUG",date.toGMTString());
        	tweet.createdAt = date;
     try {  	
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
	public void setUser(User userCredentials) {
		this.user = userCredentials;
		
	}
}