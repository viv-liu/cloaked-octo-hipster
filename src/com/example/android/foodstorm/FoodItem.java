package com.example.android.foodstorm;
/**
 * Simple class to contain fields relevant to displaying a food item
 * in the list view
 * @author sugar
 *
 */
public class FoodItem {
	public String name;
	public FoodGroup foodGroup;
	public int icon;
	public boolean isHeader;
	public float quantity;
	public String units;
	
	public FoodItem(String n, FoodGroup fg, int i, boolean h, float q, String u){
		name = n;
		foodGroup = fg;
		icon = i;
		isHeader = h;
		quantity = q; 
		units = u;
	}
}
