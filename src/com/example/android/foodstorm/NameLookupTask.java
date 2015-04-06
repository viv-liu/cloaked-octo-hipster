/* Used in food adding activity */
package com.example.android.foodstorm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class NameLookupTask extends AsyncTask<String, String, String>{
	
	public AddActivity host;

	@Override
	protected String doInBackground(String... code) {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://foodstormfun.cloudapp.net/get_food_by_name.php?name=";
		try {
			url += URLEncoder.encode(code[0], "UTF-8");
			HttpResponse response = httpClient.execute(new HttpGet(url));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				
				
				return out.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String result) {
		String[] foodNames;
		ArrayList<FoodItem> foods = new ArrayList<FoodItem>();
		
		if(result.indexOf("No result") != -1){
			host.updateListView(foods);
			return;
		}
		
		foodNames = result.split("\n");
		for(int i = 0;i < foodNames.length;i++) {
			foods.add(new FoodItem(foodNames[i], FoodGroup.PRODUCE, R.drawable.chef_hat, false, 2.4f, "units"));
		}
		host.updateListView(foods);
	}
}
