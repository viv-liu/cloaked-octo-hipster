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

import com.example.android.effectivenavigation.R;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
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

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
     * A fragment that launches other parts of the demo application.
     */
    public static class FridgeFragment extends Fragment {
    	static FridgeFoodItem[] fridgeList; 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	// For testing, hardcode some list items
        	fridgeList = new FridgeFoodItem[6];
        	fridgeList[0] = new FridgeFoodItem(FoodGroup.PRODUCE.toString(), FoodGroup.PRODUCE, R.drawable.chef_hat);
        	fridgeList[1] = new FridgeFoodItem("TestCarrot", FoodGroup.PRODUCE, R.drawable.chef_hat);
        	fridgeList[2] = new FridgeFoodItem("TestCabbage", FoodGroup.PRODUCE, R.drawable.chef_hat);
        	fridgeList[3] = new FridgeFoodItem(FoodGroup.PROTEIN.toString(), FoodGroup.PROTEIN, R.drawable.chef_hat);
        	fridgeList[4] = new FridgeFoodItem("TestCupcake", FoodGroup.PROTEIN, R.drawable.chef_hat);
        	fridgeList[5] = new FridgeFoodItem("TestTornadoChicken", FoodGroup.PROTEIN, R.drawable.chef_hat);
        	
            View rootView = inflater.inflate(R.layout.fragment_section_fridge, container, false);
            return rootView;
        }
        
        public void onStart() {
        	super.onStart();
        	ListView lv_produce = (ListView)getActivity().findViewById(R.id.lv_fridge);
            lv_produce.setAdapter(new FridgeItemAdapter(getActivity(), fridgeList));
        }
        
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class RecipesFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";
        
        //private GridView cards;
        private ListView left_list, right_list;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_recipes, container, false);
            Bundle args = getArguments();
            //cards = (GridView) rootView.findViewById(R.id.recipes_cards_grid);
            //cards.setAdapter(createAdapter());
            left_list = (ListView) rootView.findViewById(R.id.recipes_list_left);
            right_list = (ListView) rootView.findViewById(R.id.recipes_list_right);
            left_list.setAdapter(createAdapterLeft());
            right_list.setAdapter(createAdapterRight());
            return rootView;
        }
        
        private RecipeCardAdapter createAdapterLeft() {
        	ArrayList<RecipeItem> recipes = new ArrayList<RecipeItem>();
        	
        	recipes.add(0, new RecipeItem("Fried Fish",
        			"Sometimes appears in Asian supermarkets and Korean restuarants. For some reason this description is also really" +
        			" really long, maybe because there's food in the background too that's part of the recipe",
        			R.drawable.friedfish));
        	recipes.add(1, new RecipeItem("Burnt Dumplings", 
        			"Chester demonstrates how not to cook. Make sure you're alone first",
        			R.drawable.burnt_dumplings));
        	
        	return new RecipeCardAdapter(getActivity(), recipes);
        }
        
        private RecipeCardAdapter createAdapterRight() {
        	ArrayList<RecipeItem> recipes = new ArrayList<RecipeItem>();
        	
        	recipes.add(0, new RecipeItem("Ridiculous Omelet", 
        			"Inspires ragequitting and fruitless yelling. Reduces difficulty of all other recipes",
        			R.drawable.flipped_chef_hat));
        	recipes.add(1, new RecipeItem("Rice and Beef",
        			"Awesome food inspired by Microsoft cafeterias and good code",
        			R.drawable.rice_and_chopsticks));
        	
        	
        	return new RecipeCardAdapter(getActivity(), recipes);
        }
    }
}
