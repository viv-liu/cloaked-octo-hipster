package com.example.android.foodstorm;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RecipeDetailsActivity extends Activity {

	public static RecipeItem RECIPE;
	
	private LinearLayout linear_layout;
	private DirectionCardAdapter directionCardAdapter;
	private TextView tv_title;
	private ImageView iv_foodPic;
	private TableLayout tl_ingredients;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.activity_recipe_details);
    	
    	tv_title = (TextView) findViewById(R.id.textView1);
    	tv_title.setText(RECIPE.title);
    	iv_foodPic = (ImageView) findViewById(R.id.imageView1);
    	iv_foodPic.setImageResource(RECIPE.image);
    	tl_ingredients = (TableLayout) findViewById(R.id.tableLayout1);
    	fillIngredientsTable(tl_ingredients, RECIPE.ingredients);
    	directionCardAdapter = new DirectionCardAdapter(this, RECIPE.directions);
    	linear_layout = (LinearLayout) findViewById(R.id.linear_layout);
    	
    	for(int i = 0; i < RECIPE.directions.size(); i++) {
    		linear_layout.addView(directionCardAdapter.getView(i, null, linear_layout));
    	}
	}
	
	private void fillIngredientsTable(TableLayout table, List<FoodItem> sortedIngredients) {
		 
        TableRow row;
        TextView tv1, tv2;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());
 
        for (int i = 0; i < sortedIngredients.size(); i++) {
            row = new TableRow(this);
            
            // TODO: not sure if these params do anything noticeable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(20*dip, 1, 20*dip, 1);
            row.setLayoutParams(params);
            
            if(i%2 == 0) {
            	//row.setBackgroundColor(Color.LTGRAY);
            } else {
            	row.setBackgroundColor(Color.LTGRAY);
            }
            tv1 = new TextView(this);
            tv2 = new TextView(this);
 
            tv1.setText(sortedIngredients.get(i).name);
            tv2.setText(String.valueOf(sortedIngredients.get(i).quantity) + " " 
            						 + sortedIngredients.get(i).units);

            tv1.setTypeface(null, 1);
            tv2.setTypeface(null, 1);
 
            tv1.setTextSize(15);
            tv2.setTextSize(15);

            tv1.setPadding(20*dip, 10*dip, 10*dip, 10*dip);
            tv2.setPadding(10*dip, 10*dip, 20*dip, 10*dip);
            
            row.addView(tv1);
            row.addView(tv2);
 
            table.addView(row, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
 
        }
	}
}




