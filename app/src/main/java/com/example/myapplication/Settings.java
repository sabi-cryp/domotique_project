package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {
	
	// Cr�ation d'un fichier ..R�ellement un fichier XML pour le stockage des donn�es
	//on va aussi faire les m�thodes d'acc� en lecture et on �criture
	SharedPreferences pref; 
	
	Editor editor;
	Context context;

	
	final String Mail="mail";
	final String Mdp="mdp";
	final String ID="id";
	final String host="http://195.201.179.80/";
	final String nom_user="nom_user";
	final String prenom_user="prenom_user";
	final String tel_user="tel_user";
	final String date_naissance_user="date_naissance_user";
	final String pic_user="pic_user";
	final String selectedhouse="selectedhouse";
	
	//Name du SharedPreferences
	final String SH_Name="demo";
	
	//Constructeur
	public Settings(Context context) {
		super();
		this.context = context;
		//Mode 0: si fichier cr�er il ajoute dedant sinon il cr�e un nouveau
		pref=context.getSharedPreferences(SH_Name, 0);
		editor=pref.edit();
	}
	
	//Autorise l'�criture dans le fichier XML
	public void createSettings(String mail, String mdp)
	{
		editor.putString(Mail, mail);
		editor.putString(Mdp, mdp);
		//Sauvegarde des donn�es
		editor.commit();
	}
	
	
	public String getNom_user() {
		return nom_user;
	}

	public String getPrenom_user() {
		return prenom_user;
	}

	public String getTel_user() {
		return tel_user;
	}

	public String getDate_naissance_user() {
		return date_naissance_user;
	}

	public String getPic_user() {
		return pic_user;
	}
	
	public void setId(String id)
	{
		editor.putString(ID, id);
		editor.commit();
	}
	public void sethost(String hos)
	{
		editor.putString(host, hos);
		editor.commit();
	}
	public void setselectedhouse(String id)
	{
		editor.putString(selectedhouse, id);
		editor.commit();
	}
	public void setnom_user(String nom_user)
	{
		editor.putString(this.nom_user, nom_user);
		editor.commit();
	}
	public void setprenom_user(String prenom_user)
	{
		editor.putString(this.prenom_user, prenom_user);
		editor.commit();
	}
	public void settel_user(String tel_user)
	{
		editor.putString(this.tel_user, tel_user);
		editor.commit();
	}
	public void setdate_naissance_user(String date_naissance_user)
	{
		editor.putString(this.date_naissance_user, date_naissance_user);
		editor.commit();
	}
	public void setpic_user(String pic_user)
	{
		editor.putString(this.pic_user, pic_user);
		editor.commit();
	}
	
	public void disconnect()
	{
		editor.clear();
	}
	//Autorise les lectures du fichier XML

	public String getMail() {
		return pref.getString(Mail, "aaaaaa@gmail.com");
	}
	public String getMdp() {
		return pref.getString(Mdp, "0000");
	}
	public String getID() {
		return pref.getString(ID, "0");
	}
	public String getHost() {
		return pref.getString(host, "http://195.201.179.80/");
	}
	public String getselectedhouse() {
		return pref.getString(selectedhouse, "1");
	}
	
	
}
