package com.example.utils;

public class Personne {
	private String id_pers;	
	private String nom_pers;
	private String prenom_pers;
	private String daten_pers;
	private String Urlpic_pers;
	public String getId_pers() {
		return id_pers;
	}
	public Personne(String id_pers, String nom_pers, String prenom_pers,
			String daten_pers, String urlpic_pers) {
		super();
		this.id_pers = id_pers;
		this.nom_pers = nom_pers;
		this.prenom_pers = prenom_pers;
		this.daten_pers = daten_pers;
		Urlpic_pers = urlpic_pers;
	}
	public void setId_pers(String id_pers) {
		this.id_pers = id_pers;
	}
	public String getNom_pers() {
		return nom_pers;
	}
	public void setNom_pers(String nom_pers) {
		this.nom_pers = nom_pers;
	}
	public String getPrenom_pers() {
		return prenom_pers;
	}
	public void setPrenom_pers(String prenom_pers) {
		this.prenom_pers = prenom_pers;
	}
	public String getDaten_pers() {
		return daten_pers;
	}
	public void setDaten_pers(String daten_pers) {
		this.daten_pers = daten_pers;
	}
	public String getUrlpic_pers() {
		return Urlpic_pers;
	}
	public void setUrlpic_pers(String urlpic_pers) {
		Urlpic_pers = urlpic_pers;
	}
	

}
