package com.example.dailyinsight;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * Serves as a settings menu for the DailyInsights application.
 * @author Siatua Uili, 
 * @modified jaimesbooth 23/09/14
 * 	Changed topics to Sprint 1 insight topics: All, Health, Wealth and Goals.
 * 	Added Toast when group radio button changed
 *
 */
public class Activity2 extends Activity
{

	private RadioGroup radioTopicsGroup;
	private RadioButton radioTopicsButton;
	private ImageButton imgBtnTopic;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity2);

		addListenerOnRadioGroup();

	}

	//add listener to Topic radio group
	public void addListenerOnRadioGroup() 
	{
		radioTopicsGroup = (RadioGroup) findViewById(R.id.radioTopics);
		
		imgBtnTopic = (ImageButton) findViewById(R.id.imageButton1);

		// Set topic to All by default
		radioTopicsGroup.check(R.id.radioAll);

		radioTopicsGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{

			public void onCheckedChanged(RadioGroup radioTopicsGroup, int checkedId) 
			{
				
				if(R.id.radioAll == checkedId)
				{
					
					toastSelectedRadioButton();

				}
				else if(R.id.radioHealth == checkedId)
				{
					
					toastSelectedRadioButton();

				}
				else if(R.id.radioWealth == checkedId)
				{
					
					toastSelectedRadioButton();

				}
				else if(R.id.radioGoals == checkedId)
				{
					
					toastSelectedRadioButton();

				}
			}
		});

		imgBtnTopic.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{


				// get selected radio button from radioGroup
				int selectedId = 
						radioTopicsGroup.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				radioTopicsButton = 
						(RadioButton) findViewById(selectedId);

				Toast.makeText (Activity2.this, radioTopicsButton.getText() 
						+ " topic chosen", Toast.LENGTH_SHORT).show();

			}
		});

	}
	
	/**
	 * Prints the selected radio button text to a pop up toast window
	 */
	public void toastSelectedRadioButton()
	{
		
		// get selected radio button from radioGroup
		int selectedId = 
				radioTopicsGroup.getCheckedRadioButtonId();

		// find the radiobutton by returned id
		radioTopicsButton = 
				(RadioButton) findViewById(selectedId);
		
		Toast.makeText (Activity2.this, radioTopicsButton.getText() 
				+ " topic chosen", Toast.LENGTH_SHORT).show();
		
	}

}
