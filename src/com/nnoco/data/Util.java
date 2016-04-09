package com.nnoco.data;

import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class Util {
	public static Context CONTEXT;
	public static PlaySound ps;
	public static Vibrator vibe;
	
	public static void shortToast(String message){
		Toast.makeText(CONTEXT, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void longToast(String message){
		Toast.makeText(CONTEXT, message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * This method convets dp unit to equivalent device specific value in pixels. 
	 * 
	 * @param dp A value in dp(Device independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent Pixels equivalent to dp according to device
	 */
	public static float convertDpToPixel(float dp){
	    Resources resources = CONTEXT.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi/160f);
	    return px;
	}
	/**
	 * This method converts device specific pixels to device independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent db equivalent to px value
	 */
	public static float convertPixelsToDp(float px){
	    Resources resources = CONTEXT.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}
}
