package com.suhasjoshi.android.samples.gridimagesearch;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	
	private String fullUrl = null;
	private String thumbUrl = null;
	
	
	public ImageResult(JSONObject json) {
		
		try {
			
			this.setFullUrl(json.getString("url"));
			this.setThumbUrl(json.getString("tbUrl"));
			
		} catch(JSONException e) {
			
			//TODO: Log Error
			e.printStackTrace();
		}
		
		
	}


	public String getFullUrl() {
		return fullUrl;
	}


	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}


	public String getThumbUrl() {
		return thumbUrl;
	}


	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}


	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray imageJsonResults) {
		
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		
		for(int i=0; i<imageJsonResults.length(); i++) {
			
			try {

				results.add(new ImageResult(imageJsonResults.getJSONObject(i)));
			} catch(JSONException e) {
				e.printStackTrace();
			}
			

		}
		return results;
	}

   public String toString() {
		return thumbUrl;
	}
}
