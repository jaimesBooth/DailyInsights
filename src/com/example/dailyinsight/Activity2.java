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
import android.widget.Toast;

public class Activity2 extends Activity
{

	private RadioGroup radioTopicsGroup;
	private RadioButton radioTopicsButton;
	private ImageButton imgBtnTopic;
	 
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity2);
	 
		addListenerOnButton();
	 
	  }
	 
	  //add listener to Topic button
	  public void addListenerOnButton() {
	 
		radioTopicsGroup = (RadioGroup) findViewById(R.id.radioTopics);
		imgBtnTopic = (ImageButton) findViewById(R.id.imageButton1);
	 
		imgBtnTopic.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View v) {
	 
			        // get selected radio button from radioGroup
				int selectedId = 
					radioTopicsGroup.getCheckedRadioButtonId();
	 
				// find the radiobutton by returned id
			        radioTopicsButton = 
					(RadioButton) findViewById(selectedId);
	 
				Toast.makeText
				  (Activity2.this, 
				   radioTopicsButton.getText() + " topic chosen", 
				   Toast.LENGTH_SHORT).show();
	 
			}
		});

}
}
