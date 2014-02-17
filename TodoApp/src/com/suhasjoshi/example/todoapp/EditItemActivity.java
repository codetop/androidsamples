package com.suhasjoshi.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	EditText etListItem; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String listItemValue = getIntent().getStringExtra("listItemValue");
		etListItem = (EditText)findViewById(R.id.etListItem);
		etListItem.setText(listItemValue);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void onEdit(View v) {
		
		Intent data = new Intent();
		data.putExtra("listItemNewValue", etListItem.getText().toString());
		data.putExtra("listItemPos", getIntent().getExtras().getInt("listItemPos"));
		setResult(RESULT_OK,data);
		this.finish();
	}

}
