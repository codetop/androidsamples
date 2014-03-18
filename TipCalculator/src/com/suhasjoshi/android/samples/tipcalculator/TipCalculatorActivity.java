package com.suhasjoshi.android.samples.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TipCalculatorActivity extends Activity {
	
	
	private RadioGroup tipGroup;
	private RadioButton tip15Selection;
	private RadioButton tip20Selection;
	private RadioButton tip25Selection;
	private EditText etCheckAmount;
	private TextView tvTipAmount;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);
		tipGroup = (RadioGroup) findViewById(R.id.rgTipGroup);
		tip15Selection = (RadioButton) findViewById(R.id.rbTip15);
		tip20Selection = (RadioButton) findViewById(R.id.rbTip20);
		tip25Selection = (RadioButton) findViewById(R.id.rbTip25);
		etCheckAmount = (EditText) findViewById(R.id.etCheckAmount);
		tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		
		
		tipGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup tipGroup, int checkedId) {
				
				switch (checkedId) {
				
				case R.id.rbTip15:
					updateTip(15,getCheckAmount());
					break;
				case R.id.rbTip20:
					updateTip(20,getCheckAmount());
					break;
				case R.id.rbTip25:
					updateTip(25,getCheckAmount());					
				break;
			
				}
					
			}
			
		});
	

		etCheckAmount.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				
				if(tip15Selection.isChecked()) {
					
					updateTip(15,getCheckAmount());
					
				} else if (tip20Selection.isChecked()) {
					
					updateTip(20,getCheckAmount());
					
				} else if (tip25Selection.isChecked()) {
					
					updateTip(25,getCheckAmount());
					
				}
				return true;
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calculator, menu);
		return true;
	}
	protected float getCheckAmount() {
		
		float checkAmount=0;		
		checkAmount = Float.parseFloat(etCheckAmount.getText().toString());
		return checkAmount;
	}
	protected void updateTip(int tipPercent,float checkAmount) {
		
		float tip = checkAmount*tipPercent/100;
		tvTipAmount.setText(new Float(tip).toString());
		
	}
	


}
