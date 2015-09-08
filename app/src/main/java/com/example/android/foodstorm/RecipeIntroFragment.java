package com.example.android.foodstorm;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        LinearLayout row;
        TextView tv1, tv2;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int i = 0; i < sortedIngredients.size(); i++) {
            row = new LinearLayout(this.getActivity());

            // TODO: not sure if these params do anything noticeable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(20 * dip, 1, 20 * dip, 1);
            row.setLayoutParams(params);

            if(i%2 == 0) {
                //row.setBackgroundColor(Color.LTGRAY);
            } else {
                row.setBackgroundColor(Color.LTGRAY);
            }
            tv1 = new TextView(this.getActivity()); // ingredient name
            tv2 = new TextView(this.getActivity()); // quantity, unit

            tv1.setText(sortedIngredients.get(i).name);
            tv2.setText(String.valueOf(sortedIngredients.get(i).quantity) + " "
                    + sortedIngredients.get(i).units);

            tv1.setTypeface(null, Typeface.NORMAL);
            tv2.setTypeface(null, Typeface.NORMAL);

            tv1.setTextSize(15);
            tv2.setTextSize(15);

            tv1.setPadding(0 * dip, 10 * dip, 0 * dip, 10 * dip); // ingredient name
            tv2.setPadding(0*dip, 10*dip, 10*dip, 10*dip); // quantity, unit

            // Assign layout gravities to give units some fixed space
            ViewGroup.LayoutParams rowLayoutParams = row.getLayoutParams();
            LinearLayout.LayoutParams tv1LayoutParams = new LinearLayout.LayoutParams(rowLayoutParams);
            tv1LayoutParams.gravity = Gravity.LEFT;
            tv1LayoutParams.weight = 9;
            tv1.setLayoutParams(tv1LayoutParams);

            LinearLayout.LayoutParams tv2LayoutParams = new LinearLayout.LayoutParams(rowLayoutParams);
            tv2LayoutParams.gravity = Gravity.LEFT;
            tv2LayoutParams.weight = 1;
            tv2.setLayoutParams(tv2LayoutParams);

            row.addView(tv1);
            row.addView(tv2);

            table.addView(row, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        }
    }
}