package com.example.myapplication;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.House;
import com.example.utils.JSONParser;
import com.example.utils.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Maison extends Activity {
	 ListView list;
	 ArrayList<House> data;
	 int selectedIndex =0;
	 boolean stateTask=false;
		private Settings config;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maison);
		list=(ListView) findViewById(R.id.listMaison);
		data = new ArrayList<House>();
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedIndex=position;
				new Settings(Maison.this).setselectedhouse(data.get(selectedIndex).getId_mai());
				Intent ish = new Intent(Maison.this, DashBoard.class);
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
	    		pDialog=new ProgressDialog(Maison.this);
	    		pDialog.setMessage("Loading.. Please Wait ");
	    		pDialog.show();
				super.onPreExecute();
			}


			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
				parames.add(new BasicNameValuePair("id_user", new Settings(Maison.this).getID() ));
				
				
				JSONParser JParser = new JSONParser();
				JSONObject json = JParser.makeHttpRequest(new Settings(Maison.this).getHost()+"/Domotique/Maison.php","GET", parames);
				Log.d("JsonMessage", json.toString());
				try {
					int success= json.getInt("success");
					
					if (success==1)
					{
						
						JSONArray maisons = json.getJSONArray("maison");
						for (int i=0 ; i < maisons.length(); i++)
						{
							JSONObject room = maisons.getJSONObject(i);
							
							String id_maison = room.getString("id_maison");
							String adr_maison = room.getString("adr_maison");
							String latitude_maison = room.getString("latitude_maison");
							String longitude_maison = room.getString("longitude_maison");
							String tel_maison = room.getString("tel_maison");
							String ip_maison = room.getString("ip_maison");
							
				House h= new House (id_maison, adr_maison, Double.parseDouble(longitude_maison),Double.parseDouble(latitude_maison), tel_maison, ip_maison);
				
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
					Toast.makeText(Maison.this, msg, Toast.LENGTH_SHORT).show();
					ArrayList<String> des_data = new ArrayList<String>();
					for (int i=0; i< data.size();i++)
					{
						
						des_data.add(data.get(i).getAdr_mai());
					}
					
					ArrayAdapter <String>adapter = new ArrayAdapter(Maison.this, android.R.layout.simple_list_item_1 , des_data);
					list.setAdapter(adapter);
					
					stateTask=true;
				}
				
				if (result.equals("faild"))
				{
					Toast.makeText(Maison.this, msg, Toast.LENGTH_SHORT).show();
				}
				
			super.onPostExecute(result);
			}
}	

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maison, menu);
		return true;
	}

}
