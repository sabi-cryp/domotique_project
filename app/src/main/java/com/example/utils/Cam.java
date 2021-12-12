package com.example.utils;

public class Cam extends Equip{
	private int port;
	



	public Cam(String id_equip, String nom_equip, int port) {
		super(id_equip, nom_equip);
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

}
