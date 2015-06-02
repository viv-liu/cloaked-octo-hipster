package com.example.android.foodstorm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
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
public class FridgeItemAdapter extends ArrayAdapter<FoodItem> implements Filterable {
	private static final String TAG = "FridgeItemAdapter";
	private static final Integer LIST_HEADER = 0;
    private static final Integer LIST_ITEM = 1;
    
	private final Context mContext;
	private final List<FoodItem> FOOD_GROUPS_AS_FRIDGEFOODITEMS;
	
	private List<FoodItem> mOriginalFoodList;
	private List<FoodItem> mFilteringList;
	
	// might want to put these in a separate constructor
	public OnClickListener itemClickListener = null;
	public int actionIcon = -1;
	
	public FridgeItemAdapter(Context c, List<FoodItem> fl) {
		super(c, android.R.layout.simple_list_item_1, fl);
		mContext = c;
		
		FOOD_GROUPS_AS_FRIDGEFOODITEMS = new ArrayList<FoodItem>();
		FOOD_GROUPS_AS_FRIDGEFOODITEMS.add(new FoodItem(-1, FoodGroup.PRODUCE.toString(), FoodGroup.PRODUCE, R.drawable.chef_hat, true, 0, "none"));
		FOOD_GROUPS_AS_FRIDGEFOODITEMS.add(new FoodItem(-1, FoodGroup.PROTEIN.toString(), FoodGroup.PROTEIN, R.drawable.chef_hat, true, 0, "none"));
		
		mOriginalFoodList = fl;
		mFilteringList = fl;
	}
	
	public FridgeItemAdapter(Context c, FoodItem[] fl){
		this(c, Arrays.asList(fl));		
	}
	
	public FridgeItemAdapter(Context c, List<FoodItem> fl, OnClickListener listener, int icon){
		this(c, fl);
		itemClickListener = listener;
		actionIcon = icon;
	}
	
	@Override
    public int getCount() {
        return mFilteringList.size();
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
    public FoodItem getItem(int position) {
    	// If position is out of index
    	if(position >= mFilteringList.size()) {
    		return null;
    	}
        return mFilteringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {		
		// TODO: add more ORs when more FoodGroup enums added
		String foodName = new String(mFilteringList.get(position).name);
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
        tv_foodName.setText(mFilteringList.get((position) % mFilteringList.size()).name);
        Log.d(FridgeItemAdapter.TAG, "mFoodItemList = " + mFilteringList.get((position) % mFilteringList.size()).name);
        
        ImageView imageView = (ImageView) item.findViewById(R.id.lv_item_icon);
		imageView.setImageResource(mFilteringList.get(position).icon);
        TextView subtext = (TextView)item.findViewById(R.id.lv_item_subtext);
        subtext.setText("*Any extra info that may need displaying*");
         
        // set a click listener to the right button
        ImageButton rightButton = (ImageButton)item.findViewById(R.id.button);
        if(itemClickListener != null && rightButton != null){
        	/* slightly convoluted scheme where tag is set to item (parent),
        	 * and item's id is set to the position. Using tag directly 
        	 * doesn't work. idk why.
        	 */
        	item.setId(position);
        	rightButton.setTag(item);
        	rightButton.setOnClickListener(itemClickListener);
        }
        if(rightButton != null && actionIcon > 0){
        	rightButton.setImageResource(actionIcon);
        }
        return item;
    }

    private String getHeader(int position) {
	    return mFilteringList.get(position).name;
    }
    
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mFilteringList = (List<FoodItem>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<FoodItem> FilteredArrList = new ArrayList<FoodItem>();

                if (mOriginalFoodList == null) {
                    mOriginalFoodList = new ArrayList<FoodItem>(mFilteringList); // saves the original data in mOriginalValues
                }

                /********
                 * 
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)  
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return  
                    results.count = mOriginalFoodList.size();
                    results.values = mOriginalFoodList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    
                    int curFoodGroupIndex = -1;
                    
                    // Iterate through the rest of the foods, add ones that satisfy the constraint to FilteredArrList AND is NOT a header
                    for (int i = 0; i < mOriginalFoodList.size(); i++) {
                        FoodItem data = mOriginalFoodList.get(i);
                        if (data.name.toLowerCase().startsWith(constraint.toString()) && !data.isHeader) {
                        	
                        	// Next food group, add the PROTEIN (and etc.) header
                        	if(data.foodGroup.toInt() != curFoodGroupIndex) {
                        		curFoodGroupIndex = data.foodGroup.toInt();
                        		FilteredArrList.add(FOOD_GROUPS_AS_FRIDGEFOODITEMS.get(curFoodGroupIndex));
                        	}
                            FilteredArrList.add(data);
                            Log.d("Filter", "Added" + String.valueOf(data.name));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
