/* Used in food adding activity
* Looks up food by barcode
 * instead of reporting back to AddActivity,
 * this one directly adds the food.
 *
 * A little messy, yes, but putting the user through
 * the AddActivity list interface isn't necessary if there's
 * just one food per barcode (which I hope is the case) */
package com.example.android.foodstorm;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BarcodeLookupTask extends AsyncTask<String, String, String>{

	public MainActivity host; // unfortunately needed because toast needs a context

	@Override
	protected String doInBackground(String... code) {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://foodstormfun.cloudapp.net/get_food_by_barcode.php?code=";
		try {
			url += URLEncoder.encode(code[0], "UTF-8");
			HttpResponse response = httpClient.execute(new HttpGet(url));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				return out.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String result) {
		String[] foodNames;
		String foodName;
		int foodId, idNameSplitPoint;
		ArrayList<FoodItem> foods = new ArrayList<FoodItem>();
		FoodItem foodToAdd;
		FoodSQLiteHelper dataSource;
		
		if(result.indexOf("No result") != -1) {
			Toast.makeText(host, "We have no idea what that is! :(", Toast.LENGTH_SHORT).show();
			return;
		}
		
		foodNames = result.split("\n");
		for(int i = 0;i < foodNames.length;i++) {
			if(foodNames[i].length() < 1) continue;
			idNameSplitPoint = foodNames[i].indexOf(',');
			foodId = Integer.parseInt(foodNames[i].substring(0, idNameSplitPoint));
			foodName = foodNames[i].substring(idNameSplitPoint + 1);
			foods.add(new FoodItem(foodId, foodName, FoodGroup.PRODUCE, R.drawable.chef_hat, false, 2.4f, "units"));
		}

		// just add the first one. There should not be more than one result per barcode.
		foodToAdd = foods.get(0);

		dataSource = new FoodSQLiteHelper(host);
		if(dataSource.addFood(foodToAdd) == 0) {
			Toast.makeText(host, foodToAdd.name + " added!", Toast.LENGTH_SHORT).show();
			MainActivity.FridgeFragment.fridgeListChanged = true;
		} else Toast.makeText(host, "Add failed!", Toast.LENGTH_SHORT).show();
	}
}
