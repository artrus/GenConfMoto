package com.motorola.config;

public class Valve {

	private boolean CSPA;
	private String number; 			//Номер задвижки №...а
	private String manufactory;		//Производитель ЭПЦ ТЭК ЭЛЕСИ
				
	public Valve() {
		
	}
	
	public Valve(String number, String manufactory) {
		this.setNumber(number);
		this.setManufactory(manufactory);
	}

	public boolean isCSPA() {
		return CSPA;
	}

	public void setCSPA(boolean cSPA) {
		CSPA = cSPA;
	}

	public String getManufactory() {
		return manufactory;
	}

	public void setManufactory(String manufactory) {
		this.manufactory = manufactory;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	

}
