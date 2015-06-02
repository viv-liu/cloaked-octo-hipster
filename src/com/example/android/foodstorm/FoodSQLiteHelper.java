package com.example.android.foodstorm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodSQLiteHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "foodstorm";
	public static final int DB_VERSION = 1;
	
	public static final String FOODS_TBL_NAME = "foods";
	public static final String FOODS_ID_FIELD = "foodId";
	public static final String FOODS_NAME_FIELD = "name";
	public static final String FOODS_GROUP_FIELD = "foodgroup";
	public static final String FOODS_ICON_FIELD = "icon";
	public static final String FOODS_QUANTITY_FIELD = "quantity";
	public static final String FOODS_UNITS_FIELD = "units";
	
	/* Updates to SQLite DB aren't reflected until 
	 * app is closed and restarted. To get around this, we keep
	 * an in-memory copy of the foods list, and only fetch from the 
	 * database when the app is first loaded.
	 */
	public static boolean dataLoaded = false;
	private static List<FoodItem> foodsList = null;
	
	public FoodSQLiteHelper(Context context) {
	    super(context, DB_NAME, null, DB_VERSION);
	  }
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + FOODS_TBL_NAME +
				"(" + FOODS_ID_FIELD + " INTEGER, " +
				FOODS_NAME_FIELD + " TEXT NOT NULL, " +
				FOODS_GROUP_FIELD + " TEXT, " +
				FOODS_ICON_FIELD + " TEXT, " +
				FOODS_QUANTITY_FIELD + " FLOAT, " +
				FOODS_UNITS_FIELD + " TEXT);");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/* drop the table and create a new one
		 * how are we supposed to handle upgrades anyways
		 * hopefully this function never gets called
		 */
		db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
		onCreate(db);
	}
	
	public int addFood(FoodItem food){
		int rc = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FoodSQLiteHelper.FOODS_ID_FIELD, food.id);
		values.put(FoodSQLiteHelper.FOODS_NAME_FIELD, food.name);
		values.put(FoodSQLiteHelper.FOODS_GROUP_FIELD, food.foodGroup.toString());
		values.put(FoodSQLiteHelper.FOODS_ICON_FIELD, food.icon);
		values.put(FoodSQLiteHelper.FOODS_QUANTITY_FIELD, food.quantity);
		values.put(FoodSQLiteHelper.FOODS_UNITS_FIELD, food.units);

		//if(db.insert(FOODS_TBL_NAME, null, values) < 0) rc = -1;
		try {
			db.insertOrThrow(FOODS_TBL_NAME, null, values);
		} catch(android.database.SQLException e) {
			return -1;
		}
		db.close();
		
		if(foodsList != null) foodsList.add(food);
		return rc;
	}
	
	public void deleteFood(FoodItem food) {
		SQLiteDatabase db = this.getWritableDatabase();
		// TODO: Change to ID
		db.execSQL("DELETE FROM " + FOODS_TBL_NAME + 
				" WHERE " + FOODS_NAME_FIELD + "='" + food.name + "';");
		db.close();
		
		if(foodsList != null) {
			int i;
			// Delete everything with matching food name
			for(i = 0;i < foodsList.size();i++) {
				if(foodsList.get(i).name.equals(food.name)){
					foodsList.remove(i);
					i--; // assuming it shifts
				}
			}
		}
	}
	
	public List<FoodItem> getAllFoods(){
		SQLiteDatabase db = this.getWritableDatabase();
		
		if(foodsList != null) return foodsList;
		List<FoodItem> foods = new ArrayList<FoodItem>();
		String[] allCols = {
				FoodSQLiteHelper.FOODS_ID_FIELD,
				FoodSQLiteHelper.FOODS_NAME_FIELD,
				FoodSQLiteHelper.FOODS_GROUP_FIELD,
				FoodSQLiteHelper.FOODS_ICON_FIELD,
				FoodSQLiteHelper.FOODS_QUANTITY_FIELD,
				FoodSQLiteHelper.FOODS_UNITS_FIELD
		};
		
		Cursor cursor = db.rawQuery("SELECT * FROM " + FOODS_TBL_NAME + ";", null);
		
		cursor.moveToFirst();
		while(! cursor.isAfterLast()){
			FoodItem food = new FoodItem(cursor.getInt(0), cursor.getString(1),
					FoodGroup.PRODUCE,  // todo: group
					Integer.parseInt(cursor.getString(3)),
					false,
					Float.parseFloat(cursor.getString(4)),
					cursor.getString(5));
			foods.add(food);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		
		foodsList = foods;
		return foods;
	}
	
}
