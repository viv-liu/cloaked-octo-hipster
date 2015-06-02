/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.foodstorm;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;
	public static FoodSQLiteHelper dataSource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		dataSource = new FoodSQLiteHelper(this);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);     // no home/up button
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(
                actionBar.newTab()
                        .setIcon(R.drawable.fridge_main)
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setIcon(R.drawable.chef_hat)
                        .setTabListener(this));
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
	    	case R.id.action_add:
	    		/*IntentIntegrator integrator = new IntentIntegrator(this);
	    		integrator.initiateScan();
	    		Intent scanIntent = new Intent("com.google.zxing.client.android.SCAN");
				scanIntent.putExtra("SCAN_MODE", "PRODUCT_MODE");
				scanIntent.putExtra("SCAN_FORMATS", "UPC_A,UPC_E,EAN_8,EAN_13,CODE_39,CODE_93,CODE_128");
				startActivityForResult(scanIntent, 0);*/
	    		
	    		Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
	    		return true;
    	}
    	return false;
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
    	/*IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	if(scanResult != null){
    		String code = scanResult.getContents();
    		Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
    		UpcRequestTask requestTask = new UpcRequestTask();
    		requestTask.appContext = getApplicationContext();
    		requestTask.execute(code);
    		
    	} else Toast.makeText(getApplicationContext(), "Barcode scan failed", Toast.LENGTH_SHORT).show();*/
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return new FridgeFragment();

                default:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new RecipesFragment();
                    Bundle args = new Bundle();
                    args.putInt(RecipesFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

    /**
     * Fridge fragment
     */
    public static class FridgeFragment extends Fragment {
    	public static List<FoodItem> fridgeList;
		public static boolean fridgeListChanged = true; // prevent refreshing recipes when we don't need to
    	private FridgeItemAdapter adapter;
    	public FoodSQLiteHelper dataSource; // for convenience
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	setHasOptionsMenu(true);
        	
        	dataSource = MainActivity.dataSource;
        	fridgeList = dataSource.getAllFoods();
        	
        	OnClickListener itemClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) { // deletion handling
					FoodItem food;
					food = fridgeList.get(((View) v.getTag()).getId());
					dataSource.deleteFood(food);
					fridgeListChanged = true;
					
					Toast.makeText(getActivity(), food.name + " deleted!", Toast.LENGTH_SHORT).show();
					
					fridgeList = dataSource.getAllFoods();
					adapter.notifyDataSetChanged();
				}};
			adapter = new FridgeItemAdapter(getActivity(), fridgeList, itemClickListener, R.drawable.x);
            View rootView = inflater.inflate(R.layout.fragment_section_fridge, container, false);
            return rootView;
        }
        
        public void onResume() {
        	super.onResume();
        	fridgeList = dataSource.getAllFoods();
			fridgeListChanged = true;
        	adapter.notifyDataSetChanged();
        }
        
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.main_activity_actions, menu);

    		 SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
    	        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

    	            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    	            searchView.setIconifiedByDefault(true);   

    	        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() 
    	        {
    	            @Override
    	            public boolean onQueryTextChange(String newText) 
    	            {
    	                // this is your adapter that will be filtered
    	                adapter.getFilter().filter(newText);
    	                System.out.println("on text chnge text: "+newText);
    	                return true;
    	            }
    	            @Override
    	            public boolean onQueryTextSubmit(String query) 
    	            {
    	                // this is your adapter that will be filtered
    	                adapter.getFilter().filter(query);
    	                System.out.println("on query submit: "+query);
    	                return true;
    	            }
    	        };
    	        searchView.setOnQueryTextListener(textChangeListener);
        }
        public void onStart() {
        	super.onStart();
        	Button fridge_add = (Button)getActivity().findViewById(R.id.fridge_add);
        	OnClickListener addClickListener = new OnClickListener() {
        		public void onClick(View v) {
        			Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
                    startActivity(intent);
        		}
        	};
        	fridge_add.setOnClickListener(addClickListener);
        	
        	
        	ListView lv_produce = (ListView)getActivity().findViewById(R.id.lv_fridge);
            lv_produce.setAdapter(adapter);
            lv_produce.setTextFilterEnabled(true); // for search filtering
        }
        
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class RecipesFragment extends Fragment implements OnClickListener {

        public static final String ARG_SECTION_NUMBER = "section_number";
        
        //private GridView cards;
        private LinearLayout leftList, rightList;
        ArrayList<RecipeItem> leftRecipes;
        ArrayList<RecipeItem> rightRecipes;
		ViewGroup recipesContainer = null;
		LinearLayout.LayoutParams cardSpacing = null;
        // Vars to help with linked scrolling
        View clickSource, touchSource;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_recipes, container, false);

			recipesContainer = container;

            /* populate left and right lists */
            leftRecipes = new ArrayList<RecipeItem>();
        	rightRecipes = new ArrayList<RecipeItem>();
        	
        	/* add to left and right lists */
        	leftList = (LinearLayout) rootView.findViewById(R.id.recipes_list_left);
        	rightList = (LinearLayout) rootView.findViewById(R.id.recipes_list_right);
        	
        	cardSpacing = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        			LinearLayout.LayoutParams.WRAP_CONTENT);
        	cardSpacing.setMargins(0, 0, 0, 20);
            return rootView;
        }

		/*
		 * On resume: refresh list of recipes
		 */
		@Override
		public void onResume() {
			super.onResume();
			// get matching recipes
			if(FridgeFragment.fridgeListChanged) {
				ArrayList<Integer> foodIds = new ArrayList<Integer>();
				for (FoodItem item : MainActivity.dataSource.getAllFoods()) foodIds.add(item.id);
				RecipesLookupTask requestTask = new RecipesLookupTask();

				requestTask.host = this;
				requestTask.execute(foodIds);
				FridgeFragment.fridgeListChanged = false;
			}
		}

		public void refreshListView() {
			leftList.removeAllViews();
			rightList.removeAllViews();
			/* Each view is assigned an integer id for OnClick identification.
        	 * In leftRecipes, ids range from 0 to leftRecipes.size() - 1
        	 * In rightRecipes, ids range from leftRecipes.size() + 0 to leftRecipes.size() +  rightRecipes.size() - 1 */
			for(int i = 0;i < leftRecipes.size();i++) {
				View cView = LayoutInflater.from(getActivity()).inflate(R.layout.recipe_card_layout, recipesContainer, false);
				TextView recipeTitle = (TextView) cView.findViewById(R.id.recipe_card_title);
				TextView recipeDescription = (TextView) cView.findViewById(R.id.recipe_card_description);
				ImageView recipeImage = (ImageView) cView.findViewById(R.id.recipe_card_image);
				recipeTitle.setText(leftRecipes.get(i).title);
				recipeDescription.setText(leftRecipes.get(i).description);
				if(leftRecipes.get(i).image > 0)
					recipeImage.setImageResource(leftRecipes.get(i).image);
				else
					recipeImage.setImageBitmap(leftRecipes.get(i).imageBitmap);
				cView.setLayoutParams(cardSpacing);
				cView.setId(i);
				cView.setOnClickListener(this);
				leftList.addView(cView);
			}
			for(int i = 0;i < rightRecipes.size();i++) {
				View cView = LayoutInflater.from(getActivity()).inflate(R.layout.recipe_card_layout, recipesContainer, false);
				TextView recipeTitle = (TextView) cView.findViewById(R.id.recipe_card_title);
				TextView recipeDescription = (TextView) cView.findViewById(R.id.recipe_card_description);
				ImageView recipeImage = (ImageView) cView.findViewById(R.id.recipe_card_image);
				recipeTitle.setText(rightRecipes.get(i).title);
				recipeDescription.setText(rightRecipes.get(i).description);
				if(rightRecipes.get(i).image > 0)
					recipeImage.setImageResource(rightRecipes.get(i).image);
				else
					recipeImage.setImageBitmap(rightRecipes.get(i).imageBitmap);
				cView.setLayoutParams(cardSpacing);
				cView.setId(i + leftRecipes.size());
				cView.setOnClickListener(this);
				rightList.addView(cView);
			}
		}

		public void updateListView(List<RecipeItem> items){
			boolean side = true;
			leftRecipes.clear();
			rightRecipes.clear();
			for(RecipeItem recipe : items){
				if(side) leftRecipes.add(recipe);
				else rightRecipes.add(recipe);
				side = !side;
			}


			refreshListView();
		}

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if(id < leftRecipes.size()) {
				RecipeDetailsActivity.RECIPE = leftRecipes.get(id);
				Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
				startActivity(intent);
			} else {
				RecipeDetailsActivity.RECIPE = rightRecipes.get(id - leftRecipes.size());
                Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
                startActivity(intent);
			}
		}
        
    }
}
