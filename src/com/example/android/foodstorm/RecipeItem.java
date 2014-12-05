package com.example.android.foodstorm;
/**
 * Simple class to hold relevant fields for recipes
 * @author sugar
 *
 */
public class RecipeItem {
	public String title;
	public String description;
	public int image;
	
	public RecipeItem(String t, String desc){
		title = t;
		description = desc;
		image = -1;
	}
	
	public RecipeItem(String t, String desc, int i) {
		title = t;
		description = desc;
		image = i;
	}
}
