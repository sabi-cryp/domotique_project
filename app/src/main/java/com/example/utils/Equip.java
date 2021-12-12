package com.example.utils;

public class Equip {
	private String id_equip;
	private String nom_equip;
	private int etat_equip;
	
	public Equip(String id_equip, String nom_equip) {
		super();
		this.id_equip = id_equip;
		this.nom_equip = nom_equip;
	}
	public String getId_equip() {
		return id_equip;
	}
	public void setId_equip(String id_equip) {
		this.id_equip = id_equip;
	}
	public String getNom_equip() {
		return nom_equip;
	}
	public void setNom_equip(String nom_equip) {
		this.nom_equip = nom_equip;
	}
	public int getEtat_equip() {
		return etat_equip;
	}
	public void setEtat_equip(int etat_equip) {
		this.etat_equip = etat_equip;
	}
	
	

}
