package com.example.utils;

public class Shutter extends  Equip{
	private int niv_shutter;
private Room rm;
	
	public Shutter(String id_equip, String nom_equip, int niv_shutter,Room rm) {
		super(id_equip, nom_equip);
		this.niv_shutter = niv_shutter;
		this.rm=rm;
	}

	public Room getRm() {
		return rm;
	}

	public void setRm(Room rm) {
		this.rm = rm;
	}

	public int getNiv_shutter() {
		return niv_shutter;
	}

	public void setNiv_shutter(int niv_shutter) {
		this.niv_shutter = niv_shutter;
	}
	

}
