package com.example.android.foodstorm;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.List;
import android.content.Context;

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
		ViewHolder holder;
		
		if(cView != null) holder = (ViewHolder) cView.getTag();
		else {
			cView = LayoutInflater.from(context).inflate(R.layout.direction_card_layout, parent, false);
			holder = new ViewHolder();
			holder.directionIndex = (TextView) cView.findViewById(R.id.direction_card_index);
			holder.directionDescription = (TextView) cView.findViewById(R.id.direction_card_description);
			cView.setTag(holder);
		}
		if(idx == 0) {
			LinearLayout.LayoutParams cardLayoutParams = new  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			cardLayoutParams.setMargins(10, 10, 10, 5);
			cView.setLayoutParams(cardLayoutParams);
		}
		holder.directionIndex.setText(String.valueOf(idx));
		holder.directionDescription.setText(directions.get(idx));
		return cView;
	}
	
	private static class ViewHolder {
		private TextView directionIndex;
		private TextView directionDescription;
	}
}