package com.example.utils;

public class Climatiseur extends Equip {
	
	private int niveau_Climatiseur;
	private String type_Climatiseur;
	private Room rm;
	
	public Climatiseur(String id_equip, String nom_equip,
			int niveau_Climatiseur, String type_Climatiseur,Room rm) {
		super(id_equip, nom_equip);
		this.niveau_Climatiseur = niveau_Climatiseur;
		this.type_Climatiseur = type_Climatiseur;
		this.rm=rm;
	}
	
	
	
	public Room getRm() {
		return rm;
	}
	public void setRm(Room rm) {
		this.rm = rm;
	}
	
	public int getNiveau_Climatiseur() {
		return niveau_Climatiseur;
	}
	public void setNiveau_Climatiseur(int niveau_Climatiseur) {
		this.niveau_Climatiseur = niveau_Climatiseur;
	}
	public String getType_Climatiseur() {
		return type_Climatiseur;
	}
	public void setType_Climatiseur(String type_Climatiseur) {
		this.type_Climatiseur = type_Climatiseur;
	}
	

}
