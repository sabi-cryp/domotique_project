package com.example.myapplication;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.JSONParser;
import com.example.utils.Lampe;
import com.example.utils.Room;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class Lights extends Activity {
	 ListView list;
	 private ArrayList<Lampe> data;
	 ArrayList<String> dataIds;
	 ArrayList<String> dataLi_m;
	 boolean RoomTaskState=false;
	 Button on,off;
	 CheckBox autolight;
	 SeekBar lightgradient;
	 int lightState=0;
	 int lightindex=1;
	 ImageView light;
	 int lightImageState=0;
	 Timer autoUpdate;
	int  autostate=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lights);
		list=(ListView) findViewById(R.id.listMaison);
		light=(ImageView) findViewById(R.id.imageView1);
		on=(Button) findViewById(R.id.button1);
		off=(Button) findViewById(R.id.button2);
		lightgradient=(SeekBar) findViewById(R.id.seekBar1);
		autolight=(CheckBox)findViewById(R.id.checkBox1);
		
		on.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lightState=1;
				new LightTask().execute();
			}
		});
		off.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lightState=0;
				new LightTask().execute();
			}
		});
		autolight.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean status) {
				// TODO Auto-generated method stub
				if(!status)
				autostate=1;
				else
				autostate=0;
				
				
				
				
				hidden(status);
				new LightTask().execute();
				Log.i("etat auto",autostate+"");
				
			}
		});
		
		lightgradient.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(progress>=0 && progress<=33)
					lightState=3;//low
				if(progress>=34 && progress<=66)
					lightState=2;//medium
				
				if(progress>=67 && progress<=100)
					lightState=1;//high
				
				new LightTask().execute();
			} 
		});
		data=new ArrayList<Lampe>();
		dataIds=new ArrayList<String>();
		dataLi_m=new ArrayList<String>();
		new RoomTask().execute();
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				
				lightindex=position;
				
		/*		int li_m = Integer.parseInt(dataLi_m.get(arg2));
				
				if(li_m==1)
					lightgradient.setVisibility(SeekBar.VISIBLE);
				else
					lightgradient.setVisibility(SeekBar.INVISIBLE);	*/
			}
		});
	}
	public void hidden(boolean etat)
	{
		if(etat)
		{
		on.setVisibility((View.GONE));
		off.setVisibility((View.GONE));
		lightgradient.setVisibility((View.GONE));
		}
		else
		{
			on.setVisibility((View.VISIBLE));
			off.setVisibility((View.VISIBLE));
			lightgradient.setVisibility((View.VISIBLE));
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lights, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		  autoUpdate = new Timer();
		
		autoUpdate.schedule(new TimerTask() {
			
			@Override
			public void run() { 
				// TODO Auto-generated method stub
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(RoomTaskState==true)
						{
						new LightStateTask().execute();
						Log.i("state","ok");
						}
						
					}
				});
				
				
			}
		}, 1000,1000);
		
		
		super.onResume();
	}
	
	
	public class RoomTask extends AsyncTask<String, String, String>
	{

		
		
		private ProgressDialog pDialog;
		private JSONParser jParser;
		private String message;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pDialog=new ProgressDialog(Lights.this);
			pDialog.setMessage("Loading Rooms");
			pDialog.show();
			
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			jParser=new JSONParser();
			
			ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("id_user", new Settings(Lights.this).getID() ));
			parames.add(new BasicNameValuePair("id_maison", new Settings(Lights.this).getselectedhouse() ));
			
			JSONObject json = jParser.makeHttpRequest(new Settings(Lights.this).getHost()+"/Domotique/LightState.php", "GET", parames);
			Log.d("shutter", json.toString());
			try {
				int success= json.getInt("success");
				
				if (success==1)
				{
					
					JSONArray lampes = json.getJSONArray("lampe");
					Log.i("allobjectlampe", lampes.toString());
					for (int i=0 ; i < lampes.length(); i++)
					{
						JSONObject lamp = lampes.getJSONObject(i);
						
							
						String id_lampe = lamp.getString("id_lampe");
						Log.i("id_shutter", id_lampe);
						String id_rm = lamp.getString("id_rm");
						String des_rm = lamp.getString("des_rm");
						int temp_rm = lamp.getInt("temp_rm");
						int himu_rm = lamp.getInt("himu_rm");
						int etat_lampe = lamp.getInt("etat_lampe");
			Lampe  lp= new Lampe(id_lampe, "LAMPE", etat_lampe,new Room(id_rm, des_rm, temp_rm, himu_rm));
			
			//if (Integer.parseInt(sh_s)==1)
			data.add(lp);
						}
						
						message=json.getString("message");
						return "success";
						
					}else
					{
						message=json.getString("message");
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
			
			ArrayList<String> des_data = new ArrayList<String>();
			for (int i=0; i< data.size();i++)
			{
				des_data.add(data.get(i).getRm().getNom_rm());
			}
			
			
			
			if(result.equals("success"))
			{
				ArrayAdapter <String>adapter = new ArrayAdapter(Lights.this, android.R.layout.simple_list_item_1 , des_data);
				list.setAdapter(adapter);
				
				RoomTaskState=true;
			}else	
			{
				Toast.makeText(Lights.this, message, Toast.LENGTH_LONG).show();
			}
			
			super.onPostExecute(result);
		}
		
	
		
	}

	
	public class LightTask extends AsyncTask<String, String, String>
	{

		private String message;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONParser jParser = new JSONParser();
			ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("id_lampe", data.get(lightindex).getId_equip()));
			parames.add(new BasicNameValuePair("etat",lightState+""));
			parames.add(new BasicNameValuePair("auto",autostate+""));
			
				
			JSONObject json = jParser.makeHttpRequest(new Settings(Lights.this).getHost()+"/Domotique/Light.php", "GET", parames);
			
			
			try {
				int success=json.getInt("success");
				
				if(success==1)
				{
					
					message=json.getString("message");
					return "success";
					
				}else
				{
					message=json.getString("message");
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
			
			Toast.makeText(Lights.this, message, Toast.LENGTH_LONG).show();
			
			if(result.equals("success"))
			{
			switch (lightState) {
			case 0:
				
				light.setImageResource(R.drawable.light_off);
				break;

			case 1:
				light.setImageResource(R.drawable.light_max);
				break;
				
			case 2:
				light.setImageResource(R.drawable.light_middle);
				break;
				
			case 3:
				light.setImageResource(R.drawable.light_low);
				break;

			default:
				break;
			}
			}
			super.onPostExecute(result);
		}

		
		
		
	}

	public class LightStateTask extends AsyncTask<String , String, String>
	{
		

		private JSONParser jsonParser;
		private String message;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			RoomTaskState=false;
			jsonParser=new JSONParser();
			ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("id_lampe",data.get(lightindex).getId_equip()));
			parames.add(new BasicNameValuePair("etat",lightindex+""));
			
			JSONObject json = jsonParser.makeHttpRequest(new Settings(Lights.this).getHost()+"/Domotique/statslight.php", "GET", parames);
			
			try {
				int success=json.getInt("success");
				RoomTaskState=true;
				if(success==1)
				{
					
					lightImageState=Integer.parseInt(json.getString("etat_lampe"));
					message=json.getString("message");
					autostate=Integer.parseInt(json.getString("auto_lampe"));
					return "success";
					
				}else
				{
					message=json.getString("message");
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
			
			if(result.equals("fail"))
			Toast.makeText(Lights.this, message, Toast.LENGTH_LONG).show();
			
			if(result.equals("success"))
			{
			switch (lightImageState) {
			case 0:
				
				light.setImageResource(R.drawable.light_off);
				break;

			case 1:
				light.setImageResource(R.drawable.light_max);
				break;
				
			case 2:
				light.setImageResource(R.drawable.light_middle);
				break;
				
			case 3:
				light.setImageResource(R.drawable.light_low);
				break;

			default:
				break;
			}
			}
			super.onPostExecute(result);
		}

	
		
		
	}




}
