package com.example.myapplication;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.JSONParser;
import com.example.utils.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Params extends Activity {
	//D�claration
	EditText mail,mdp;
	Button confirm;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        if (verify())
        {
        	Intent intent= new Intent(Params.this,Maison.class);
			startActivity(intent);
        }
        setContentView(R.layout.activity_params);
        mail=(EditText) findViewById(R.id.editText4);
        mdp=(EditText) findViewById(R.id.editText2);
        confirm=(Button) findViewById(R.id.button1);
        confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//R�cup�rer les texte des zones
				String smail=mail.getText().toString();
				String smdp=mdp.getText().toString();
					Log.d("textinput", smail);

					
				//Instance de la classe Settings :Cr�ation des donn�es dans l'XML
				new Settings(Params.this).createSettings(smail, smdp);
				Toast.makeText(Params.this, "Success", Toast.LENGTH_LONG).show();
				Log.d("ConnectTask", "ConnectTask");
				  if(isOnline())
			        {
				new ConnectTask().execute();
			        }
				  else
				  {
					  Toast.makeText(Params.this, "Connection Failed", Toast.LENGTH_LONG).show();
				  }
			}
		});
    }

	public boolean verify()
	{
		if(new Settings(Params.this).getID().equals("0"))
			return false;
		
		return true;
	}
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.params, menu);
        return true;
    }
    
    //Connection au serveur 
    public class ConnectTask extends AsyncTask<String, String, String>
    {
    	
    	private ProgressDialog pDialog;
		private Settings config;
		private String msg;


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
    		pDialog=new ProgressDialog(Params.this);
    		pDialog.setMessage("Loading.. Please Wait ");
    		pDialog.show();
    		Log.d("loding","I am here");
			super.onPreExecute();
		}

    
	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONParser jParse =new JSONParser();
			
			config=new Settings(Params.this);
			
			//Une liste parames contenant deux types de param�tres
			ArrayList<NameValuePair> parames=new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("mail", config.getMail()));
			parames.add(new BasicNameValuePair("mdp", config.getMdp()));
			
			Log.d("jsonparser","jsonparser");
			//Log.d("Mail", config.getMail());
			Log.d("jsonparser",parames.get(0).toString());
			Log.d("jsonparser",parames.get(1).toString());
		
			JSONObject json =jParse.makeHttpRequest(new Settings(Params.this).getHost()+"/Domotique/Param.php", "GET", parames);
			
			Log.d("Status", json.toString());
			Log.e("Status", json.toString());
			Log.i("Status", json.toString());
			
			
		
			
			
			
			
			try {
				
					int success=json.getInt("success");
					
					if (success==1)
					{
						//Test sur le cas success : je vais stock� l'ID et je retourne success
					
						String id=json.getString("id");
						String nom_user=json.getString("nom_user");
						String prenom_user=json.getString("prenom_user");
						String tel_user=json.getString("tel_user");
						String date_naissance_user=json.getString("date_naissance_user");
						String pic_user=json.getString("pic_user");
						new Settings(Params.this).setId(id);
						new Settings(Params.this).setnom_user(nom_user);
						new Settings(Params.this).setprenom_user(prenom_user);
						new Settings(Params.this).settel_user(tel_user);
						new Settings(Params.this).setdate_naissance_user(date_naissance_user);
						new Settings(Params.this).setpic_user(pic_user);
					Log.d("picture", config.getPic_user());
	User u=new User(id, nom_user, prenom_user, date_naissance_user, pic_user);
	
						return "success";
						
					}else  
					{
						//Test sur le cas fail
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
			if (result.equals("fail"))
			{
				Toast.makeText(Params.this, msg, Toast.LENGTH_LONG).show();
			}
			if (result.equals("success"))
			{
				Toast.makeText(Params.this, msg, Toast.LENGTH_LONG).show();
				Intent intent= new Intent(Params.this,Maison.class);
				startActivity(intent);
			}
			super.onPostExecute(result);
		}

    }
    
}
