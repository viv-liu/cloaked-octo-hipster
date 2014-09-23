package com.example.foodstorm;

import com.example.foodstorm.enterfoods.EnterFoodMainFragment;
import com.example.foodstorm.fridge.FridgeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener
{
	public final static String TAG = "HomeFragment";
	private ImageButton enterFoodImageButton;
	private ImageButton fridgeImageButton;
	private ImageButton matchImageButton;
	private ImageButton browseImageButton;
	
	public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        enterFoodImageButton = (ImageButton) rootView.findViewById(R.id.imageButton1);
        fridgeImageButton = (ImageButton) rootView.findViewById(R.id.imageButton2);
        matchImageButton = (ImageButton) rootView.findViewById(R.id.imageButton3);
        browseImageButton = (ImageButton) rootView.findViewById(R.id.imageButton4);
        enterFoodImageButton.setOnClickListener(this);
        fridgeImageButton.setOnClickListener(this);
        matchImageButton.setOnClickListener(this); 
        browseImageButton.setOnClickListener(this); 
        return rootView;
    }
    @Override
    public void onClick(View v) 
    {
    	FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();  
        // Navigate to appropriate fragments
        switch (v.getId()) 
        {
            case R.id.imageButton1:
            	EnterFoodMainFragment enterF = new EnterFoodMainFragment();                      
                ft.replace(R.id.container, enterF, EnterFoodMainFragment.TAG);
                ft.addToBackStack(EnterFoodMainFragment.TAG);
                break;
            case R.id.imageButton2:
            	FridgeFragment fridgeF = new FridgeFragment();   
                ft.replace(R.id.container, fridgeF, FridgeFragment.TAG);
                ft.addToBackStack(FridgeFragment.TAG);
                break;
            case R.id.imageButton3:
            	Toast.makeText(v.getContext(), "Navigate to matched foods list", Toast.LENGTH_SHORT).show();
            	break;
            case R.id.imageButton4:
            	Toast.makeText(v.getContext(), "Navigate to popular recipes users like. (with navigation drawers here!)", Toast.LENGTH_SHORT).show();
            	break;
        }
        ft.commit();
    }
}
