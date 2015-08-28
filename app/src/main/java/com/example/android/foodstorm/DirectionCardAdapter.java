package com.example.android.foodstorm;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class DirectionCardAdapter extends BaseAdapter {
	
	private List<String> directions;
	private final Context context;
	
	public DirectionCardAdapter(Context context, List<String> d){
		this.context = context;
		this.directions = d;
	}
	
	@Override
	public int getCount() {
		return directions.size();
	}

	@Override
	public Object getItem(int idx) {
		return directions.get(idx);
	}

	@Override
	public long getItemId(int idx) {
		return idx;
	}

	@Override
	public View getView(int idx, View cView, ViewGroup parent) {
		DirectionViewHolder holder;
		
		if(cView != null) holder = (DirectionViewHolder) cView.getTag();
		else {
			cView = LayoutInflater.from(context).inflate(R.layout.direction_card_layout, parent, false);
			holder = new DirectionViewHolder();
			holder.directionIndex = (TextView) cView.findViewById(R.id.direction_card_index);
			holder.directionDescription = (TextView) cView.findViewById(R.id.direction_card_description);

			LinearLayout ingredientList = (LinearLayout) cView.findViewById(R.id.ingredient_list);
			((LinearLayout.LayoutParams) ingredientList.getLayoutParams()).bottomMargin = -50;

			TextView tv = new TextView(context);
			tv.setText("Potatoes");
			TextView tv1 = new TextView(context);
			tv1.setText("Bean");
			ingredientList.addView(tv);
			ingredientList.addView(tv1);

			ingredientList.setVisibility(View.GONE);

			holder.directionId = idx;
			cView.setTag(holder);
		}
		/*if(idx == 0) {
			LinearLayout.LayoutParams cardLayoutParams = new  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			cardLayoutParams.setMargins(10, 10, 10, 5);
			cView.setLayoutParams(cardLayoutParams);
		}*/
		holder.directionIndex.setText(String.valueOf(idx + 1));
		holder.directionDescription.setText(directions.get(idx));
		return cView;
	}
}