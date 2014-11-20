package com.example.android.foodstorm;

import com.example.android.effectivenavigation.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/***
 * FridgeItemAdapter class
 * Array structure: 
 * {dummy_foodGroup_FridgeFoodItem, fooditem1, fooditem2,...}
 * index:		0					1			2	  ,...
 * 
 * dummy_foodGroup_FridgeFoodItem is a dummy that gives food group info for this list
 * @author Vivian
 *
 */
public class FridgeItemAdapter extends ArrayAdapter<FridgeFoodItem> {
	private static final String TAG = "FridgeItemAdapter";
	
	private static final Integer LIST_HEADER = 0;
    private static final Integer LIST_ITEM = 1;
    
	private final Context mContext;
	private final FridgeFoodItem[] mFoodItemList;
	
	public FridgeItemAdapter(Context c, FridgeFoodItem[] fl){
		super(c, android.R.layout.simple_list_item_1, fl);
		mContext = c;
		mFoodItemList = fl;		
	}
	
	@Override
    public int getCount() {
        return mFoodItemList.length;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public FridgeFoodItem getItem(int position) {
    	// If position is out of index
    	if(position >= mFoodItemList.length) {
    		return null;
    	}
        return mFoodItemList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {		

		// TODO: add more ORs when more FoodGroup enums added
		String foodName = new String(mFoodItemList[position].name);
		if(foodName.equals(FoodGroup.PRODUCE.toString()) ||
		   foodName.equals(FoodGroup.PROTEIN.toString())) {
			String headerText = getHeader(position);
			if(headerText != null) {

	            View item = convertView;
	            if(convertView == null || convertView.getTag() == LIST_ITEM) {

	                item = LayoutInflater.from(mContext).inflate(
	                        R.layout.lv_header_layout, parent, false);
	                item.setTag(LIST_HEADER);

	            }

	            TextView headerTextView = (TextView)item.findViewById(R.id.lv_list_hdr);
	            headerTextView.setText(headerText);
	            return item;
	        }
		}
		
        View item = convertView;
        if(convertView == null || convertView.getTag() == LIST_HEADER) {
            item = LayoutInflater.from(mContext).inflate(
                    R.layout.lv_layout, parent, false);
            item.setTag(LIST_ITEM);
        }

        TextView tv_foodName = (TextView)item.findViewById(R.id.lv_item_header);
        tv_foodName.setText(mFoodItemList[(position) % mFoodItemList.length].name);
        Log.d(FridgeItemAdapter.TAG, "mFoodItemList = " + mFoodItemList[(position) % mFoodItemList.length].name);
        
        ImageView imageView = (ImageView) item.findViewById(R.id.lv_item_icon);
		imageView.setImageResource(mFoodItemList[position].icon);
        // Any other kind of info we want to stick into the subtext?
        TextView subtext = (TextView)item.findViewById(R.id.lv_item_subtext);
        subtext.setText("*Any extra info that may need displaying*");
        
        //Set last divider (grey line) in a sublist invisible
        /*View divider = item.findViewById(R.id.item_separator);
        if(position == mFoodItemList.length - 1) {
            divider.setVisibility(View.INVISIBLE);
        }*/
        return item;
    }

    private String getHeader(int position) {
	    return mFoodItemList[position].name;
    }
	// Creates string array from array of fridge items. 
	// Needed to make super constructor call one line (because it has to be the first line)
	/*private static String[] fridgeListItemToStrArr(FridgeFoodItem[] values){
		String[] strValues = new String[values.length];
		for(int i = 0;i < values.length;i++) {
			strValues[i] = values[i].name;
		}
		return strValues;
	}*/
}
