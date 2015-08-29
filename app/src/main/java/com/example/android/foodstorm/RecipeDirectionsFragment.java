package com.example.android.foodstorm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDirectionsFragment extends Fragment implements SensorEventListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
	private LinearLayout linear_layout;
	private DirectionCardAdapter directionCardAdapter;

	/* for scrolling with proximity sensor */
	private SensorManager sensorManager;
	private Sensor sensor;
	private int selectedDirectionIndex;
	private ArrayList<View> directionViews;
	private ObservableScrollView directionsScrollView;

	private Switch noTouchSwitch;
	private TextView debugTextView;

	/* onCreateView:
	   - init class view variables
	   - init proximity sensor
	   - create direction card views
	   - init scroll view listener (to infer active direction from scroll position)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recipe_directions, container, false);
		directionsScrollView = (ObservableScrollView)rootView.findViewById(R.id.directions_scroll_view);
		debugTextView = (TextView)rootView.findViewById(R.id.recipe_directions_debug);

		/* Initialize proximity sensor, to let the user scroll without
		   touching the screen (i.e., when hands messy with cooking) */
		sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		selectedDirectionIndex = 0;

		noTouchSwitch = (Switch) rootView.findViewById(R.id.switch1);
		noTouchSwitch.setOnCheckedChangeListener(this);
		noTouchSwitch.setChecked(true);
		
		directionViews = new ArrayList<View>();
		directionCardAdapter = new DirectionCardAdapter(this.getActivity(), RecipeFragmentAdapter.RECIPE.directions);
		linear_layout = (LinearLayout) rootView.findViewById(R.id.linear_layout);
		for (int i = 0; i < RecipeFragmentAdapter.RECIPE.directions.size(); i++) {
			View directionView = directionCardAdapter.getView(i, null, linear_layout);
			directionView.setOnClickListener(this);
			directionViews.add(directionView);
			linear_layout.addView(directionView);
		}
		directionsScrollView.setScrollViewListener(new DirectionsScrollView());
		return rootView;
	}

	public void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	private void scrollToNextDirection(){
		if(selectedDirectionIndex + 1 >= directionViews.size()) return;
		selectedDirectionIndex++;
		highlightDirection(selectedDirectionIndex);

		View target = directionViews.get(selectedDirectionIndex);
		directionsScrollView.scrollTo(0, target.getTop());
		/*if(target.getTop() < directionsScrollView.getHeight()/2) {
			return;
		}
		directionsScrollView.scrollTo(0, target.getTop() - directionsScrollView.getHeight()/2);*/
	}

	private void highlightDirection(int index){
		TextView directionText;
		int i = 0;
		for(View view : directionViews){
			if(i == index){
				view.getBackground().setColorFilter(Color.parseColor("#2FB6F4"), PorterDuff.Mode.DARKEN);
				directionText = (TextView)view.findViewById(R.id.direction_card_description);
				directionText.setTextColor(Color.parseColor("#FFFFFF"));
			} else {
				view.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
				directionText = (TextView) view.findViewById(R.id.direction_card_description);
				directionText.setTextColor(Color.parseColor("#999999")); // should match text view in direction card layout
			}
			i++;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy){}

	public void onSensorChanged(SensorEvent event){
		if(event.values[0] == 0){
			scrollToNextDirection();
			String debugOutput = "Proximity close -> direction idx = " +  selectedDirectionIndex;
			debugTextView.setText(debugOutput.toCharArray(), 0, debugOutput.length());
		} else {
			String debugOutput = "Proximity far";
			debugTextView.setText(debugOutput.toCharArray(), 0, debugOutput.length());
		}
	}

	/* handles card clicks */
	public void onClick(View v){
		DirectionViewHolder holder = (DirectionViewHolder)v.getTag();
		selectedDirectionIndex = holder.directionId;
		highlightDirection(selectedDirectionIndex);
		View ingredientList = v.findViewById(R.id.ingredient_list);

		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(ingredientList, 500);

		// Start the animation on the toolbar
		ingredientList.startAnimation(expandAni);
	}

	/* Hooks onto scroll view, updating the currently selected direction
	   whenever scrolling happens
	 */
	private class DirectionsScrollView implements ScrollViewListener {
		public void onScrollChanged(int x, int y, int oldx, int oldy) {
			int currentDirection = 0;
			for(View view : directionViews) {
				currentDirection++;
				if(y >= view.getTop() && y <= view.getBottom()) {
					selectedDirectionIndex = currentDirection - 1;
					highlightDirection(selectedDirectionIndex);
					break; // shouldn't highlight more than one direction
				}
			}
		}
	}

	/*
	 * Switch turns on and off proximity sensor listener.
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked) {
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			sensorManager.unregisterListener(this);
		}
	}
}