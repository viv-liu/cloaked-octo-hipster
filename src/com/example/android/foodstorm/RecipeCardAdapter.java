package com.example.android.foodstorm;

import com.example.android.effectivenavigation.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.List;
import android.content.Context;

public class RecipeCardAdapter extends BaseAdapter {
	
	private List<RecipeItem> items;
	private final Context context;
	
	public RecipeCardAdapter(Context context, List<RecipeItem> items){
		this.context = context;
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int idx) {
		return items.get(idx);
	}

	@Override
	public long getItemId(int idx) {
		return idx;
	}

	@Override
	public View getView(int idx, View cView, ViewGroup parent) {
		ViewHolder holder;
		
		if(cView != null) holder = (ViewHolder) cView.getTag();
		else {
			cView = LayoutInflater.from(context).inflate(R.layout.recipe_card_layout, parent, false);
			holder = new ViewHolder();
			holder.recipeTitle = (TextView) cView.findViewById(R.id.recipe_card_title);
			holder.recipeDescription = (TextView) cView.findViewById(R.id.recipe_card_description);
			holder.recipeImage = (ImageView) cView.findViewById(R.id.recipe_card_image);
			cView.setTag(holder);
		}
		holder.recipeTitle.setText(items.get(idx).title);
		holder.recipeDescription.setText(items.get(idx).description);
		holder.recipeImage.setImageResource(items.get(idx).image);
		return cView;
	}
	
	private static class ViewHolder {
		private TextView recipeTitle;
		private TextView recipeDescription;
		private ImageView recipeImage;
	}
}
