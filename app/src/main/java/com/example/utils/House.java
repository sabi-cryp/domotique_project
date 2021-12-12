package com.example.utils;

import java.util.Vector;

public class House {
	private String id_mai;
	private String adr_mai;
	private double longitude_mai;
	private double latitude_mai;
	private String tel_mai;
	private String ip_mai;
	private Vector<Room> listroom;
	
	
	public House(String id_mai, String adr_mai, double longitude_mai,
			double latitude_mai, String tel_mai, String ip_mai) {
		super();
		this.id_mai = id_mai;
		this.adr_mai = adr_mai;
		this.longitude_mai = longitude_mai;
		this.latitude_mai = latitude_mai;
		this.tel_mai = tel_mai;
		this.ip_mai = ip_mai;
	}
	public Vector<Room> getListroom() {
		return listroom;
	}
	public void setListroom(Vector<Room> listroom) {
		this.listroom = listroom;
	}
	public String getIp_mai() {
		return ip_mai;
	}
	public void setIp_mai(String ip_mai) {
		this.ip_mai = ip_mai;
	}
	public String getId_mai() {
		return id_mai;
	}
	public void setId_mai(String id_mai) {
		this.id_mai = id_mai;
	}
	public String getAdr_mai() {
		return adr_mai;
	}
	public void setAdr_mai(String adr_mai) {
		this.adr_mai = adr_mai;
	}
	public double getLongitude_mai() {
		return longitude_mai;
	}
	public void setLongitude_mai(double longitude_mai) {
		this.longitude_mai = longitude_mai;
	}
	public double getLatitude_mai() {
		return latitude_mai;
	}
	public void setLatitude_mai(double latitude_mai) {
		this.latitude_mai = latitude_mai;
	}
	public String getTel_mai() {
		return tel_mai;
	}
	public void setTel_mai(String tel_mai) {
		this.tel_mai = tel_mai;
	}

}
