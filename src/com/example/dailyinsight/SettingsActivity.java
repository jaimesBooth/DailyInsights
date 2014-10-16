package com.example.dailyinsight;

import android.app.Activity;
//import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.dailyinsight.MainActivity;

/**
 * Serves as a settings menu for the DailyInsights application.
 * @author Siatua Uili, 
 * @modified Jaimes 23/09/14
 * 	Changed topics to Sprint 1 insight topics: All, Health, Wealth and Goals.
 * 	Added Toast when group radio button changed
 * @modified Luke 03/10/14 Added extra radio buttons to the group
 * @modified Jaimes 04/10/14 Set topic to currently selected category.
 * 	Change insights quote array list based on selected topic.
 * @modified Luke 05/10/14 Reduced code repetition, removed chosen button and related code.
 * @modified Jaimes 08/10/14 Reverted to plain background. Added instructions text box.
 * 	Renamed to SettingsActivity (from Activity2) to clarify purpose.
 */
public class SettingsActivity extends Activity
{

	private RadioGroup radioTopicsGroup;
	private RadioButton radioTopicsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		addListenerOnRadioGroup();

	}

	//add listener to Topic radio group
	public void addListenerOnRadioGroup() 
	{
		radioTopicsGroup = (RadioGroup) findViewById(R.id.radioTopics);

		// Set topic to currently selected category
		if (MainActivity.mainActivity.getSelectedCategory() == Category.all)
		{
			radioTopicsGroup.check(R.id.radioAll);
		}
		else if (MainActivity.mainActivity.getSelectedCategory() == Category.attitude)
		{
			radioTopicsGroup.check(R.id.radioAttitude);
		}
		else if (MainActivity.mainActivity.getSelectedCategory() == Category.beliefs)
		{
			radioTopicsGroup.check(R.id.radioBeliefs);
		}
		else if (MainActivity.mainActivity.getSelectedCategory() == Category.favourites)
		{
			radioTopicsGroup.check(R.id.radioFavourites);
		}
		else if (MainActivity.mainActivity.getSelectedCategory() == Category.goals)
		{
			radioTopicsGroup.check(R.id.radioGoals);
		}
		else if (MainActivity.mainActivity.getSelectedCategory() == Category.health)
		{
			radioTopicsGroup.check(R.id.radioHealth);
		}
		else if (MainActivity.mainActivity.getSelectedCategory() == Category.wealth)
		{
			radioTopicsGroup.check(R.id.radioWealth);
		}

		radioTopicsGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{

			@Override
			public void onCheckedChanged(RadioGroup radioTopicsGroup, int checkedId) 
			{
				if(R.id.radioAll == checkedId)
				{
					// Change the set of selected insights to All the quotes
					MainActivity.mainActivity.setSelectedInsights(Category.all);
				}
				else if(R.id.radioAttitude == checkedId)
				{
					// Change the set of selected insights to Attitude quotes
					MainActivity.mainActivity.setSelectedInsights(Category.attitude);
				}
				else if(R.id.radioBeliefs == checkedId)
				{
					// Change the set of selected insights to Beliefs quotes
					MainActivity.mainActivity.setSelectedInsights(Category.beliefs);
				}
				else if(R.id.radioFavourites == checkedId)
				{
					// Catch empty favorites array
					if (MainActivity.mainActivity.getFavouriteInsights().isEmpty())
					{
						// Notify user there are no favorites
						Toast.makeText(getApplicationContext(),"No Favourites to show", Toast.LENGTH_SHORT).show();
						
						// revert selected insights to "All"
						MainActivity.mainActivity.setSelectedInsights(Category.all);
						radioTopicsGroup.check(R.id.radioAll);
						
						// Break out of method. Naughty Naughty!
						return;
						
					} else
					{
						// Change the set of selected insights to Favourites quotes
						MainActivity.mainActivity.setSelectedInsights(Category.favourites);
					}
				}
				else if(R.id.radioGoals == checkedId)
				{
					// Change the set of selected insights to Goals quotes
					MainActivity.mainActivity.setSelectedInsights(Category.goals);
				}
				else if(R.id.radioHealth == checkedId)
				{
					// Change the set of selected insights to Health quotes
					MainActivity.mainActivity.setSelectedInsights(Category.health);
				}
				else if(R.id.radioWealth == checkedId)
				{
					// Change the set of selected insights to Wealth quotes
					MainActivity.mainActivity.setSelectedInsights(Category.wealth);
				}
				toastSelectedRadioButton();
			}
		});
	}
	
	public void toastSelectedRadioButton() {
		int selectedId = 
				radioTopicsGroup.getCheckedRadioButtonId();

		// find the radiobutton by returned id
		radioTopicsButton = 
				(RadioButton) findViewById(selectedId);

		Toast.makeText (SettingsActivity.this, radioTopicsButton.getText() 
				+ " topic chosen", Toast.LENGTH_SHORT).show();
	}
}
