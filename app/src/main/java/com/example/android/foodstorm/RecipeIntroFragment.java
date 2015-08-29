package com.example.android.foodstorm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;

public class RecipeIntroFragment extends Fragment {
    private TextView tv_title;
    private ImageView iv_foodPic;
    private TextView tv_description;
    private TextView tv_cooktime;
    private TextView tv_difficulty;
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

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}