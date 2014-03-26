package com.suhasjoshi.android.samples.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	
	
	private String baseUrl = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&q=" ;
	private EditText etQuery;
	private Button btSearch;
	private GridView gvResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;
	private Settings settings = new Settings(); 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				
              Intent i = new Intent(getApplicationContext(),ImageDisplayActivity.class);
			  ImageResult imageResult = imageResults.get(position);
			  i.putExtra(Constants.KEY_URL, imageResult.getFullUrl());
			  startActivity(i);
			}
		});
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
	            loadImages(page);
	            

	        }
	        });
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		//getMenuInflater().inflate(R.menu.options_menu,menu);
		return true;
	}

	public void onSettingsPress(MenuItem mi) {
		
		//Toast.makeText(this, "Settings launched", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this,SettingsActivity.class);		
		i.putExtra(Constants.DATA_SETTINGS, settings);
		startActivityForResult(i,Constants.SETTINGS_REQUEST);		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  
	  if (resultCode == RESULT_OK && requestCode == Constants.SETTINGS_REQUEST) {
	     
		 // Save the settings. 
		 settings = (Settings) data.getSerializableExtra(Constants.DATA_SETTINGS);
	     //Toast.makeText(this, settings.toString(), Toast.LENGTH_SHORT).show();
	  }
	} 
	
	public void onImageSearch(View v) {

		imageResults.clear(); 
		loadImages(0);
		
	}
	
	private void loadImages(int page) { 
		
		String query = etQuery.getText().toString();
		//Toast.makeText(this, query + ": page " + page, Toast.LENGTH_SHORT).show();    
		AsyncHttpClient client = new AsyncHttpClient();
		
        String unFilteredUrl = baseUrl + Uri.encode(query) +"&start=" + page;
		
        StringBuffer urlBuffer = new StringBuffer(unFilteredUrl);
        
        if (settings.getFilterColor() != null) {
        	
        	urlBuffer.append("&imgcolor="+settings.getFilterColor());
        }
        if (settings.getFilterSite() != null) {
        	
        	urlBuffer.append("&as_sitesearch="+settings.getFilterSite());
        }
        
        if(settings.getFilterSize() != null) {
        	
        	urlBuffer.append("&imgsz="+settings.getFilterSite());
        }

        if(settings.getFilterType() !=null) {
        	urlBuffer.append("&imgtype="+settings.getFilterType());
        }
        
        String urlWithFilters = urlBuffer.toString();
        Log.d("DEBUG",urlWithFilters);
        client.get(urlWithFilters, 
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				
				JSONArray imageJsonResults = null;
				
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					//imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG",imageResults.toString());
					
				}catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});

	}
	private void setupViews() {
		
		etQuery = (EditText)findViewById(R.id.etQuery);
		btSearch = (Button)findViewById(R.id.btSearch);
		gvResults = (GridView)findViewById(R.id.gvResults);
		
	}
}
