package com.example.foodstorm.enterfoods;

import java.util.ArrayList;

import com.example.foodstorm.R;
import com.example.foodstorm.fridge.FridgeFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TypeFoodNameFragment extends Fragment 
{
	public final static String TAG = "TypeFoodNameFragment";
	private EditText editText;
	private Button addFoodButton;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
        View rootView = inflater.inflate(R.layout.fragment_typefoodname, container, false);
        this.editText = (EditText) rootView.findViewById(R.id.editText1);
        this.addFoodButton = (Button) rootView.findViewById(R.id.button1);
        this.addFoodButton.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
            	// TEMP: this static list is just a temp solution.
                if(FridgeFragment.ingredientNames == null) 
                {
                	FridgeFragment.ingredientNames = new ArrayList<String>();
                }
                
                String input = editText.getText().toString();
                
                if(!input.isEmpty()) // TODO: && keyword match to Azure
                {
                	FridgeFragment.ingredientNames.add(editText.getText().toString());
                	Toast.makeText(v.getContext(), input + " added to fridge after checking it exists in Azure.", Toast.LENGTH_SHORT).show();
                } 
                else 
                {
                	Toast.makeText(v.getContext(), "You can't add a blank food name!", Toast.LENGTH_SHORT);
                }
                
                
            }
        });
        return rootView;
    }
}
