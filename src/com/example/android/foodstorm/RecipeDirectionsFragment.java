package com.example.android.foodstorm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeDirectionsFragment extends Fragment {
	private LinearLayout linear_layout;
 	private DirectionCardAdapter directionCardAdapter;
 	
 	@Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     	View rootView = inflater.inflate(R.layout.fragment_recipe_directions, container, false);

    	directionCardAdapter = new DirectionCardAdapter(this.getActivity(), RecipeFragmentAdapter.RECIPE.directions);
     	linear_layout = (LinearLayout) rootView.findViewById(R.id.linear_layout);     		
     	for(int i = 0; i < RecipeFragmentAdapter.RECIPE.directions.size(); i++) {
     		linear_layout.addView(directionCardAdapter.getView(i, null, linear_layout));
     	}
     	return rootView;
     }
}
