package com.example.android.foodstorm;

public enum FoodGroup {
    PRODUCE("Rabbit feed", 0),
    PROTEIN("Shark feed", 1);

    private String stringValue;
    private int intValue;
    private FoodGroup(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
    
    public int toInt() {
    	return intValue;
    }
}