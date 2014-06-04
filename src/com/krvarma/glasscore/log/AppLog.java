package com.krvarma.glasscore.log;

import android.util.Log;

public class AppLog {
	// Application Tag
	private static final String APP_TAG = "GlassCore";
	
	// Log with application tag
	public static int log(String tag, String message){
		return Log.d(tag, message);
	}
	
	// Log with default tag
	public static int log(String message){
		return log(APP_TAG, message);
	}
}
