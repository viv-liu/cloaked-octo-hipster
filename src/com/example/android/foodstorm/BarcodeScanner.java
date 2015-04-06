package com.example.android.foodstorm;

import com.example.android.foodstorm.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

public class BarcodeScanner extends Activity {
	private static final String SCANNER = "com.google.zxing.client.android.SCAN";
	private static final String SCAN_FORMATS = "UPC_A,UPC_E,EAN_8,EAN_13,CODE_39,CODE_93,CODE_128";
	private static final String SCAN_MODE = "PRODUCT_MODE";
	
	public void OnCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barcode_scanner);
		
		try {
			Button scanButton = (Button)findViewById(R.id.barcode_scanner_button);
			
			scanBarcodeButtonListener scanButtonListener = new scanBarcodeButtonListener();
			scanButton.setOnClickListener(scanButtonListener);
		} catch(ActivityNotFoundException e){
			// wtf
		}
	}
	
	public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.activity_barcode_scanner, container, false);
        return rootView;
	}
	
	private class scanBarcodeButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent scanIntent = new Intent(SCANNER);
			scanIntent.putExtra("SCAN_MODE", SCAN_MODE);
			scanIntent.putExtra("SCAN_FORMATS", SCAN_FORMATS);
			startActivityForResult(scanIntent, 0);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent result){
		if(requestCode == 0){
			if(resultCode == RESULT_OK) {
				String barcode = result.getStringExtra("SCAN_RESULT");
				String format = result.getStringExtra("SCAN_RESULT_FORMAT");
				// do something on successful scan
			} else if(resultCode == RESULT_CANCELED) {
				// something went wrong
			}
		}
	}
}
