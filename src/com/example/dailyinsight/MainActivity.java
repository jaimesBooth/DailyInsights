package com.example.dailyinsight;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {

	Button aButton;
	TextView insight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aButton = (Button) this.findViewById(R.id.button1);
		
		
		aButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				insight = (TextView) findViewById(R.id.textView3);
				insight.setText("The longer you wait to do something you should do now, the greater the odds that you will never actually do it.");
				insight.setTextColor(Color.rgb(200,0,0));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
