package com.example.utils;

public class Alarme extends Equip {
	private Model_Equip marque_alar;

	public Model_Equip getMarque_alar() {
		return marque_alar;
	}

	public Alarme(String id_equip, String nom_equip, Model_Equip marque_alar) {
		super(id_equip, nom_equip);
		this.marque_alar = marque_alar;
	}

	public void setMarque_alar(Model_Equip marque_alar) {
		this.marque_alar = marque_alar;
	}

}
