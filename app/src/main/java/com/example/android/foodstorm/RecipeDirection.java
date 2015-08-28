package com.example.android.foodstorm;

import java.util.ArrayList;

/**
 * Created by chester on 8/28/2015.
 */
public class RecipeDirection {
    public String direction;
    public ArrayList<FoodItem> ingredients;
    public RecipeDirection() {
        direction = null;
        ingredients = new ArrayList<FoodItem>();
    }
}
