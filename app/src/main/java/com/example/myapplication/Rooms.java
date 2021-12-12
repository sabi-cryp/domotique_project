package com.example.myapplication;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.JSONParser;
import com.example.utils.Room;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Rooms extends Activity {
 ListView list;
 EditText roomName;
 Button confirm;
private CheckBox chV;
private CheckBox chE;
private CheckBox chT;
ArrayList<Room> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rooms);
		list=(ListView) findViewById(R.id.listViewRoom);
		roomName =(EditText) findViewById(R.id.editTextPiece);
		confirm =(Button) findViewById(R.id.buttonConf);
		data = new ArrayList<Room>();
		//On charge la liste directement lorsqu'on connecte
		/* new ListRoomTask().execute();
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogRoom(roomName.getText().toString());
			}
		});
	}
	
	public void dialogRoom(final String name )
{
	AlertDialog.Builder aDialog=new AlertDialog.Builder(Rooms.this);
	aDialog.setTitle(name);
	
	LinearLayout layout= new LinearLayout(Rooms.this);
	layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	layout.setOrientation(LinearLayout.VERTICAL);
	
	 chV = new CheckBox(Rooms.this);
	chV.setText("Volet");
	layout.addView(chV);
	
	 chE = new CheckBox(Rooms.this);
	chE.setText("Eclairage");
	layout.addView(chE);
	
	 chT = new CheckBox(Rooms.this);
	chT.setText("Thermique");
	layout.addView(chT);
	
	aDialog.setView(layout);
	
		aDialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				new AddRoomTask().execute(name);
			}
		});
	
		aDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
	
		aDialog.show(); */
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rooms, menu);
		return true;
	}
	 /*
	
	//Pour envoyer la requete au serveur
	 public class AddRoomTask extends AsyncTask<String, String, String>
	    {
	    

			private String msg;

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
				parames.add(new BasicNameValuePair("id", new Settings(Rooms.this).getID() ));
				parames.add(new BasicNameValuePair("des_rm", params[0] ));
				parames.add(new BasicNameValuePair("sh_s", StateCheckbox(chV.isChecked())));
				parames.add(new BasicNameValuePair("li_s", StateCheckbox(chE.isChecked())));
				parames.add(new BasicNameValuePair("th_s", StateCheckbox(chT.isChecked())));

				
				JSONParser JParser = new JSONParser();
				JSONObject json = JParser.makeHttpRequest(new Settings(Rooms.this).getHost()+"/Domotique/Room.php","GET", parames);
				
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
						return "faild";
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
				
				if (result.equals("success"))
				{
					Toast.makeText(Rooms.this, msg, Toast.LENGTH_SHORT).show();
					
					data.clear();
					new ListRoomTask().execute();
				}
				
				if (result.equals("faild"))
				{
					Toast.makeText(Rooms.this, msg, Toast.LENGTH_SHORT).show();
				}
				
			super.onPostExecute(result);
			}
 }	

	
	 public class ListRoomTask extends AsyncTask<String, String, String>
	    {
	    
	    	private ProgressDialog pDialog;
			private Settings config;
			private String msg;


			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
	    		pDialog=new ProgressDialog(Rooms.this);
	    		pDialog.setMessage("Loading.. Please Wait ");
	    		pDialog.show();
				super.onPreExecute();
			}


			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
				parames.add(new BasicNameValuePair("id_usr", new Settings(Rooms.this).getID() ));
				
				
				JSONParser JParser = new JSONParser();
				JSONObject json = JParser.makeHttpRequest(new Settings(Rooms.this).getHost()+"/Domotique/List.php","GET", parames);
				
				try {
					int success= json.getInt("success");
					
					if (success==1)
					{
						
						JSONArray rooms = json.getJSONArray("Room");
						for (int i=0 ; i < rooms.length(); i++)
						{
							JSONObject room = rooms.getJSONObject(i);
							
							
							String id_rm = room.getString("id_rm");
							String des_rm = room.getString("des_rm");
							String sh_s = room.getString("sh_s");
							String li_s = room.getString("li_s");
							String th_s = room.getString("th_s");
				Room rm= new Room (Integer.parseInt(id_rm),des_rm,Integer.parseInt(sh_s),Integer.parseInt(li_s),Integer.parseInt(th_s));
				data.add(rm);
						}
						msg=json.getString("message");
						return "success";
					}
					else
					{
						msg=json.getString("message");
						return "faild";
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
				
				if (result.equals("success"))
				{
					Toast.makeText(Rooms.this, msg, Toast.LENGTH_SHORT).show();
					ArrayList<String> des_data = new ArrayList<String>();
					for (int i=0; i< data.size();i++)
					{
						des_data.add(data.get(i).getDes_rm());
					}
					
					ArrayAdapter <String>adapter = new ArrayAdapter(Rooms.this, android.R.layout.simple_list_item_1 , des_data);
					list.setAdapter(adapter);
				}
				
				if (result.equals("faild"))
				{
					Toast.makeText(Rooms.this, msg, Toast.LENGTH_SHORT).show();
				}
				
			super.onPostExecute(result);
			}
}	

	 */
	 
	 public String StateCheckbox(boolean ch)
	 {
		 if (ch)
			 return "1";
		 return "0";
	 }
}
