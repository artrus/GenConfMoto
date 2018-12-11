package com.motorola.config;

public class Valve {

	private boolean CSPA;
	private int number;
	private String manufactory;
	public Valve() {
		// TODO Auto-generated constructor stub
	}
	
	public Valve(int number, String manufactory) {
		// TODO Auto-generated constructor stub
		this.number = number;
		this.manufactory = manufactory;
	}

	public boolean isCSPA() {
		return CSPA;
	}

	public void setCSPA(boolean cSPA) {
		CSPA = cSPA;
	}
	

}
