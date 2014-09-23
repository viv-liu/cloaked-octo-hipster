package com.example.foodstorm.fridge;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FridgeFragment extends ListFragment 
{
	public final static String TAG = "FridgeFragment";
	// TEMP: static list stores contents of fridge for now.
	public static List<String> ingredientNames;
	// Make custom adapter to display various pieces of info from each Ingredient.
	//public static List<Ingredient> contents;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
	    super.onActivityCreated(savedInstanceState);
	    
	    if(FridgeFragment.ingredientNames == null)
	    {
	    	FridgeFragment.ingredientNames = new ArrayList<String>();
	    }
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	        android.R.layout.simple_list_item_1, FridgeFragment.ingredientNames);
	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) 
	  {
	    // do something with the data
	  }
}
