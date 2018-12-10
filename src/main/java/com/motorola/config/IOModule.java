package com.motorola.config;

public class IOModule {

	
	private int position;		//позиция модуля в корзине
//	private int type;
	private int cntInputs;		//кол-во входов, если 0 то входов нет
	private int cntOutputs;		//кол-во выходов, если 0 то выходов нет
	private String name; 		//имя модуля
	
	public IOModule() {
		this.position = 0;
		this.cntInputs = 0;
		this.cntOutputs = 0;
		this.name = "default";
	}

	public IOModule(int position) {
		this.position = position;
		this.cntInputs = 0;
		this.cntOutputs = 0;
		this.name = "default";
	}

	public IOModule(int position, int type, int cntInputs, int cntOutputs, String name) {
		this.position = position;
		this.cntInputs = cntInputs;
		this.cntOutputs = cntOutputs;
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCntInputs() {
		return cntInputs;
	}

	public void setCntInputs(int cntInputs) {
		this.cntInputs = cntInputs;
	}

	public int getCntOutputs() {
		return cntOutputs;
	}

	public void setCntOutputs(int cntOutputs) {
		this.cntOutputs = cntOutputs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
