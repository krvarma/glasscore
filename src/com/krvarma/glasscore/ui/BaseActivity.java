package com.krvarma.glasscore.ui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.krvarma.glasscore.log.AppLog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Base Activity
 * 
 * @author krvarma
 *
 */
public class BaseActivity extends Activity {
	public static final String DEVICE_ID = "<<deviceid>>";
	public static final String ACCESS_TOKEN = "<<accesstoken>>";
	public static final String FUNCTION_NAME = "digitalwrite";
	public static final String SPARK_URL = "https://api.spark.io/v1/devices/" + DEVICE_ID + "/" + FUNCTION_NAME;	
	
	// Task listener interface
	public interface TaskListener{
		public void onPreExecite();
		public void onPostExecute(String result);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	// Set text
	protected void setTextViewText(int id, String message){
		((TextView)findViewById(id)).setText(message);
	}
	
	// Execute Spark Cloud command. this will create one AsyncTask and start executing 
	protected void executeSparkCommand(int command, TaskListener listener){
		SparkTask task = new SparkTask();
		
		task.setListener(listener);
		task.execute(command);
	}
	
	// Parse the result tag. I only check the return_value is 1 or not, but it should be 
	// changed to incorporate more error handling
	protected boolean processSparkResult(String result){
		boolean isSuccess = false;
		
		if(null != result){
			try {
				// Parse JSON string
				JSONObject object = new JSONObject(result);
				int retValue = object.getInt("return_value");
	
				if(retValue == 1){
					isSuccess = true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return isSuccess;
	}
	
	// Spark Cloud task
	private class SparkTask extends AsyncTask<Integer, Void, String>{
		private TaskListener listener = null;
		
		@Override
		protected String doInBackground(Integer... params) {
			String responseString = null;
			
			try {
				// Open URL and post data
				URL url = new URL(SPARK_URL);
				HttpsURLConnection cn = (HttpsURLConnection) url.openConnection();
		        String param = "access_token=" + ACCESS_TOKEN + "&args=" + "D7," + (params[0] == 1 ? "HIGH" : "LOW");
		        
		        // Post method
		        cn.setRequestMethod("POST");
		        cn.setDoOutput(true);
		 
		        // Write data to the stream
		        DataOutputStream out = new DataOutputStream(cn.getOutputStream());
		        out.writeBytes(param);
		        out.flush();
		        out.close();
		 
		        AppLog.log("HTTP Status " + cn.getResponseCode());
		        
		        // Read response string
		        BufferedReader in = new BufferedReader(new InputStreamReader(cn.getInputStream()));
		        StringBuffer response = new StringBuffer();
		        String data;
		        
		        while ((data = in.readLine()) != null) {
		            response.append(data);
		        }
		        
		        in.close();
		        
		        // Return response string
		        responseString =  response.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(null != listener){
				// Report post execute
				listener.onPostExecute(result);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if(null != listener){
				// Report Pre execute
				listener.onPreExecite();
			}
		}

		// Set task listener
		public void setListener(TaskListener listener) {
			this.listener = listener;
		}
	}
}