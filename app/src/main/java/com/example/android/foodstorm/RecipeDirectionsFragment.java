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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.CompoundButton;
import java.util.ArrayList;
import android.util.DisplayMetrics;

public class RecipeDirectionsFragment extends Fragment implements SensorEventListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
	private LinearLayout linear_layout;
    private RelativeLayout header_relative_layout;
	private DirectionCardAdapter directionCardAdapter;

	/* for scrolling with proximity sensor */
	private SensorManager sensorManager;
	private Sensor sensor;
	private int selectedDirectionIndex = 0;
	private ArrayList<View> directionViews;
	private ObservableScrollView directionsScrollView;

	private TextView debugTextView;
    private TextView backToTopTextView;
    private int defaultTextViewColor;

    private Switch noTouchSwitch;
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
        backToTopTextView = (TextView)rootView.findViewById(R.id.textView3);
        defaultTextViewColor = backToTopTextView.getCurrentTextColor();

		/* Initialize proximity sensor, to let the user scroll without
		   touching the screen (i.e., when hands messy with cooking) */
		sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(sensor.TYPE_PROXIMITY);
		selectedDirectionIndex = 0;

        noTouchSwitch = (Switch) rootView.findViewById(R.id.switch1);
        noTouchSwitch.setOnCheckedChangeListener(this);
        noTouchSwitch.setChecked(false);

		directionViews = new ArrayList<View>();
		directionCardAdapter = new DirectionCardAdapter(this.getActivity(), RecipeFragmentAdapter.RECIPE.directions);
		linear_layout = (LinearLayout) rootView.findViewById(R.id.linear_layout);
        header_relative_layout = (RelativeLayout) rootView.findViewById(R.id.header_relative_layout);

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
		if(noTouchSwitch.isChecked()) {
			sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	public void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

    /* Scrolls to next direction, highlighting the current direction in the middle of the screen */
	private void scrollToNextDirection() {
		if(selectedDirectionIndex + 1 >= directionViews.size()) {
            if(backToTopTextView.getCurrentTextColor() == defaultTextViewColor) {
                backToTopTextView.setTextColor(Color.parseColor("#2FB6F4"));
                highlightDirection(0); // to clear highlight from last card
                return;
            } else if (backToTopTextView.getCurrentTextColor() == Color.parseColor("#2FB6F4")) {
                backToTopTextView.setTextColor(defaultTextViewColor);
                selectedDirectionIndex = -1; // to scroll to first direction
            }
        }

		selectedDirectionIndex++;
		highlightDirection(selectedDirectionIndex);

        View target = directionViews.get(selectedDirectionIndex);

        int halfScrollViewHeight = directionsScrollView.getHeight()/2;
        int headerOffset = header_relative_layout.getHeight();
        int footerOffset = 30; // to represent the footer icon panel at the bottom

        /* targetScrollY is the y coordinate to scroll to, with the top of the card slightly higher than
           the center of the screen */
        int targetScrollY = target.getTop() - halfScrollViewHeight + headerOffset + footerOffset;

        directionsScrollView.smoothScrollTo(0, targetScrollY);
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

	/* Handles card clicks. Card clicks are only handled if no-touch mode is disabled*/
	public void onClick(View v){
		if(!noTouchSwitch.isChecked()) {
			DirectionViewHolder holder = (DirectionViewHolder) v.getTag();
			selectedDirectionIndex = holder.directionId;
			highlightDirection(selectedDirectionIndex);
		}
	}

	/* Hooks onto scroll view, updating the currently selected direction
	   whenever scrolling happens
	 */
	private class DirectionsScrollView implements ScrollViewListener {
		public void onScrollChanged(int x, int y, int oldx, int oldy) {
			TextView directionText;
			int currentDirection = 0;
			/*for(View view : directionViews) {
				currentDirection++;
				if(y + halfScrollHeight  >= view.getTop() && y + halfScrollHeight <= view.getBottom()) {
			        selectedDirectionIndex = currentDirection - 1;
					highlightDirection(selectedDirectionIndex);
					break; // shouldn't highlight more than one direction
				}
			}*/
		}
	}

    /*
	 * Switch turns on and off proximity sensor listener. Highlights first direction.
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            highlightDirection(selectedDirectionIndex);
        } else {
            sensorManager.unregisterListener(this);
        }
    }
}