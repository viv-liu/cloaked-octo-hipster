package com.example.foodstorm.enterfoods;

import com.example.foodstorm.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class EnterFoodMainFragment extends Fragment 
{
	public final static String TAG = "EnterFoodMainFragment";
	private Button ocrButton;
	private Button barcodeButton;
	private Button typeButton;
	public EnterFoodMainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enterfoodsmain, container, false);
        this.ocrButton = (Button) rootView.findViewById(R.id.button1);
        this.barcodeButton = (Button) rootView.findViewById(R.id.button2);
        this.typeButton = (Button) rootView.findViewById(R.id.button3);
        
        this.ocrButton.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v) {
            	// Fragment -> Fragment/Activity, camera and OCR controlled
                Toast.makeText(v.getContext(), "Navigate to ocr screen", Toast.LENGTH_SHORT).show();
            }
        });
        this.barcodeButton.setOnClickListener(new View.OnClickListener() 
        {
        	// Fragment list
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Navigate to barcide scanning", Toast.LENGTH_SHORT).show();
            }
        });
        this.typeButton.setOnClickListener(new View.OnClickListener() 
        {
            // Fragment list -> Activity view pager tabs
        	@Override
            public void onClick(View v) 
        	{
        		TypeFoodNameFragment f = new TypeFoodNameFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();        
                ft.replace(R.id.container, f, TypeFoodNameFragment.TAG);
                ft.addToBackStack(TypeFoodNameFragment.TAG);
                ft.commit();
            }
        });

        return rootView;
    }
}
