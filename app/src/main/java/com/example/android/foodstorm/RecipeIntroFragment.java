package com.example.android.foodstorm;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeIntroFragment extends Fragment {
    private TextView tv_title;
    private ImageView iv_foodPic;
    private TextView tv_description;
    private TextView tv_cooktime;
    private TextView tv_difficulty;
    private TableLayout tl_ingredients;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_intro, container, false);

        tv_title = (TextView) rootView.findViewById(R.id.textView1);
        tv_title.setText(RecipeFragmentAdapter.RECIPE.title);
        tv_cooktime=(TextView) rootView.findViewById(R.id.tv_cooktime);
        tv_cooktime.setText(String.valueOf(RecipeFragmentAdapter.RECIPE.minutes) + " min");
        tv_difficulty = (TextView) rootView.findViewById(R.id.tv_difficulty);
        switch(RecipeFragmentAdapter.RECIPE.difficulty) {
            case 0: tv_difficulty.setText("Easy"); break;
            case 1: tv_difficulty.setText("Intermediate"); break;
            case 2: tv_difficulty.setText("Hard"); break;
            default: tv_difficulty.setText("Impossibru"); break;
        }

        if(RecipeFragmentAdapter.RECIPE.title.length() > 25) tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        iv_foodPic = (ImageView) rootView.findViewById(R.id.imageView1);
        if(RecipeFragmentAdapter.RECIPE.imageUrl != null)
            //iv_foodPic.setImageBitmap(RecipeFragmentAdapter.RECIPE.imageBitmap);
            Picasso.with(this.getActivity())
                    .load(RecipeFragmentAdapter.RECIPE.imageUrl)
                    .error(R.drawable.chef_hat)
                    .into(iv_foodPic);

        tv_description = (TextView) rootView.findViewById(R.id.textView2);
        tv_description.setText(RecipeFragmentAdapter.RECIPE.description);

        tl_ingredients = (TableLayout) rootView.findViewById(R.id.ingredientsTable);
        fillIngredientsTable(tl_ingredients, RecipeFragmentAdapter.RECIPE.ingredients);

        return rootView;
    }

    private void fillIngredientsTable(TableLayout table, List<FoodItem> sortedIngredients) {

        TableRow row;
        TextView ingredient_tv, quantity_tv;

        // For converting values to dip units
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int i = 0; i < sortedIngredients.size(); i++) {
            row = new TableRow(this.getActivity());

            // Give rows alternating background colors
            if(i%2 == 0) {
                //row.setBackgroundColor(Color.LTGRAY);
            } else {
                row.setBackgroundColor(Color.LTGRAY);
            }

            ingredient_tv = new TextView(this.getActivity()); // ingredient name
            quantity_tv = new TextView(this.getActivity()); // quantity, unit

            IngredientTextViewTag textInfo = new IngredientTextViewTag();
            textInfo.parentRow = row;
            textInfo.fullText = sortedIngredients.get(i).name;
            textInfo.truncatedText = null;
            textInfo.expanded = false;
            textInfo.bottomMargin = 5 * dip; // must match padding set later

            // Truncate name if it's too long, so the quantity doesn't get shoved off
            if(sortedIngredients.get(i).name.length() > 40){
                String truncatedText = sortedIngredients.get(i).name.substring(0, 40) + "...";
                ingredient_tv.setText(truncatedText);
                textInfo.truncatedText = truncatedText;
                textInfo.fullText = sortedIngredients.get(i).name.substring(0, 40) + "\n" +
                        sortedIngredients.get(i).name.substring(40);
            } else
                ingredient_tv.setText(sortedIngredients.get(i).name);

            quantity_tv.setText(String.valueOf(sortedIngredients.get(i).quantity) + " "
                    + sortedIngredients.get(i).units);
            
            ingredient_tv.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
            quantity_tv.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);

            // Give rows some top and bottom padding, so they're not so squished
            // padding order = left, top, right, bottom
            ingredient_tv.setPadding(0, 5*dip, 0, 5*dip); // ingredient name
            quantity_tv.setPadding(0, 5*dip, 0, 5*dip); // quantity, unit

            ingredient_tv.setOnClickListener(new IngredientClickListener());
            ingredient_tv.setTag(textInfo);

            row.addView(ingredient_tv);
            row.addView(quantity_tv);

            table.addView(row, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 20));

        }
    }

    private class IngredientTextViewTag {
        public TableRow parentRow;
        public String truncatedText;
        public String fullText;
        public boolean expanded; // used to track animation
        public int bottomMargin; // aids animation class in figuring out when to stop and which way to go
    }

    /* Private class for handling ingredient clicks
       For now, this will only be used for expanding items too
       long to be shown (without shoving off the quantity display)
     */
    private class IngredientClickListener implements View.OnClickListener {
        public void onClick(View v){

            IngredientTextViewTag textInfo = (IngredientTextViewTag)v.getTag();
            TextView ingredient_tv = (TextView)v;
            if(textInfo != null && textInfo.truncatedText != null){
                if(textInfo.expanded) {
                    ingredient_tv.setText(textInfo.truncatedText);
                    textInfo.expanded = false;
                } else {
                    ingredient_tv.setText(textInfo.fullText);
                    textInfo.expanded = true;
                }
                ExpandAnimation expandAni = new ExpandAnimation(ingredient_tv, 100, false, textInfo.bottomMargin);
                ingredient_tv.startAnimation(expandAni);
            }
        }
    }
}