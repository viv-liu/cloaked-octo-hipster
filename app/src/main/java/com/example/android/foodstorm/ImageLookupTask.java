/* Ultimate class clutter - have a whole new class just
 * to retrieve images from the web and make them android bitmaps
  * ugh, why is this not built in somehow*/
package com.example.android.foodstorm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageLookupTask extends AsyncTask<String, String, Bitmap>{

	public RecipeItem recipe;
	public MainActivity.RecipesFragment host;

	@Override
	protected Bitmap doInBackground(String... url) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(new HttpGet(url[0]));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream input = response.getEntity().getContent();
				return BitmapFactory.decodeStream(input);
			}
		} catch(IOException e){
			return null;
		}
		return null;
	}
	
	protected void onPostExecute(Bitmap result) {
		if(result == null) {
			recipe.image = R.drawable.chef_hat;
			return;
		}
		recipe.imageBitmap = result;
		recipe.image = -1;
		host.refreshListView();
	}
}
