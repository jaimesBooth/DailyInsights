package com.example.dailyinsight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
 * 
 * @author 09/09/14 Sia 1135311, Luke 1092144, Ron 1250610, Jaimes 1305390
 * @modified 10/09/14 Implemented swipe recognition:
 *           http://code.tutsplus.com/tutorials
 *           /android-sdk-detecting-gestures--mobile-21161 Implemented change of
 *           insight on swipe Left / Right Implemented change of background on
 *           swipe Up / Down
 *           http://stackoverflow.com/questions/3355220/android-how
 *           -can-i-make-a-drawable-array
 * @modified 19/09/14 Implemented changing to settings activity when selecting
 *           settings from menu.
 *           http://developer.android.com/guide/topics/ui/menus
 *           .html#RespondingOptionsMenu
 * @modified 23/09/14 Removed buttons to activity.
 * @Modified 01/10/14 Luke Reduced code repetition, and edited button action
 *           message. Replaced string away with new quote and category classes.
 * @modified 04/10/14 Jaimes Refactor Category and Quote class to start with
 *           Capital. Added selectedCategory attribute. ArrayList of selected
 *           insights based on selected category. Set attributes and methods to
 *           static to be accessible from settings activity.
 * @modified 05/10/14 Luke Edited toast length. Fixed bug with insightsIndex
 *           pointing to array
 * @modified 13/10/14 Luke Now Includes the bookmarked quotes. Random Quote of
 *           the day added with welcome toast Additionally quotes are now added
 *           to the db, and removed beginning instructions.
 * @modified 16/10/14 Jaimes Added proof of concept Facebook connectivity test.
 * 			 AVD networking can be toggled by pressing F8 function key. 
 */
public class MainActivity extends Activity
{

	private Button aButton; // The Favorite Button on the Main Activity
	private ImageButton facebookImgButton; // The Facebook ImageButton on the Main Activity
	private static DatabaseHelper quoteDatabase;
	private static TextView insight; // The textbox to display the Insight
	private static Category selectedCategory; // The currently selected category
												// topic

	// A small array of insights for testing purposes
	private static Quote[] insights =
	{
			new Quote("It is health \n that is the real wealth",
					Category.health),
			new Quote(
					"Everytime you eat or drink \n you are either feeding \n disease \n or fighting it",
					Category.health),
			new Quote(
					"Wealth \n is the ability \n to fully experience \n life",
					Category.wealth),
			new Quote("Wealth \n is the heart and mind \n not the \npocket",
					Category.wealth),
			new Quote(
					"The longer you wait to do something you should do now, the greater the odds that you will never actually do it",
					Category.goals),
			new Quote("A goal without \n a plan \n is just a wish",
					Category.goals),
			new Quote(
					"A bad attitude \n is like a flat tire. \n If you do not change it \n you will never go \n anywhere",
					Category.attitude),
			new Quote(
					"Until you spread your wings. You will have no idea how far you can fly",
					Category.attitude),
			new Quote("Your beliefs pave the way to success \n or block you",
					Category.beliefs),
			new Quote(
					"Whatever the mind \n of man can conceive and believe \n the mind can achieve",
					Category.beliefs) };

	private static ArrayList<Quote> selectedInsights; // The current selection
														// of insight quotes
														// based on selected
														// category topic

	private static int insightsIndex = 0; // Pointer to currently displayed
											// insight

	// Array of backgrounds for testing purposes
	// http://stackoverflow.com/questions/3355220/android-how-can-i-make-a-drawable-array
	private int[] backgroundArray = new int[]
	{ R.drawable.purple_flowers, R.drawable.wooden_floor, R.drawable.lamp,
			R.drawable.white_background };

	private int backgroundIndex = 0; // Pointer to currently displayed
										// background

