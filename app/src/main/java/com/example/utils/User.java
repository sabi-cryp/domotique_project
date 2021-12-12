package com.example.utils;

public class User extends Personne {

	private String login_user;
	private int mdp_user;

	
	
	public User(String id_pers, String nom_pers, String prenom_pers,
			String daten_pers, String urlpic_pers) {
		
		super(id_pers, nom_pers, prenom_pers, daten_pers, urlpic_pers);
	}
	
	
	
	public String getLogin_user() {
		return login_user;
	}
	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}
	public int getMdp_user() {
		return mdp_user;
	}
	public void setMdp_user(int mdp_user) {
		this.mdp_user = mdp_user;
	}

}
