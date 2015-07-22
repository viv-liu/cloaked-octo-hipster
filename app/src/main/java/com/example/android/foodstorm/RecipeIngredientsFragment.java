package com.example.android.foodstorm;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RecipeIngredientsFragment extends Fragment{
	private TableLayout tl_ingredients;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
    	
    	tl_ingredients = (TableLayout) rootView.findViewById(R.id.tableLayout1);
       	fillIngredientsTable(tl_ingredients, RecipeFragmentAdapter.RECIPE.ingredients);
		
		return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    private void fillIngredientsTable(TableLayout table, List<FoodItem> sortedIngredients) {
  	 
    	TableRow row;
    	TextView tv1, tv2;
    	//Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());
 
        for (int i = 0; i < sortedIngredients.size(); i++) {
            row = new TableRow(this.getActivity());
            
            // TODO: not sure if these params do anything noticeable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(20 * dip, 1, 20 * dip, 1);
            row.setLayoutParams(params);
            
            if(i%2 == 0) {
            	//row.setBackgroundColor(Color.LTGRAY);
            } else {
            	row.setBackgroundColor(Color.LTGRAY);
            }
            tv1 = new TextView(this.getActivity());
            tv2 = new TextView(this.getActivity());
 
            tv1.setText(sortedIngredients.get(i).name);
            tv2.setText(String.valueOf(sortedIngredients.get(i).quantity) + " " 
            						 + sortedIngredients.get(i).units);

            /*LinearLayout.LayoutParams column1Params = new LinearLayout.LayoutParams(
                    0, LayoutParams.MATCH_PARENT, 2.0f);
            LinearLayout.LayoutParams column2Params = new LinearLayout.LayoutParams(
                    0, LayoutParams.MATCH_PARENT, 1.0f);
            tv1.setLayoutParams(column1Params);
            tv2.setLayoutParams(column2Params);*/

            tv1.setTypeface(null, 1);
            tv2.setTypeface(null, 1);
 
            tv1.setTextSize(15);
            tv2.setTextSize(15);

            tv1.setPadding(10*dip, 10*dip, 10*dip, 10*dip);
            tv2.setPadding(10*dip, 10*dip, 20*dip, 10*dip);
            //tv1.setSingleLine(false);
            
            row.addView(tv1);
            row.addView(tv2);
 
            table.addView(row, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
 
        }
	}
}
