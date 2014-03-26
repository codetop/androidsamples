package com.suhasjoshi.android.samples.gridimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	private AutoCompleteTextView actvTextView = null; 
	private Spinner spSize = null;
	private Spinner spType = null;
	private Spinner spColor = null; 
	private ArrayAdapter<CharSequence> spSizeAdapter = null; 
	private ArrayAdapter<CharSequence> spColorAdapter = null;
	private ArrayAdapter<CharSequence> spTypeAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupViews();
		setupAdapters(); 
		populateView();
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void onSave (View view) { 
		
		Settings settings = new Settings();		
		settings.setFilterColor(spColor.getSelectedItem().toString());
		settings.setFilterSite(actvTextView.getText().toString());
		settings.setFilterSize(spSize.getSelectedItem().toString());
		settings.setFilterType(spType.getSelectedItem().toString());
		Intent data = new Intent();
		data.putExtra(Constants.DATA_SETTINGS, settings);
		setResult(RESULT_OK, data);
		this.finish();
	}
	
	private void setupViews() {
		
		actvTextView = (AutoCompleteTextView) findViewById(R.id.actSite);
		spSize = (Spinner) findViewById(R.id.spSize);
		spType = (Spinner) findViewById(R.id.spType);
		spColor = (Spinner) findViewById(R.id.spColor);
	}
	
	private void setupAdapters() { 
		
		spSizeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_sizes, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		spSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spSize.setAdapter(spSizeAdapter);
		
		spColorAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_colors, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		spColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spColor.setAdapter(spColorAdapter);
		
		spTypeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_types, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		spTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spType.setAdapter(spTypeAdapter);
	}
	
	private void populateView() {
		
		Settings settings = (Settings)getIntent().getSerializableExtra(Constants.DATA_SETTINGS);
		if(settings.getFilterSite() != null) {
			
			actvTextView.setText(settings.getFilterSite());
		}
		if(settings.getFilterColor() !=null) {
			
			spColor.setSelection(spColorAdapter.getPosition(settings.getFilterColor()));
		}
		
		if(settings.getFilterSize() != null) {
			spSize.setSelection(spSizeAdapter.getPosition(settings.getFilterSize()));
		}
		
		if(settings.getFilterType() != null) {
			spType.setSelection(spTypeAdapter.getPosition(settings.getFilterType()));
		}
	}
	

}
