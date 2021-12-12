package com.example.myapplication;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myapplication.Lights.LightTask;
import com.example.myapplication.Maison.ListHouseTask;
import com.example.utils.BoiteLetter;
import com.example.utils.House;
import com.example.utils.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Letter extends Activity {
		 ListView list;
		 ArrayList<BoiteLetter> data;
		 int selectedIndex =0;
		 boolean stateTask=false;
			private Settings config;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_letter);
			list=(ListView) findViewById(R.id.listView1);
			data = new ArrayList<BoiteLetter>();
			
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					selectedIndex=position;
					Intent ish = new Intent(Letter.this, DetailLetter.class);
					startActivity(ish);
					
				}
			});
			new ListHouseTask().execute();
		}
			
		 public class ListHouseTask extends AsyncTask<String, String, String>
		    {
		    
		    	private ProgressDialog pDialog;
				private Settings config;
				private String msg;


				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
		    		pDialog=new ProgressDialog(Letter.this);
		    		pDialog.setMessage("Loading.. Please Wait ");
		    		pDialog.show();
					super.onPreExecute();
				}


				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					
					ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
					parames.add(new BasicNameValuePair("id_maison", new Settings(Letter.this).getselectedhouse() ));
					
					JSONParser JParser = new JSONParser();
					JSONObject json = JParser.makeHttpRequest(new Settings(Letter.this).getHost()+"/Domotique/Letter.php","GET", parames);
					Log.d("JsonMessage", json.toString());
					try {
						int success= json.getInt("success");
						
						if (success==1)
						{
							
							JSONArray letters = json.getJSONArray("letter");
							for (int i=0 ; i < letters.length(); i++)
							{
								JSONObject letter = letters.getJSONObject(i);
										
								String id_letter = letter.getString("id_letter");
								String date_letter = letter.getString("date_letter");
								String heure_letter = letter.getString("heure_letter");
								String status_letter = letter.getString("status_letter");
							
								
								BoiteLetter h= new BoiteLetter(id_letter, "Lettre", date_letter,heure_letter, Integer.parseInt(status_letter));
					
					data.add(h);
							}
							msg=json.getString("message");
							return "success";
						}
						else
						{
							msg=json.getString("message");
							return "fail";
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					return null;
				}
				
				@Override
				protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
					pDialog.dismiss();
					if (result.equals("success"))
					{
						Toast.makeText(Letter.this, msg, Toast.LENGTH_SHORT).show();
						ArrayList<String> des_data = new ArrayList<String>();
						for (int i=0; i< data.size();i++)
						{
							
							des_data.add(data.get(i).getDate_boit()+"  "+data.get(i).getHeure_boit());
						}
						
						ArrayAdapter <String>adapter = new ArrayAdapter(Letter.this, android.R.layout.simple_list_item_1 , des_data);
						list.setAdapter(adapter);
						
						stateTask=true;
					}
					
					if (result.equals("faild"))
					{
						Toast.makeText(Letter.this, msg, Toast.LENGTH_SHORT).show();
					}
					
				super.onPostExecute(result);
				}
	}	

		
		

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.letter, menu);
			return true;
		}

	}
