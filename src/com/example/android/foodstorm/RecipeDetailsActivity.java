package com.example.android.foodstorm;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class RecipeDetailsActivity extends Activity {

	public static RecipeItem RECIPE;
	
	private LinearLayout linear_layout;
	private DirectionCardAdapter directionCardAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.activity_recipe_details);
    	directionCardAdapter = new DirectionCardAdapter(this, RECIPE.directions);
    	linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
    	
    	for(int i = 0; i < RECIPE.directions.size(); i++) {
    		linear_layout.addView(directionCardAdapter.getView(i, null, linear_layout));
    	}
	}
}


