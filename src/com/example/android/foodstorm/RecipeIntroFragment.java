package com.example.android.foodstorm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class RecipeIntroFragment extends Fragment {
	private TextView tv_title;
	private ImageView iv_foodPic;
	private TextView tv_description;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_recipe_intro, container, false);
    	
    	tv_title = (TextView) rootView.findViewById(R.id.textView1);
    	tv_title.setText(RecipeFragmentAdapter.RECIPE.title);
    	
		iv_foodPic = (ImageView) rootView.findViewById(R.id.imageView1);
		if(RecipeFragmentAdapter.RECIPE.image > 0) {
			iv_foodPic.setImageResource(RecipeFragmentAdapter.RECIPE.image);
		} else {
			iv_foodPic.setImageBitmap(RecipeFragmentAdapter.RECIPE.imageBitmap);
		}
		
		tv_description = (TextView) rootView.findViewById(R.id.textView2);
		tv_description.setText(RecipeFragmentAdapter.RECIPE.description);
		
		return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
