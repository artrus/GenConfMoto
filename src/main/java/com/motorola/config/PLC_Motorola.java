package com.motorola.config;

import java.util.List;

public class PLC_Motorola {
	
	public enum TYPEMODULES {DI, AI, DO, AO, DIDO, def};
//	public IOModule iomodule;
	private int[] AllCntIO = {0,0,0,0};			//Кол-во сигналов физ. DI,AI,DO,AO;
	private int[] AllCntModules = {0,0,0,0};  	//Кол-во модулей DI,AI,DO,AO
	
	List<IOModule> ListIOModules;
	List<Valve> ListValves;
	private int kp;				//номер КП
	private int km;				//Километр КП

	
	/**
	 * Вызов обязателен после считывания или изменения структуры корзины
	 * Пересчет кол-ва сигналов
	 * @param plc
	 */
	public void CalcIOsignals (PLC_Motorola plc) {
	
	for (int i = 0; i<plc.ListIOModules.size(); i++ ) {
		switch (plc.ListIOModules.get(i).getName()) {
		case "DI":
			this.AllCntIO[0]=this.AllCntIO[0]+plc.ListIOModules.get(i).getCntInputs();
			break;
		
		case "AI":
			this.AllCntIO[1]=this.AllCntIO[1]+plc.ListIOModules.get(i).getCntInputs();
			break;
		
		case "DO":
			this.AllCntIO[2]=this.AllCntIO[2]+plc.ListIOModules.get(i).getCntOutputs();
			break;
		
		case "AO":
			this.AllCntIO[3]=this.AllCntIO[3]+plc.ListIOModules.get(i).getCntOutputs();
			break;

		default:
			System.out.println("Найден неизвестный модуль при посчете!");
			break;
			}
		}
	}
	
	public int getKp() {
		return kp;
	}
	public void setKp(int kp) {
		this.kp = kp;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	
	public int[] getAllCntIO() {
		return AllCntIO;
	}
	
	public int getAllCntIO(TYPEMODULES type) {
		
		switch (type) {
		case DI:
			return AllCntIO[0];
		case AI:
			return AllCntIO[1];
		case DO:
			return AllCntIO[2];		
		case AO:
			return AllCntIO[3];
		default:
			break;
		}
		
		return 0;
	}

	


	
	
	
}
