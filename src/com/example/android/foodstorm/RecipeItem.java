package com.example.android.foodstorm;

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
	public int image;
	List<String> directions;
	List<FoodItem> ingredients;
	
	public RecipeItem(String t, String desc){
		title = t;
		description = desc;
		image = -1;
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
		directions = d;
		ingredients = ing;
	}
}