	// The gesture detector object which detects swipes
	private GestureDetectorCompat gestureDetector;

	
	/**
	 * Creates the DailyInsight main activity.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setaButton((Button) this.findViewById(R.id.button1));
		this.facebookImgButton = (ImageButton) this.findViewById(R.id.imageButtonFacebook);

		// Initialize the selected category
		selectedCategory = Category.all;
		selectedInsights = new ArrayList<Quote>(Arrays.asList(insights));

		// Initialize the gesture detector
		gestureDetector = new GestureDetectorCompat(this,
				new MyGestureListener());

		// Format the insight text box (TextView)
		insight = (TextView) findViewById(R.id.textView3);
		insight.setTextColor(Color.rgb(200, 0, 0));

		// Place todays insight as the default, select the insight at random
		Random random = new Random();
		int todaysInsight = random.nextInt(insights.length);
		insight.setText(insights[todaysInsight].getMessage());

		// Inform user where to find instructions
		Toast.makeText(
				getApplicationContext(),
				"Please Check the settings menu \n for application instructions",
				Toast.LENGTH_LONG).show();

		// Initialize the bookmarked quotes database
		quoteDatabase = new DatabaseHelper(this);

		// Listen for a click on this button
		aButton.setOnClickListener(new OnClickListener()
		{
			/**
			 * Performs the enclosed code on button click.
			 */
			@Override
			public void onClick(View v)
			{
				// Insert current insight in to the insight database
				SQLiteDatabase db = quoteDatabase.getWritableDatabase();
				String insertQuery = "INSERT INTO QUOTES (Quote) VALUES ('"
						+ insight.getText() + "')";
				db.execSQL(insertQuery);

				Toast.makeText(getApplicationContext(), "Quote Saved",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listen for a click on the Facebook button
		facebookImgButton.setOnClickListener(new OnClickListener()
		{
			/**
			 * Performs the enclosed code on button click.
			 */
			@Override
			public void onClick(View v)
			{
				// Test network connection by attempting to download Facebook URL
				new ConnectivityTest(getApplicationContext());
			}
		});
	}

	/**
	 * Triggers the gestureDetectector on a touch event.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * Sets the insight Text view to the specified String.
	 * 
	 * @param text
	 */
	public static void setInsightText(String text)
	{
		insight.setText(text);
	}

	/**
	 * Adds items to the action bar.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Passes the selected MenuItem when the user selects an item from the
	 * options menu (including action items in the action bar).
	 * http://developer.
	 * android.com/guide/topics/ui/menus.html#RespondingOptionsMenu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
		case R.id.action_settings:
			// Open SettingsActivity in response to selecting settings
			Intent intent = new Intent(this, SettingsActivity.class);

			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Handles gesture events. An enclosed class.
	 * 
	 * @author Jaimes
	 *         http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures
	 *         --mobile-21161
	 */
	public class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener
	{
		// Notification of detected gestures
		// private static final String DEBUG_TAG = "Gestures";

		// The weight the swipe has to exceed in order to trigger.
		private float flingMin = 100;
		private float velocityMin = 100;

		/**
		 * What to do on finger "press". This has to be implemented (even though
		 * it may not be used) to allow other gestures to be detected.
		 */
		@Override
		public boolean onDown(MotionEvent event)
		{
			// Notify of detected gesture
			// Log.d(DEBUG_TAG,"onDown: " + event.toString());

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
			RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayoutid);

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
			if (absHDiff > absVDiff && absHDiff > flingMin
					&& absVelocityX > velocityMin)
			{
				// move forward or backward
				if (horizontalDiff > 0)
					backward = true;
				else
					forward = true;
			}
			// Otherwise if swipe is vertical and exceeds trigger weights
			else if (absVDiff > flingMin && absVelocityY > velocityMin)
			{
				// Move up or down
				if (verticalDiff > 0)
					down = true;
				else
					up = true;
			}

			// user is cycling forward through messages
			if (forward)
			{

				// Handy notification
				Toast.makeText(getApplicationContext(), "Forward",
						Toast.LENGTH_SHORT).show();

				// Change to next insight
				incrementInsightIndex();

				getInsightAtIndex(insightsIndex);

				insight.setText(getInsightAtIndex(insightsIndex));

			}
			// user is cycling backwards through messages
			else if (backward)
			{

				Toast.makeText(getApplicationContext(), "Backward",
						Toast.LENGTH_SHORT).show();

				decrementInsightIndex();

				getInsightAtIndex(insightsIndex);

				insight.setText(getInsightAtIndex(insightsIndex));
			}
			// user is cycling down through background
			else if (down)
			{

				Toast.makeText(getApplicationContext(), "Down",
						Toast.LENGTH_SHORT).show();

				decrementBackgroundIndex();

				// Change background
				layout.setBackgroundResource(backgroundArray[backgroundIndex]);

			}
			// user is cycling up through background
			else if (up)
			{

				Toast.makeText(getApplicationContext(), "Up",
						Toast.LENGTH_SHORT).show();

				incrementBackgroundIndex();

				// Change background
				layout.setBackgroundResource(backgroundArray[backgroundIndex]);
			}

			// Notify of detected gesture
			// Log.d(DEBUG_TAG, "onFling: " +
			// event1.toString()+event2.toString());

			return true;
		}

	}

