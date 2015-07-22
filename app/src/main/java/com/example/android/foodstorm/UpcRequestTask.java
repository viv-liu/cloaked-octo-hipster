package com.example.android.foodstorm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class UpcRequestTask extends AsyncTask<String, String, String>{
	
	public Context appContext;

	@Override
	protected String doInBackground(String... code) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(new HttpGet("http://foodstormfun.cloudapp.net/upc.php?code=" +  code[0]));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				return out.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String result) {
		Toast.makeText(appContext, result, Toast.LENGTH_SHORT).show();
	}
}
