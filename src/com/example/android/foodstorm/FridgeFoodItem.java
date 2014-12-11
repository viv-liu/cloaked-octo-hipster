package com.example.android.foodstorm;
/**
 * Simple class to contain fields relevant to displaying a fridge item
 * in the list view
 * @author sugar
 *
 */
public class FridgeFoodItem {
	public String name;
	public FoodGroup foodGroup;
	public int icon;
	public boolean isHeader;
	public FridgeFoodItem(String n, FoodGroup fg, int i, boolean h){
		name = n;
		foodGroup = fg;
		icon = i;
		isHeader = h;
	}
}
