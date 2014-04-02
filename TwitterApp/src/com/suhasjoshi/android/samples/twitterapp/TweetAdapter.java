package com.suhasjoshi.android.samples.twitterapp;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.suhasjoshi.android.samples.twitterapp.models.Tweet;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	public TweetAdapter(Context context, 
			List<Tweet> tweets) {
		super(context,0, tweets);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if(view == null) {
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		
		String formattedTime = getRelativeTime(tweet.getCreatedAt().getTime());
		TextView nameView = (TextView)view.findViewById(R.id.tvName);	
		String formattedName = "<b>" + tweet.getUser().getName()  +"</b>" + " <small><font color ='#7777777'>@" +
				tweet.getUser().getScreenName() + "</font></small>" + " <small><font color ='#7777777'><bold>" +
				formattedTime + "</bold></font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		return view;
	}
	
	private String getRelativeTime(long theEventInMillis) {
		
	 
		return DateUtils.getRelativeTimeSpanString (theEventInMillis, (System.currentTimeMillis()), 
				DateUtils.MINUTE_IN_MILLIS, 
				DateUtils.FORMAT_NO_NOON).toString();	
		
	}
}
