package com.example.android.foodstorm;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDirectionsFragment extends Fragment implements SensorEventListener {
	private LinearLayout linear_layout;
	private DirectionCardAdapter directionCardAdapter;

	/* for scrolling with proximity sensor */
	private SensorManager sensorManager;
	private Sensor sensor;
	private int selectedDirectionIndex;
	private ArrayList<View> directionViews;
	private ObservableScrollView directionsScrollView;

	private TextView debugTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recipe_directions, container, false);
		directionsScrollView = (ObservableScrollView)rootView.findViewById(R.id.directions_scroll_view);
		debugTextView = (TextView)rootView.findViewById(R.id.recipe_directions_debug);

		/* Initialize proximity sensor, to let the user scroll without
		   touching the screen (i.e., when hands messy with cooking)
		 */
		sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(sensor.TYPE_PROXIMITY);
		selectedDirectionIndex = 0;

		directionViews = new ArrayList<View>();
		directionCardAdapter = new DirectionCardAdapter(this.getActivity(), RecipeFragmentAdapter.RECIPE.directions);
		linear_layout = (LinearLayout) rootView.findViewById(R.id.linear_layout);
		for (int i = 0; i < RecipeFragmentAdapter.RECIPE.directions.size(); i++) {
			View directionView = directionCardAdapter.getView(i, null, linear_layout);
			directionViews.add(directionView);
			linear_layout.addView(directionView);
		}
		directionsScrollView.setScrollViewListener(new DirectionsScrollView());
		return rootView;
	}

	public void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	private void scrollToNextDirection(){
		View target;
		TextView directionText;

		if(selectedDirectionIndex + 1 >= directionViews.size()) return;
		selectedDirectionIndex++;
		target = directionViews.get(selectedDirectionIndex);

		/* get view position relative to parent, then scroll there
		* really hope possibly getting clipped by the ScrollView doesn't
		* screw up getTop. */
		directionsScrollView.scrollTo(0, target.getTop());
		// give the current direction a blue background (and keep the rest white)
		for(View view : directionViews){
			view.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
			directionText = (TextView)view.findViewById(R.id.direction_card_description);
			directionText.setTextColor(Color.parseColor("#090909")); // should match text view in direction card layout
		}
		target.getBackground().setColorFilter(Color.parseColor("#2FB6F4"), PorterDuff.Mode.DARKEN);
		directionText = (TextView)target.findViewById(R.id.direction_card_description);
		directionText.setTextColor(Color.parseColor("#FFFFFF"));    // white on blue
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

	private class DirectionsScrollView implements ScrollViewListener {
		public void onScrollChanged(int x, int y, int oldx, int oldy) {
			TextView directionText;
			int currentDirection = 0;
			// give the current direction a blue background (and keep the rest white)
			for(View view : directionViews){
				view.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
				directionText = (TextView)view.findViewById(R.id.direction_card_description);
				directionText.setTextColor(Color.parseColor("#090909")); // should match text view in direction card layout
			}
			for(View view : directionViews) {
				currentDirection++;
				if(y >= view.getTop() && y <= view.getBottom()) {
					selectedDirectionIndex = currentDirection - 1;
					view.getBackground().setColorFilter(Color.parseColor("#2FB6F4"), PorterDuff.Mode.DARKEN);
					directionText = (TextView)view.findViewById(R.id.direction_card_description);
					directionText.setTextColor(Color.parseColor("#FFFFFF"));    // white on blue
				}
			}
		}
	}
}