	/**
	 * Increments the insights array, looping if end of array is reached.
	 */
	private void incrementInsightIndex()
	{
		if (insightsIndex == (selectedInsights.size() - 1))
		{
			insightsIndex = 0;
		} else
			insightsIndex++;
	}

	/**
	 * Decrements the insights array, looping if beginning of array is reached.
	 */
	private void decrementInsightIndex()
	{
		if (insightsIndex == 0)
		{
			insightsIndex = (selectedInsights.size() - 1);
		} else
			insightsIndex--;
	}

	/**
	 * Gets the insight String from the insights array at the specified index.
	 * 
	 * @param insightsIndex
	 * @return The insight at the specified index
	 */
	private String getInsightAtIndex(int insightsIndex)
	{
		return selectedInsights.get(insightsIndex).getMessage();
	}

	/**
	 * Increments the backgrounds array, looping if end of array is reached.
	 */
	private void incrementBackgroundIndex()
	{
		if (backgroundIndex == (backgroundArray.length - 1))
		{
			backgroundIndex = 0;
		} else
			backgroundIndex++;
	}

	/**
	 * Decrements the backgrounds array, looping if beginning of array is
	 * reached.
	 */
	private void decrementBackgroundIndex()
	{
		if (backgroundIndex == 0)
		{
			backgroundIndex = (backgroundArray.length - 1);
		} else
			backgroundIndex--;
	}

	/**
	 * Gets the aButton.
	 * 
	 * @return
	 */
	public Button getaButton()
	{
		return aButton;
	}

	/**
	 * Sets the aButton.
	 * 
	 * @param aButton
	 */
	public void setaButton(Button aButton)
	{
		this.aButton = aButton;
	}

	/**
	 * Gets the currently selected Category attribute.
	 * 
	 * @return The currently selected Category
	 */
	public static Category getSelectedCategory()
	{
		return selectedCategory;
	}

	/**
	 * Sets the selected category.
	 * 
	 * @param selectedCategory
	 */
	public void setSelectedCategory(Category selectedCategory)
	{
		MainActivity.selectedCategory = selectedCategory;
	}

	/**
	 * Gets the test array of all insights.
	 * 
	 * @return The test array of all insights.
	 */
	public Quote[] getInsights()
	{
		return insights;
	}

	/**
	 * Gets the selected Insights arrayList of quotes.
	 * 
	 * @return The selected arrayList
	 */
	public ArrayList<Quote> getSelectedInsights()
	{
		return selectedInsights;
	}

	/**
	 * Sets the selected Insights array of quotes based on specified Category
	 * 
	 * @param specified
	 *            insight Category
	 */
	public static void setSelectedInsights(Category category)
	{
		// empty the list
		selectedInsights.clear();

		if (category == Category.all)
		{
			selectedCategory = Category.all;
			selectedInsights = new ArrayList<Quote>(Arrays.asList(insights));
		} else if (category == Category.attitude)
		{
			selectedCategory = Category.attitude;

			for (Quote insight : insights)
			{
				if (insight.getCategory() == Category.attitude)
				{
					selectedInsights.add(insight);
				}
			}
		} else if (category == Category.beliefs)
		{
			selectedCategory = Category.beliefs;

			for (Quote insight : insights)
			{
				if (insight.getCategory() == Category.beliefs)
				{
					selectedInsights.add(insight);
				}
			}
		} else if (category == Category.goals)
		{
			selectedCategory = Category.goals;

			for (Quote insight : insights)
			{
				if (insight.getCategory() == Category.goals)
				{
					selectedInsights.add(insight);
				}
			}
		} else if (category == Category.health)
		{
			selectedCategory = Category.health;

			for (Quote insight : insights)
			{
				if (insight.getCategory() == Category.health)
				{
					selectedInsights.add(insight);
				}
			}
		} else if (category == Category.wealth)
		{
			selectedCategory = Category.wealth;

			for (Quote insight : insights)
			{
				if (insight.getCategory() == Category.wealth)
				{
					selectedInsights.add(insight);
				}
			}
		}
		insightsIndex = 0;
		// Set the insight text to a quote from the selected insights
		setInsightText(selectedInsights.get(insightsIndex).getMessage());
	}
}
