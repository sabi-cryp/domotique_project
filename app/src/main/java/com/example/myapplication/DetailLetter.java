package com.example.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DetailLetter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_letter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_letter, menu);
		return true;
	}

}
