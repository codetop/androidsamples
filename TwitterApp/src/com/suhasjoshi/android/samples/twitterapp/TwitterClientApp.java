package com.suhasjoshi.android.samples.twitterapp;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.suhasjoshi.android.samples.twitterapp.models.User;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 * 
 *     RestClient client = RestClientApp.getRestClient();
 *     // use client to send requests to API
 *     
 */
public class TwitterClientApp extends com.activeandroid.app.Application {
	private static Context context;
    private static User userCredentials = null; 
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterClientApp.context = this;
        
        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
        		cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
    }
    
    public static TwitterClient getClient() {
    	return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterClientApp.context);
    }
    
	public static User getUserCredentials() {
		return userCredentials;
	}

	public static void setUserCredentials(User userCredentials) {
		TwitterClientApp.userCredentials = userCredentials;
	}

}