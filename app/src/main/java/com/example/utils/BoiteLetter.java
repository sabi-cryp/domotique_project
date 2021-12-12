package com.example.utils;


public class BoiteLetter extends Equip {
	private String date_boit;
	private String heure_boit;
	private int status_boit;
	
	
	
	
	
	
	public BoiteLetter(String id_equip, String nom_equip, String date_boit, String heure_boit,
			int status_boit) {
		super(id_equip, nom_equip);
		this.date_boit = date_boit;
		this.status_boit = status_boit;
		this.setHeure_boit(heure_boit);
	}
	
	
	
	public String getDate_boit() {
		return date_boit;
	}
	public void setDate_boit(String date_boit) {
		this.date_boit = date_boit;
	}
	public int getStatus_boit() {
		return status_boit;
	}
	public void setStatus_boit(int status_boit) {
		this.status_boit = status_boit;
	}



	public String getHeure_boit() {
		return heure_boit;
	}



	public void setHeure_boit(String heure_boit) {
		this.heure_boit = heure_boit;
	}
	
	

}
