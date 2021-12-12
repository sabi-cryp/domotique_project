package com.example.utils;

public class Lampe extends Equip {
	private int niv_lamp;
	
	
	
	public Lampe(String id_equip, String nom_equip, int niv_lamp,Room rm) {
		super(id_equip, nom_equip);
		this.niv_lamp = niv_lamp;
		this.rm=rm;
	}
	
	
	public Room getRm() {
		return rm;
	}

	public void setRm(Room rm) {
		this.rm = rm;
	}

	private Room rm;



	public int getNiv_lamp() {
		return niv_lamp;
	}

	public void setNiv_lamp(int niv_lamp) {
		this.niv_lamp = niv_lamp;
	} 
	

}
