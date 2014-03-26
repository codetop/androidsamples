package com.suhasjoshi.android.samples.gridimagesearch;

import java.io.Serializable;

public class Settings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6669857209151528578L;
	public static final String ALL = "all";
	
	private String filterSize = null;
	private String filterColor = null;
	private String filterType = null;
	private String filterSite = null;
	
	public Settings() {
		super();
	}	
	public Settings(String filterSize, String filterColor, String filterType,
			String filterSite) {
		super();
		this.filterSize = filterSize;
		this.filterColor = filterColor;
		this.filterType = filterType;
		this.filterSite = filterSite;
	}
	public String getFilterSize() {
		return filterSize;
	}
	public void setFilterSize(String filterSize) {
		if(filterSize !=null && !filterSize.equals(ALL))
			this.filterSize = filterSize;
	}
	public String getFilterColor() {
		
		return filterColor;
	}
	public void setFilterColor(String filterColor) {
		if(filterColor !=null && !filterColor.equals(ALL))
			this.filterColor = filterColor;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		if(filterType !=null && !filterType.equals(ALL))
			this.filterType = filterType;
	}
	public String getFilterSite() {
		return filterSite;
	}
	public void setFilterSite(String filterSite) {
		if(filterSite !=null && !filterSite.trim().equals(""))
			this.filterSite = filterSite;
	} 
	
	public String toString() {
		
		return filterSite + " , " + filterSize + "," + filterType + "," + filterColor;  
	}

}
