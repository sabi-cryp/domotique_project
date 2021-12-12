package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DashBoard extends Activity {
	ImageView shutter,ligth,thermique,room,param;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
		initAttrs();
		initListeners();
	}
	
	public void initAttrs()
	{
		shutter=(ImageView)findViewById(R.id.imageViewVolet);
		ligth=(ImageView)findViewById(R.id.ImageViewEcl);
		room=(ImageView)findViewById(R.id.ImageViewPiece);
		thermique=(ImageView)findViewById(R.id.ImageViewTher);
		param=(ImageView)findViewById(R.id.ImageViewParams);		
	}
	
	public void initListeners()
	{
		shutter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ish = new Intent(DashBoard.this, Shutters.class);
				startActivity(ish);
				
			}
		});
		
		ligth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ish = new Intent(DashBoard.this, Lights.class);
				startActivity(ish);
				
			}
		});
		
		room.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ish = new Intent(DashBoard.this, Letter.class);
				startActivity(ish);
				
			}
		});
		
		thermique.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ish = new Intent(DashBoard.this, Thermiques.class);
				startActivity(ish);
				
				
			}
		});
		
		param.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ish = new Intent(DashBoard.this, Camera.class);
				startActivity(ish);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dash_board, menu);
		return true;
	}

}
