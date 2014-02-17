package com.suhasjoshi.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	public static final int REQUEST_CODE = 100;
	public static final String SAVE_FILE = "save_file.txt";
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);  
        //Log.w("TodoActivity:onCreate",items.toString());
        setupListViewListener();
        setupListItemListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	
      if (resultCode == RESULT_OK ) {
         // Extract name value from result extras
         String listItemNewValue = data.getExtras().getString("listItemNewValue");
         int listItemPos = data.getExtras().getInt("listItemPos", 0);
         items.set(listItemPos, listItemNewValue);
         itemsAdapter.notifyDataSetChanged();
         saveItems();
      }
    } 
    
    //Add items to todo list
    public void addTodoItem(View v) {
    	
    	//Log.w("TodoActivity:addTodoItem","Adding item current : " + items.toString());
    	
    	EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	//Log.w("TodoActivity:addTodoItem",items.toString());
    	saveItems();
    	
    }
    
    //Implements delete on long click
    private void setupListViewListener() {
    	
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
    		
    		
    		@Override
    	    public  boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id) {
    			
    			//Log.w("TodoActivity:setupListViewListener:1", items.toString());
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			return true;
    		}
    		
    		
    	});
    }
    
    private void setupListItemListener() {
    	
    	//Listen on single click and launch the Edit activity
    	lvItems.setOnItemClickListener(new OnItemClickListener(){
    		
    		@Override
    		public void onItemClick (AdapterView<?> aView, View item, int pos, long id) {
    			  Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
    			  i.putExtra("listItemValue", items.get(pos));
    			  i.putExtra("listItemPos", pos);
    			  startActivityForResult(i, REQUEST_CODE); // brings up the second activity
    		}
    	});
    }
    
    //Read the todo list from persisted file
    private void readItems() {
    	
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir,SAVE_FILE);
    	
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException ioEx) {
    	  items = new ArrayList<String>();
    	  ioEx.printStackTrace();
    	}
    	
    	
    }
    
    //Save the todo list to a file for persistence
    private void saveItems() {
    	
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, SAVE_FILE);
    	
    	try {
    		
    		FileUtils.writeLines(todoFile,items);
    		
    	} catch (IOException ioEx) {
    	
    		ioEx.printStackTrace();
    	}
    }
    
}
