/* Looks up a list of recipes based on foods available */
package com.example.android.foodstorm;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RecipesLookupTask extends AsyncTask<List<Integer>, String, String>{

	public MainActivity.RecipesFragment host;

	@Override
	protected String doInBackground(List<Integer>... ids) {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://foodstormfun.cloudapp.net/get_recipe_by_foods.php?foodIds=";
		try {
			boolean first = true;
			for(Integer i : ids[0]){
				if(! first) url += "_";
				else first = false;
				url += i;
			}
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
		String[] recipeStrings;
		String[] temp;
		String recipeTitle, recipeDescription, recipeImage;
		int recipeId;
		RecipeItem recipe;
		ArrayList<RecipeItem> recipes = new ArrayList<RecipeItem>();
		
		if(result.indexOf("No result") != -1){
			host.updateListView(recipes);
			return;
		}
		recipeStrings = result.split("<another_recipe>");
		for(int i = 0;i < recipeStrings.length;i++) {
			if(recipeStrings[i].length() > 0){
				temp = recipeStrings[i].split("<end_id>");
				if(temp.length < 2) continue; // must have more than just id
				recipeId = Integer.parseInt(temp[0]);
				temp = temp[1].split("<end_title>");
				if(temp.length < 2) continue; // must have more than just title
				recipeTitle = temp[0];
				temp = temp[1].split("<end_description>");
				if(temp.length < 2) continue;
				recipeDescription = temp[0];
				temp = temp[1].split("<end_img>");
				recipeImage = temp[0];
				recipe = new RecipeItem(recipeTitle, recipeDescription);
				recipe.id = recipeId;
				recipe.image = R.drawable.chef_hat;

				recipe.directions = new ArrayList<String>();
				temp = temp[1].split("<end_instructions>");
				if(temp.length < 2) continue; // must have ingredients after this
				String[] directions_arr = temp[0].split("<another_instruction>");
				for(String direction : directions_arr){
					if(direction.length() > 0) recipe.directions.add(direction);
				}

				// all that's left in temp[1] now should be ingredients
				recipe.ingredients = new ArrayList<FoodItem>();
				String[] ingredients_arr = temp[1].split("<another_ingredient>");
				for(String ingredient : ingredients_arr){
					String[] ingredientNameSplit = ingredient.split("<end_ingr_name>");
					if(ingredientNameSplit.length < 2) continue;
					String ingredientName = ingredientNameSplit[0];
					String[] ingredientQtySplit = ingredientNameSplit[1].split("<end_ingr_quantity>");
					if(ingredientQtySplit.length < 2) continue;
					String ingredientQty = ingredientQtySplit[0];
					String ingredientUnits = ingredientQtySplit[1];

					FoodItem ingr = new FoodItem(ingredientName, null,
							R.drawable.chef_hat, false,
							Float.parseFloat(ingredientQty),
							ingredientUnits);
					recipe.ingredients.add(ingr);
				}

				// apparently this happens in the main thread so find images in separate thread
				// and very inefficiently re-draw the recipes view when each image is loaded -.-
				/*ImageLookupTask requestTask = new ImageLookupTask();
				requestTask.host = host;
				requestTask.recipe = recipe;
				requestTask.execute(recipeImage);*/

				recipe.imageUrl = recipeImage;

				recipes.add(recipe);
			}
		}

		host.updateListView(recipes);
	}
}
