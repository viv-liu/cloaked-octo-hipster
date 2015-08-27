package com.example.android.foodstorm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class to hold relevant fields for recipes
 * @author sugar
 *
 */
public class RecipeItem {
	public String title;
	public String description;
	public int image;               // preferred over imageDrawable (used for testing)
	//public Bitmap imageBitmap;         // more convenient for getting images off web
	public String imageUrl;
	List<String> directions;
	List<FoodItem> ingredients;
	public int id;
	
	public RecipeItem(String t, String desc){
		title = t;
		description = desc;
		image = -1;
		imageUrl = null;
		directions = new ArrayList<String>();
		directions.add("Find apples.");
		directions.add("Eat apples.");
		directions.add("Find more apples.");
		directions.add("Share apples.");
		directions.add("Or not");
	}
	
	public RecipeItem(String t, String desc, int i, List<String> d, List<FoodItem> ing) {
		title = t;
		description = desc;
		image = i;
		imageUrl = null;
		directions = d;
		ingredients = ing;
	}
}
