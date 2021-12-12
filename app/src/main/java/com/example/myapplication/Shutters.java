package com.example.myapplication;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.utils.JSONParser;
import com.example.utils.Room;
import com.example.utils.Shutter;

public class Shutters extends Activity {
	
	private ListView list;
	private ArrayList<Shutter> data;
	private ImageView imgShutterState;
	private ImageView btnopen;
	private ImageView btnclose;
	
	boolean stateTask=false;
	int selectedIndex =0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shutters);
		list=(ListView) findViewById(R.id.listView2);
		data = new ArrayList<Shutter>();
		//On charge la liste directement lorsqu'on connecte
		new ListRoomTask().execute();
		imgShutterState = (ImageView)findViewById(R.id.imageViewshutterState);
		btnopen = (ImageView)findViewById(R.id.imageViewopen);
		btnclose = (ImageView)findViewById(R.id.imageViewclose);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedIndex=position;
				
			}
		});
		
		btnopen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CommandRoomTask().execute("1");
				
			}
		});
		
		btnclose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CommandRoomTask().execute("0");

			}
		});  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shutters, menu);
		return true;
	}

//	@Override
	
	protected void onResume() {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
					if (stateTask)
				new ShutterStateTask().execute();
			}
		},2000,2000);
		super.onResume();
	}	
	 public class ListRoomTask extends AsyncTask<String, String, String>
	    {
	    
	    	private ProgressDialog pDialog;
			private Settings config;
			private String msg;


			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
	    		pDialog=new ProgressDialog(Shutters.this);
	    		pDialog.setMessage("Loading.. Please Wait ");
	    		pDialog.show();
				super.onPreExecute();
			}


			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
				parames.add(new BasicNameValuePair("id_user", new Settings(Shutters.this).getID() ));
				parames.add(new BasicNameValuePair("id_maison", new Settings(Shutters.this).getselectedhouse() ));
				
				JSONParser JParser = new JSONParser();
				JSONObject json = JParser.makeHttpRequest(new Settings(Shutters.this).getHost()+"/Domotique/ShutterState.php","GET", parames);
				Log.d("shutter", json.toString());
				try {
					int success= json.getInt("success");
					
					if (success==1)
					{
						
						JSONArray shutters = json.getJSONArray("shutter");
						Log.i("allobjectshutter", shutters.toString());
						for (int i=0 ; i < shutters.length(); i++)
						{
							JSONObject shut = shutters.getJSONObject(i);
							
								
							String id_shutter = shut.getString("id_shutter");
							Log.i("id_shutter", id_shutter);
							String id_rm = shut.getString("id_rm");
							String des_rm = shut.getString("des_rm");
							int temp_rm = shut.getInt("temp_rm");
							int himu_rm = shut.getInt("himu_rm");
							int etat = shut.getInt("etat");
				Shutter sh= new Shutter(id_shutter, "Shutter", etat, new Room(id_rm, des_rm, temp_rm, himu_rm));
				
				//if (Integer.parseInt(sh_s)==1)
				data.add(sh);
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
					Toast.makeText(Shutters.this, msg, Toast.LENGTH_SHORT).show();
					ArrayList<String> des_data = new ArrayList<String>();
					for (int i=0; i< data.size();i++)
					{
						des_data.add(data.get(i).getRm().getNom_rm());
					}
					
					ArrayAdapter <String>adapter = new ArrayAdapter(Shutters.this, android.R.layout.simple_list_item_1 , des_data);
					list.setAdapter(adapter);
					
					stateTask=true;
				}
				
				if (result.equals("faild"))
				{
					Toast.makeText(Shutters.this, msg, Toast.LENGTH_SHORT).show();
				}
				
			super.onPostExecute(result);
			}
}	

	 
	 public class CommandRoomTask extends AsyncTask<String, String, String>
	 {

		private String msg;

		@Override
		protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
			ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("id_shutter", data.get(selectedIndex).getId_equip() ));
			parames.add(new BasicNameValuePair("etat", params[0]));
			
			JSONParser JParser = new JSONParser();
			JSONObject json = JParser.makeHttpRequest(new Settings(Shutters.this).getHost()+"/Domotique/Shutter.php","GET", parames);
			
			try {
				int success= json.getInt("success");
				
				if (success==1)
				{
										
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
		
		
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
				
				if (result.equals("success"))
				{
					Toast.makeText(Shutters.this, msg, Toast.LENGTH_SHORT).show();
					
				}
				
				if (result.equals("fail"))
				{
					Toast.makeText(Shutters.this, msg, Toast.LENGTH_SHORT).show();
				}
				
			super.onPostExecute(result);
			}

	 }
	 
	 
	 public class ShutterStateTask extends AsyncTask<String, String, String>
	 {

		private String msg;
		private String etat;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("id_shutter",data.get(selectedIndex).getId_equip() ));
			JSONParser JParser = new JSONParser();
			JSONObject json = JParser.makeHttpRequest(new Settings(Shutters.this).getHost()+"/Domotique/statshutt.php","GET", parames);
			
			Log.d("shutter", json.toString());		
			try {
				int success= json.getInt("success");
				
				if (success==1)
				{
						
					
					etat=json.getString("etat");
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
		
		
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
				
				if (result.equals("success"))
				{
					switch (Integer.parseInt(etat)) {
					case 1:
						
						imgShutterState.setImageResource(R.drawable.shuttero);
						break;

						
					case 0:
						
						imgShutterState.setImageResource(R.drawable.shutterc);
						break;
						
					default:
						break;
					}
				}
				
				if (result.equals("fail"))
				{
					Toast.makeText(Shutters.this, msg, Toast.LENGTH_SHORT).show();
				}
				
			super.onPostExecute(result);
			}

	 }
	 
}
