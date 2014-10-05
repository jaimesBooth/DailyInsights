package com.example.dailyinsight;

import android.app.Activity;
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
 */
public class Activity2 extends Activity
{

	private RadioGroup radioTopicsGroup;
	private RadioButton radioTopicsButton;

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

		// Set topic to currently selected category
		if (MainActivity.getSelectedCategory() == Category.all)
		{
			radioTopicsGroup.check(R.id.radioAll);
		}
		else if (MainActivity.getSelectedCategory() == Category.attitude)
		{
			radioTopicsGroup.check(R.id.radioAttitude);
		}
		else if (MainActivity.getSelectedCategory() == Category.beliefs)
		{
			radioTopicsGroup.check(R.id.radioBeliefs);
		}
		else if (MainActivity.getSelectedCategory() == Category.goals)
		{
			radioTopicsGroup.check(R.id.radioGoals);
		}
		else if (MainActivity.getSelectedCategory() == Category.health)
		{
			radioTopicsGroup.check(R.id.radioHealth);
		}
		else if (MainActivity.getSelectedCategory() == Category.wealth)
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
					MainActivity.setSelectedInsights(Category.all);
				}
				else if(R.id.radioAttitude == checkedId)
				{
					// Change the set of selected insights to Attitude the quotes
					MainActivity.setSelectedInsights(Category.attitude);
				}
				else if(R.id.radioBeliefs == checkedId)
				{
					// Change the set of selected insights to Beliefs the quotes
					MainActivity.setSelectedInsights(Category.beliefs);
				}
				else if(R.id.radioGoals == checkedId)
				{
					// Change the set of selected insights to Goals the quotes
					MainActivity.setSelectedInsights(Category.goals);
				}
				else if(R.id.radioHealth == checkedId)
				{
					// Change the set of selected insights to Health the quotes
					MainActivity.setSelectedInsights(Category.health);
				}
				else if(R.id.radioWealth == checkedId)
				{
					// Change the set of selected insights to Wealth the quotes
					MainActivity.setSelectedInsights(Category.wealth);
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

		Toast.makeText (Activity2.this, radioTopicsButton.getText() 
				+ " topic chosen", Toast.LENGTH_SHORT).show();
	}
}
