package com.suhasjoshi.android.samples.twitterapp.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5616792709401954627L;
	private String name;
	private long uid;
	private String screenName;
	private String profileBgImageUrl;
	private String profileImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;

	public User(JSONObject jsonObject) {
		super(jsonObject);
	}
    public String getName() {
        return name;
    }

    public long getId() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBgImageUrl;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }
    
	public String getProfileImageUrl() {
		return profileImageUrl;
	}


    public static User fromJson(JSONObject json) {
        User u = new User(json);
    	u.name = u.getString("name");
    	u.uid = u.getLong("id");
    	u.screenName = u.getString("screen_name");
    	u.profileBgImageUrl = u.getString("profile_background_image_url");
    	u.numTweets = u.getInt("statuses_count");
    	u.followersCount = u.getInt("followers_count");
    	u.friendsCount = u.getInt("friends_count");
    	u.profileImageUrl = u.getString("profile_image_url");

        return u;
    }


}