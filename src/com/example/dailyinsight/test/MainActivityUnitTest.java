package com.example.dailyinsight.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.TextView;

import com.example.dailyinsight.MainActivity;

/**
 * Tests the main Activity.
 * @author 21/09/14 jaimes
 * 	http://www.vogella.com/tutorials/AndroidTesting/article.html
 *
 */
public class MainActivityUnitTest extends ActivityUnitTestCase<MainActivity> 
{

	private int testButtonId;
	private int testButton2Id;
	private static MainActivity activity;
	private int testTextViewId;
	private int testInsightTxtView;
	private int testTxtView1;
	private int testTxtView2;
	
	public MainActivityUnitTest() 
	{
		super(MainActivity.class);
	}

	
	@Override
	protected void setUp() throws Exception 
	{
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
		        MainActivity.class);
	    startActivity(intent, null, null);
	    activity = getActivity();
	}

	@Override
	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	
	
	/**
	 * Tests the layout of the main activity.
	 */
	public void testLayout() 
	{
	    testButtonId = com.example.dailyinsight.R.id.button1;
	    testButton2Id = com.example.dailyinsight.R.id.imageButtonFacebook;
	    testTxtView1 = com.example.dailyinsight.R.id.textView1;
	    testTxtView2 = com.example.dailyinsight.R.id.textView2;
	    testInsightTxtView = com.example.dailyinsight.R.id.textView3;
	    
	    
	    // Check that the layout of the MainActivity contains a button with the R.id.button1 ID
	    assertNotNull(activity.findViewById(testButtonId));
	    
	    //Ensure that the text on the button is "Add as Favourite"
	    Button view = (Button) activity.findViewById(testButtonId);
	    assertEquals("Incorrect label of the button", "Add as Favourite", view.getText());
	    
	    
	    // Check that the layout of the MainActivity contains a button with the R.id.imageButtonFacebook ID
	    assertNotNull(activity.findViewById(testButton2Id));
	    
	    // Check that the layout has an insight textview
	    assertNotNull(activity.findViewById(testInsightTxtView));
	    
	    // Check that the layout has an textview1
	    assertNotNull(activity.findViewById(testTxtView1));
	    
	    // Check that the layout has an textview2
	    assertNotNull(activity.findViewById(testTxtView2));
	    
	 }

	public void testOnCreateBundle() {
		//fail("Not yet implemented");
	}

	public void testOnTouchEventMotionEvent() {
		//fail("Not yet implemented");
	}

	
	/**
	 * Tests that the insight text can be set.
	 */
	public void testSetInsightText() 
	{
		testTextViewId = com.example.dailyinsight.R.id.textView3;
	    
	    // Check that the layout of the MainActivity contains a textView with the R.id.textView2 ID
	    assertNotNull(activity.findViewById(testTextViewId));
	    
	    // Change the insight text
	    MainActivity.setInsightText("Test setInsight");
	    
	    //Ensure that the text on the text view is "Test setInsight"
	    TextView textView = (TextView) activity.findViewById(testTextViewId);

	    assertEquals("Incorrect insight in text view", "Test setInsight", textView.getText());
	}

	public void testOnCreateOptionsMenuMenu() {
		//fail("Not yet implemented");
	}

	public void testOnOptionsItemSelectedMenuItem() {
		//fail("Not yet implemented");
	}

}
