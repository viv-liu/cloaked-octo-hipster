package com.example.android.effectivenavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FridgeItemAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final fridgeListItem[] values;
	public FridgeItemAdapter(Context c, fridgeListItem[] v){
		super(c, android.R.layout.simple_list_item_1, fridgeListItemToStrArr(v));
		context = c;
		values = v;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.fridge_listview_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.fridgeListViewItemName);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.fridgeListViewItemIcon);
		textView.setText(values[position].name);
		imageView.setImageResource(values[position].icon);
		return rowView;
	}
	
	// Creates string array from array of fridge items. 
	// Needed to make super constructor call one line (because it has to be the first line)
	private static String[] fridgeListItemToStrArr(fridgeListItem[] values){
		String[] strValues = new String[values.length];
		for(int i = 0;i < values.length;i++) {
			strValues[i] = values[i].name;
		}
		return strValues;
	}
}
