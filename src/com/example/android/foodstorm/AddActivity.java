package com.example.android.foodstorm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity implements SearchView.OnQueryTextListener, OnClickListener {
	private TextView statusTextView;
	private SearchView searchView;
	private ListView searchList;
	private FridgeItemAdapter adapter;
	private ArrayList<FoodItem> fridgeList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    	setContentView(R.layout.activity_add);
    	
    	statusTextView = (TextView)findViewById(R.id.add_textview);
    	searchList = (ListView)findViewById(R.id.add_listview);
    	
    	fridgeList = new ArrayList<FoodItem>();
    	    	
    	adapter = new FridgeItemAdapter(this, fridgeList, this, R.drawable.plus);
    	searchList.setAdapter(adapter);
    	
    	searchList.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_activity_actions, menu);
		
		MenuItem searchItem = menu.findItem(R.id.action_add_search);
		searchView = (SearchView)searchItem.getActionView();
		
		searchView.setIconifiedByDefault(false);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		searchView.setOnQueryTextListener(this);
		
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		NameLookupTask requestTask = new NameLookupTask();
		
		requestTask.host = this;
		requestTask.execute(query);
		
		return false;
	}

	/* Could display matching items as name is typed, but
	   that would result in a lot of requests back to the server
	 */
	@Override
	public boolean onQueryTextChange(String newText) { return false; }
	
	public void updateText(String text) {
		statusTextView.setText(text);
	}
	
	public void updateListView(List<FoodItem> items){
		if(items.size() > 0){
			fridgeList.clear();
			for(int i = 0;i < items.size();i++){
				fridgeList.add(items.get(i)); // prefer arraylist
			}
			adapter.notifyDataSetChanged();
			searchList.setVisibility(View.VISIBLE);
			statusTextView.setVisibility(View.GONE);
		} else {
			fridgeList.clear();
			adapter.notifyDataSetChanged();
			searchList.setVisibility(View.INVISIBLE);
			statusTextView.setText("No result");
			statusTextView.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onClick(View view){	
		FoodItem food;
		FoodSQLiteHelper dataSource;
		
		food = fridgeList.get(((View)view.getTag()).getId()); // hope this is it
		dataSource = new FoodSQLiteHelper(this);
		if(dataSource.addFood(food) == 0) {
			Toast.makeText(this, food.name + " added!", Toast.LENGTH_SHORT).show();
			MainActivity.FridgeFragment.fridgeListChanged = true;
		} else Toast.makeText(this, "Add failed!", Toast.LENGTH_SHORT).show();
	}	
}