package com.example.android.foodstorm;
/**
 * Simple class to contain fields relevant to displaying a food item
 * in the list view
 * @author sugar
 *
 */
public class FoodItem {
	public int id; /* set to -1 to indicate not actual food */
	public String name;
	public FoodGroup foodGroup;
	public int icon;
	public boolean isHeader;
	public float quantity;
	public String units;
	
	public FoodItem(int foodId, String n, FoodGroup fg, int i, boolean h, float q, String u){
		id = foodId;
		name = n;
		foodGroup = fg;
		icon = i;
		isHeader = h;
		quantity = q; 
		units = u;
	}

	/* constructor used for food items, or fake FoodItem objects (headers, etc.)
	   that don't correspond to a usable food in the database
	 */
	public FoodItem(String n, FoodGroup fg, int i, boolean h, float q, String u){
		id = -1;
		name = n;
		foodGroup = fg;
		icon = i;
		isHeader = h;
		quantity = q;
		units = u;
	}
}
