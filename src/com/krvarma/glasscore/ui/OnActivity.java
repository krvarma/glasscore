package com.krvarma.glasscore.ui;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.krvarma.glasscore.R;
import com.krvarma.glasscore.log.AppLog;

/**
 * ON Activity
 * 
 * @author krvarma
 *
 */
public class OnActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_core);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setTextViewText(R.id.txtInfo, "Turning on...");
		
		// Start Spark Cloud task and turn on the PIN
		executeSparkCommand(1, new TaskListener() {
			@Override
			public void onPreExecite() {}
			
			@Override
			public void onPostExecute(String result) {
				AppLog.log("Result: " + result);
				
				// Process Spark Cloud response string
				if(!processSparkResult(result)){
					((TextView)findViewById(R.id.txtInfo)).setText("Error occured: " + ((null == result) ? "Network error" : result));
				}
				else{
					// Finish the activity
					finish();
				}
			}
		});
	}
}
