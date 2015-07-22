package com.example.android.foodstorm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class RecipeFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "Intro", "Ingredients", "Directions" };
    protected static final int[] ICONS = new int[] {
            R.drawable.perm_group_description,
            R.drawable.perm_group_ingredients,
            R.drawable.perm_group_directions
    };
    public static RecipeItem RECIPE;

    private int mCount = CONTENT.length;
    
    private RecipeIntroFragment recipeIntroFragment;
    private RecipeIngredientsFragment recipeIngredientsFragment;
    private RecipeDirectionsFragment recipeDirectionsFragment;
    
    public RecipeFragmentAdapter(FragmentManager fm, RecipeItem r) {
        super(fm);
        RECIPE = r;
        recipeIntroFragment = new RecipeIntroFragment();
        recipeIngredientsFragment = new RecipeIngredientsFragment();
        recipeDirectionsFragment = new RecipeDirectionsFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
        case 0:
        	return recipeIntroFragment;
        case 1:
        	return recipeIngredientsFragment;
        case 2:
        	return recipeDirectionsFragment;
        default:
        	return recipeIntroFragment;
        }
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return RecipeFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}