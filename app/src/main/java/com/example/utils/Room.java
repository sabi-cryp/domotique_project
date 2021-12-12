package com.example.utils;

import java.util.Vector;

public class Room {
	
	private String id_rm;
	private String nom_rm;
	private int temp_rm; //tempurature 
	private int himu_rm; //himidute 
	
	
	public Room(String id_rm, String nom_rm, int temp_rm, int himu_rm) {
		super();
		this.id_rm = id_rm;
		this.nom_rm = nom_rm;
		this.temp_rm = temp_rm;
		this.himu_rm = himu_rm;
	}
	
	
	
	public String getId_rm() {
		return id_rm;
	}
	public void setId_rm(String id_rm) {
		this.id_rm = id_rm;
	}

	public String getNom_rm() {
		return nom_rm;
	}
	public void setNom_rm(String nom_rm) {
		this.nom_rm = nom_rm;
	}
	public int getTemp_rm() {
		return temp_rm;
	}
	public void setTemp_rm(int temp_rm) {
		this.temp_rm = temp_rm;
	}
	public int getHimu_rm() {
		return himu_rm;
	}
	public void setHimu_rm(int himu_rm) {
		this.himu_rm = himu_rm;
	}
	public Vector<Equip> getEquip_room() {
		return equip_room;
	}
	public void setEquip_room(Vector<Equip> equip_room) {
		this.equip_room = equip_room;
	}
	private Vector <Equip> equip_room;
	
	
	
}