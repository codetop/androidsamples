package com.suhasjoshi.android.samples.twitterapp.fragments;

import java.util.ArrayList;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.suhasjoshi.android.samples.twitterapp.EndlessScrollListener;
import com.suhasjoshi.android.samples.twitterapp.R;
import com.suhasjoshi.android.samples.twitterapp.TweetAdapter;
import com.suhasjoshi.android.samples.twitterapp.TweetImageClickListener;
import com.suhasjoshi.android.samples.twitterapp.TwitterClient;
import com.suhasjoshi.android.samples.twitterapp.TwitterClientApp;
import com.suhasjoshi.android.samples.twitterapp.TwitterHttpReponseHandler;
import com.suhasjoshi.android.samples.twitterapp.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public abstract class TweetsListFragment extends Fragment implements TweetImageClickListener{
	
	TweetAdapter adapter = null;
	ListView lvTweets = null;
	JsonHttpResponseHandler localJsonResponseHandler  = null;
	private TweetImageClickListener tweetImageClickListener;
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		
		View view = inf.inflate(R.layout.fragments_tweets_list,parent,false);
		lvTweets = (ListView)view.findViewById(R.id.lvTweets);
		   
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetAdapter(view.getContext(), tweets);
		adapter.addAll(tweets);
		adapter.addImageClickListener(this);
		lvTweets.setAdapter(adapter);
		
        localJsonResponseHandler = new TwitterHttpReponseHandler(getAdapter(),getActivity());                
        TwitterClientApp.getClient().getTimeline(getUrl(),getScreenName(),localJsonResponseHandler);
        //ListView lvTweets = (ListView)getActivity().findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {


        	loadData(getUrl(),getScreenName(),page,totalItemsCount);
        }
        });
        
		return view;
	}
	
	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
	}

	public TweetAdapter getAdapter() {
		return adapter;
	}
	
	public  void refresh() {
		getAdapter().clear();
		loadData(getUrl(),getScreenName(),0, TwitterClient.VALUE_COUNT);
	}
	
	public  void loadData(String url,String screenName, int page,int totalItemsCount) {
		long maxId = 0;
		if(getAdapter().getCount()>0){
			maxId = Long.parseLong(getAdapter().getItem(getAdapter().getCount() -1).getTweetId()) + 1;
		}
     	 
    	TwitterClientApp.getClient().loadData(url,screenName,maxId, TwitterClient.VALUE_COUNT,localJsonResponseHandler);
	}
	
	public void imageClicked(String screenName) {
		if(tweetImageClickListener !=null) {
			tweetImageClickListener.imageClicked(screenName);
		}
	}
	
	public void addImageClickListener(TweetImageClickListener tweetImageClickListener){
		this.tweetImageClickListener = tweetImageClickListener;
	}
	
	public String getScreenName() {
		return null;
	}
	
	public abstract String getUrl();

}
