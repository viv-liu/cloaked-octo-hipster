package com.example.android.foodstorm;

import java.util.List;

import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RecipeDetailsActivity extends FragmentActivity {

	public static RecipeItem RECIPE;
	
	private LinearLayout linear_layout;
	private DirectionCardAdapter directionCardAdapter;
	private TextView tv_title;
	private ImageView iv_foodPic;
	private TableLayout tl_ingredients;
	
	RecipeFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.activity_recipe_details);

        mAdapter = new RecipeFragmentAdapter(getSupportFragmentManager(), RECIPE);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (IconPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
	}
}




