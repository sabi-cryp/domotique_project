package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.utils.Climatiseur;
import com.example.utils.JSONParser;
import com.example.utils.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Thermiques extends Activity {
//si la valeur a ete modifie donc on applique la modification sinon 
	//btn.set

	ListView list;
	  ArrayList<Climatiseur> data;
	  Button chaud,froid;
	  ToggleButton on_off;
	  TextView temp;
	  LinearLayout layoutstate;
	  int temp_value=0;
	  int himud_value=0;
	  int temp_propo;
	  SeekBar valeurtemp;
	  boolean changeState=false;
	  int thermiqueState=0;
	 int thermique_on_off=0;
	int  thermiqueindex=0;
	TextView himudiact;
	TextView tempuact;
	int tempamodifier;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thermiques);
		list=(ListView) findViewById(R.id.listView4);
		chaud=(Button) findViewById(R.id.buttonchaud);
		Log.d("i'm here ","thermique");
		froid=(Button) findViewById(R.id.buttonfroid);
		on_off=(ToggleButton) findViewById(R.id.toggleButton1);
		temp=(TextView) findViewById(R.id.textViewth);
		himudiact=(TextView) findViewById(R.id.TextViewhimu);
		tempuact=(TextView) findViewById(R.id.Textviewtemact);
		valeurtemp=(SeekBar) findViewById(R.id.seekBartemp);
		//Typeface font=Typeface.createFromAsset(getAssets(), "CristoLikidTryout.ttf");
		//temp.setTypeface(font);
		data=new ArrayList<Climatiseur>();
		Log.d("i'm here ","thermique2");
		new LoadTask().execute(); 
		valeurtemp.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
		
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				new ChangeTask().execute();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
				// TODO Auto-generated method stub
			
				tempamodifier=(int) (progress*0.14+16);
				Log.i("temp propo",temp_propo+"");
				
			
				
			}
		});
      chaud.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thermiqueState=1;//chaud
				new ChangeTask().execute();
			}
		});
		froid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thermiqueState=0;//froid
				new ChangeTask().execute();
			}
		});
		
		 on_off.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					
					if(isChecked)
						thermique_on_off=1;//on
					else
						thermique_on_off=0;//off
					
					new ChangeTask().execute();
				}
			});
		 
		 list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					thermiqueindex=arg2;
					//temp_propo=data.get(thermiqueindex).getNiveau_Climatiseur();
			
					
					
				}
			});
			 
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thermiques, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		Timer autoupdate=new Timer();
		
		autoupdate.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(changeState)
						new ThermiqueStateTask().execute();
						Log.d("Thermique", thermiqueindex+"");
						
					}
				});
				
				
			}
		},1000, 1000);
		
		super.onResume();
	}

	
	public class LoadTask extends AsyncTask<String, String, String>
	{

		private JSONParser jParser;
		private ProgressDialog pDialog;
		private String msg;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			 pDialog=new ProgressDialog(Thermiques.this);
	    		pDialog.setMessage("Lister les pi�ces");
	    		pDialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("id_user", new Settings(Thermiques.this).getID() ));
			parames.add(new BasicNameValuePair("id_maison", new Settings(Thermiques.this).getselectedhouse() ));
			Log.i("json eror","json error");
			  jParser=new JSONParser();
				
			  JSONObject json = jParser.makeHttpRequest(new Settings(Thermiques.this).getHost()+"/Domotique/ThermiqueState.php", "GET", parames);
				
				  Log.i("etat req json",json.toString());
				  
			  try {
				int success=json.getInt("success");
				
				if(success==1)
					
				{ 
					JSONArray climatiseura = json.getJSONArray("climatiseur");
					Climatiseur cl;
					for (int i = 0; i <climatiseura.length(); i++) {
						
						JSONObject climati = climatiseura.getJSONObject(i);
								
						String id_climatiseur = climati.getString("id_climatiseur");
						String etat_clima = climati.getString("etat_clima");
						String niveau_clima = climati.getString("niveau_clima");
						String type_clima = climati.getString("type_clima");
						String id_rm = climati.getString("id_rm");
						String des_rm = climati.getString("des_rm");
						int temp_rm = climati.getInt("temp_rm");
						int himu_rm = climati.getInt("himu_rm");
						cl=new Climatiseur(id_climatiseur, "Climatiseur", Integer.parseInt(niveau_clima), type_clima,new Room(id_rm, des_rm, temp_rm, himu_rm));
						
						data.add(cl);
					}
					
					
					msg=json.getString("message");
					return "success";
				}else{
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
			
			ArrayList<String> des_data = new ArrayList<String>();
			for (int i=0; i< data.size();i++)
			{
				des_data.add(data.get(i).getRm().getNom_rm());
			}
			
			
			if(result.equals("success"))
			{
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(Thermiques.this, android.R.layout.simple_list_item_1,des_data);
				list.setAdapter(adapter);
			}
			if(result.equals("success"))
			{
				Toast.makeText(Thermiques.this, msg, Toast.LENGTH_SHORT).show();
			}
			changeState=true;
			super.onPostExecute(result);
		}
		
	}

	public class ChangeTask extends AsyncTask<String, String, String>
	{

		private JSONParser jParser;
		
		private String msg;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			changeState=false;
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			 parames.add(new BasicNameValuePair("id_climatiseur",data.get(thermiqueindex).getId_equip()));
			 parames.add(new BasicNameValuePair("niveau", Integer.toString(tempamodifier)));
			 parames.add(new BasicNameValuePair("chaud_froid",thermiqueState+""));
			 parames.add(new BasicNameValuePair("etat",thermique_on_off+""));
			
			 
			  jParser=new JSONParser();
				
			  JSONObject json = jParser.makeHttpRequest(new Settings(Thermiques.this).getHost()+"/Domotique/Thermique.php", "GET", parames);
			  changeState=true;
				
				  Log.i("etat req json",json.toString());
				  
			  try {
				int success=json.getInt("success");
				
				if(success==1)
					
				{ 
                		msg=json.getString("message");
						return "success";
			
				  }else{
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
			Toast.makeText(Thermiques.this, msg, Toast.LENGTH_SHORT).show();
			
			
			 
			
			super.onPostExecute(result);
		}
		
	}

	
	
	
	
	public class ThermiqueStateTask extends AsyncTask<String, String, String>
	{

		private JSONParser jParser;
		
		private String msg;
		private int nhimud_value=himud_value; 
		private int ntemp_value=temp_value;
		private int ntemp_propo=temp_propo ; 
		private int nthermique_on_off=thermique_on_off;

		 

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			 parames.add(new BasicNameValuePair("id_climatiseur",data.get(thermiqueindex).getId_equip()));
					
			  jParser=new JSONParser();
			  JSONObject json = jParser.makeHttpRequest(new Settings(Thermiques.this).getHost()+"/Domotique/statthermique.php", "GET", parames);
				
				  Log.i("etat req json",json.toString());
				  
			  try {
				int success=json.getInt("success");
				
				if(success==1)
					
				{ 		msg=json.getString("message");
			
				nhimud_value=Integer.parseInt(json.getString("himu_rm")); 
				ntemp_value=Integer.parseInt(json.getString("temp_rm")); 
				ntemp_propo=Integer.parseInt(json.getString("niveau_clima"));   
				 nthermique_on_off=Integer.parseInt(json.getString("etat_clima"));
					msg=json.getString("message");
					
				
				
				
						return "success";
					 
					
					
			
					
				}else{
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
			
//Toast.makeText(Lights.this, msg, Toast.LENGTH_SHORT).show();
			
			
			if(result.equals("success"))
			{
				if (temp_propo!=ntemp_propo) {
					temp_propo=ntemp_propo;
					temp.setText(temp_propo+" C");
					
				}
				if(thermique_on_off!=nthermique_on_off)
				{
					thermique_on_off=nthermique_on_off;
				if(thermique_on_off==1)
				on_off.setChecked(true);
				else
					on_off.setChecked(false);
				}
				if (himud_value!=nhimud_value) {
					himud_value=nhimud_value;
					himudiact.setText(himud_value+" %");
				}
				if (temp_value!=ntemp_value) {
					temp_value=ntemp_value;
				tempuact.setText(temp_value+" C");
				}
				if (temp_propo!=ntemp_propo) {
					temp_propo=ntemp_propo;
				valeurtemp.setProgress(temp_propo);
				}
				
			}
			
			
			super.onPostExecute(result);
		}
		
	}


}
