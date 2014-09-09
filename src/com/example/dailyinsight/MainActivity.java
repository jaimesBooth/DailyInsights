package com.example.dailyinsight;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
//import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

/**
 * The main window of the DailyInsights app.
 * @author 09/09/14 Sia, Luke, Ron, jaimes 1305390
 * @modified 10/09/14 Implemented swipe recognition:
 * http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures--mobile-21161
 *
 */
public class MainActivity extends Activity {

	Button aButton; // The Enter Button on the Main Activity
	TextView insight; // The Insight to display
	
	// A small array of insights for testing purposes
	String[] insights = {"Health: The greatest wealth is health",
			"Wealth: Wealth is not his that has it but his that enjoys it",
			"Goals: A goal without a plan is just a wish"};
	
	int insightsIndex = 0; // Pointer to currently displayed insight

	// The gesture detector object which detects swipes
	private GestureDetectorCompat gestureDetector;
	
	/**
	 * Creates the DailyInsight main activity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aButton = (Button) this.findViewById(R.id.button1);
		
		// Initialize the gesture detector
		gestureDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
		// Format the insight text box (TextView)
		insight = (TextView) findViewById(R.id.textView3);
		insight.setTextColor(Color.rgb(200,0,0));
		
		// Listen for a click on this button
		aButton.setOnClickListener(new OnClickListener() 
		{
			
			/**
			 * Performs the enclosed code on button click.
			 */
			public void onClick(View v) 
			{

				insight.setText("The longer you wait to do something you should do now, the greater the odds that you will never actually do it.");

			}
		});

	}
	
	
	/**
	 * Triggers the gestureDetectector on a touch event.
	 */
	@Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
	
	
	/**
	 * Sets the insight Text view to the specified String.
	 * @param text
	 */
	public void setInsightText(String text) {
		insight.setText(text);
	}

	/**
	 * Adds items to the action bar.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Handles gesture events. An enclosed class.
	 * @author jaimes
	 * http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures--mobile-21161
	 */
	public class MyGestureListener extends GestureDetector.SimpleOnGestureListener 
	{
		// Notification of detected gestures
	    //private static final String DEBUG_TAG = "Gestures"; 
	    
		// The weight the swipe has to exceed in order to trigger.
	    private float flingMin = 100;
	    private float velocityMin = 100;
	    
	    
	    /**
	     * What to do on finger "press". This has to be implemented (even though it may not be used)
	     * to allow other gestures to be detected.
	     */
	    @Override
	    public boolean onDown(MotionEvent event) 
	    { 
	    	// Notify of detected gesture
	        //Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
	    	
	        return true;
	    }

	    
	    /**
	     * Detects swipe direction and implements the actions to be taken on 
	     */
	    @Override
	    public boolean onFling(MotionEvent event1, MotionEvent event2, 
	            float velocityX, float velocityY) 
	    {
	    	
	    	// user will move forward through messages on fling left
	    	boolean forward = false;
	    	// user will move backward through messages on fling right
	    	boolean backward = false;
	    	// user will move forward through backgrounds on fling up
	    	boolean up = false;
	    	// user will move backward through backgrounds on fling down
	    	boolean down = false;
	    	
	    	// Evaluate swipe direction
	    	// calculate the change in X position within the fling gesture
	    	float horizontalDiff = event2.getX() - event1.getX();
	    	// calculate the change in Y position within the fling gesture
	    	float verticalDiff = event2.getY() - event1.getY();
	    	
	    	float absHDiff = Math.abs(horizontalDiff);
	    	float absVDiff = Math.abs(verticalDiff);
	    	float absVelocityX = Math.abs(velocityX);
	    	float absVelocityY = Math.abs(velocityY);
	    	
	    	// If swipe is horizontal and exceeds trigger weights
	    	if(absHDiff>absVDiff && absHDiff>flingMin && absVelocityX>velocityMin)
	    	{
	    		//move forward or backward
	    		if(horizontalDiff>0) backward=true;
	    		else forward=true;
	    	}
	    	// Otherwise if swipe is vertical and exceeds trigger weights
	    	else if(absVDiff>flingMin && absVelocityY>velocityMin)
	    	{
	    		// Move up or down
	    		if(verticalDiff>0) down=true;
	    		else up=true;
			}
	    	
	    	
	    	// user is cycling forward through messages
	    	if(forward)
	    	{
	    	
	    		// Handy notification
	    		Toast.makeText(getApplicationContext(), 
                        "Forward", Toast.LENGTH_LONG).show();
	    		
	    		// Change to next insight
	    		incrementInsightIndex();
	    		
	    		getInsightAtIndex(insightsIndex);

	    		insight.setText(getInsightAtIndex(insightsIndex));

	    	}
	    	// user is cycling backwards through messages
	    	else if(backward){

	    		Toast.makeText(getApplicationContext(), 
                        "Backward", Toast.LENGTH_LONG).show();
	    		
	    		decrementInsightIndex();
	    		
	    		getInsightAtIndex(insightsIndex);
	    		
	    		insight.setText(getInsightAtIndex(insightsIndex));
	    	}
	    	// user is cycling down through background
	    	else if(down)
	    	{

	    		Toast.makeText(getApplicationContext(), 
	                    "Down", Toast.LENGTH_LONG).show();
	    		
	    		//@TODO change backgrounds
	    		decrementInsightIndex();
	    		
	    		getInsightAtIndex(insightsIndex);
	    		
	    		insight.setText(getInsightAtIndex(insightsIndex));
	    	}
	    	// user is cycling up through background
	    	else if(up)
	    	{

	    		Toast.makeText(getApplicationContext(), 
	                    "Up", Toast.LENGTH_LONG).show();
	    		
	    		//@TODO change backgrounds
	    		incrementInsightIndex();
	    		
	    		getInsightAtIndex(insightsIndex);
	    		insight.setText(getInsightAtIndex(insightsIndex));
	    	}

	    	// Notify of detected gesture
	        //Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
	        
	        return true;
	    }

	}
	
	
	  /**
     * Increments the insights array, looping if end of array is reached.
     */
	private void incrementInsightIndex() 
	{
		
		if (insightsIndex == (insights.length -1))
		{
			insightsIndex = 0;
		}
		else
			insightsIndex++;
		
	}
	
    /**
     * Decrements the insights array, looping if beginning of array is reached.
     */
	private void decrementInsightIndex() 
	{
		
		if (insightsIndex == 0)
		{
			insightsIndex = (insights.length -1);
		}
		else
			insightsIndex--;
		
	}
	
	
    /**
     * Gets the insight String from the insights array at the specified index.
     * @param insightsIndex
     * @return The insight at the specified index
     */
	private String getInsightAtIndex(int insightsIndex) 
	{
		
		return insights[insightsIndex];
		
	}
	
	
	/** Called when the user clicks the activity1 button.
	 * Call defined in the activity_main.xml */
	public void changeToActivity1(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Activity1.class);
		//EditText editText = (EditText) findViewById(R.id.edit_message);
		
		//Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		//String name = String.valueOf(spinner.getSelectedItem());
		
		//editText.setText(name);
		
		//intent.putExtra(EXTRA_MESSAGE, message);
		
		startActivity(intent);

	}
	
	/** Called when the user clicks the activity2 button.
	 * Call defined in the activity_main.xml */
	public void changeToActivity2(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Activity2.class);
		//EditText editText = (EditText) findViewById(R.id.edit_message);
		
		//Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		//String name = String.valueOf(spinner.getSelectedItem());
		
		//editText.setText(name);
		
		//intent.putExtra(EXTRA_MESSAGE, message);
		
		startActivity(intent);
	}
	
	/** Called when the user clicks the activity3 button.
	 * Call defined in the activity_main.xml */
	public void changeToActivity3(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Activity3.class);
		//EditText editText = (EditText) findViewById(R.id.edit_message);
		
		//Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		//String name = String.valueOf(spinner.getSelectedItem());
		
		//editText.setText(name);
		
		//intent.putExtra(EXTRA_MESSAGE, message);
		
		startActivity(intent);
	}

}
