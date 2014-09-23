package com.example.foodstorm;

import android.R.string;

public class Ingredient {
	public final string name;
	public final string foodGroup;
	public int quantity;
	public string units;
	public int points;
	public boolean required;
	
	public Ingredient(string n, string f, int q, string u) {
		name = n;
		foodGroup = f;
		quantity = q;
		units = u;
	}
	
}
