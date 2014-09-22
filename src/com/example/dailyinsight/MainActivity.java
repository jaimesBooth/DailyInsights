package com.example.dailyinsight;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
//import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

/**
 * The main window of the DailyInsights app.
 * @author 09/09/14 Sia, Luke, Ron, jaimes 1305390
 * @modified 10/09/14 Implemented swipe recognition:
 * 	http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures--mobile-21161
 * 	Implemented change of insight on swipe Left / Right
 * 	Implemented change of background on swipe Up / Down
 * 	http://stackoverflow.com/questions/3355220/android-how-can-i-make-a-drawable-array
 * @modified 19/09/14 Implemented changing to settings activity when selecting settings from menu.
 *  http://developer.android.com/guide/topics/ui/menus.html#RespondingOptionsMenu
 */
public class MainActivity extends Activity {

	private Button aButton; // The Enter Button on the Main Activity
	private TextView insight; // The textbox to display the Insight
	
	
	// A small array of insights for testing purposes
	String[] insights = {"Health: The greatest wealth is health",
			"Wealth: Wealth is not his that has it but his that enjoys it",
			"Goals: A goal without a plan is just a wish",
			"Goals: The longer you wait to do something you should do now, "
			+ "the greater the odds that you will never actually do it."};
	
	int insightsIndex = 0; // Pointer to currently displayed insight
	
	
	// Array of backgrounds for testing purposes
	// http://stackoverflow.com/questions/3355220/android-how-can-i-make-a-drawable-array
	int[] backgroundArray = new int[] {R.drawable.purple_flowers, R.drawable.wooden_floor, R.drawable.lamp, R.drawable.white_background};
	
	int backgroundIndex = 0; // Pointer to currently displayed background
	
	
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
		
		setaButton((Button) this.findViewById(R.id.button1));
		
		// Initialize the gesture detector
		gestureDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
		// Format the insight text box (TextView)
		insight = (TextView) findViewById(R.id.textView3);
		insight.setTextColor(Color.rgb(200,0,0));
		
		// Place a short tutorial on how to use the app in the insight text box
		insight.setText("Swipe left / right to change Insight" + System.getProperty ("line.separator")
				+ "Up / down to change background" + System.getProperty ("line.separator")
				+ "Settings accessible from the action bar");
		
		// Listen for a click on this button
		getaButton().setOnClickListener(new OnClickListener() 
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
	 * Sets the insight Text view to the specified String.w
	 * @param text
	 */
	public void setInsightText(String text) 
	{
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
	 * Passes the selected MenuItem when the user selects an item from the options 
	 * menu (including action items in the action bar).
	 * http://developer.android.com/guide/topics/ui/menus.html#RespondingOptionsMenu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle item selection
	    switch (item.getItemId())
	    {
	        case R.id.action_settings:
	        	// Open activity 2 in response to selecting settings
	    		Intent intent = new Intent(this, Activity2.class);
	    		
	    		startActivity(intent);
	            return true;
	        //case R.id.help:
	            //showHelp();
	            //return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
	    	
	    	// Initiate a layout object so it's background can be changed
	    	RelativeLayout  layout = (RelativeLayout) findViewById(R.id.relativeLayoutid);
	    	
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
	    		
	    		decrementBackgroundIndex();

	    		// Change background
	    		layout.setBackgroundResource(backgroundArray[backgroundIndex]);

	    	}
	    	// user is cycling up through background
	    	else if(up)
	    	{

	    		Toast.makeText(getApplicationContext(), 
	                    "Up", Toast.LENGTH_LONG).show();

	    		incrementBackgroundIndex();

	    		// Change background
	    		layout.setBackgroundResource(backgroundArray[backgroundIndex]);
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
	
	
	/**
     * Increments the backgrounds array, looping if end of array is reached.
     */
	private void incrementBackgroundIndex() 
	{
		
		if (backgroundIndex == (backgroundArray.length -1))
		{
			backgroundIndex = 0;
		}
		else
			backgroundIndex++;
		
	}
	
    /**
     * Decrements the backgrounds array, looping if beginning of array is reached.
     */
	private void decrementBackgroundIndex() 
	{
		
		if (backgroundIndex == 0)
		{
			backgroundIndex = (backgroundArray.length -1);
		}
		else
			backgroundIndex--;
		
	}
	
	
	/** Called when the user clicks the activity1 button.
	 * Call defined in the activity_main.xml */
	public void changeToActivity1(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Activity1.class);
		
		startActivity(intent);

	}
	
	/** Called when the user clicks the activity2 button.
	 * Call defined in the activity_main.xml */
	public void changeToActivity2(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Activity2.class);
		
		startActivity(intent);
	}
	
	/** Called when the user clicks the activity3 button.
	 * Call defined in the activity_main.xml */
	public void changeToActivity3(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, Activity3.class);
		
		startActivity(intent);
	}


	/**
	 * Gets the aButton.
	 * @return
	 */
	public Button getaButton() 
	{
		return aButton;
	}


	/**
	 * Sets the aButton.
	 * @param aButton
	 */
	public void setaButton(Button aButton) 
	{
		this.aButton = aButton;
	}

}
