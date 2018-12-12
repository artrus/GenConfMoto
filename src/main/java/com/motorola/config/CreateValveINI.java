package com.motorola.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateValveINI {

	public CreateValveINI() {
		// TODO Auto-generated constructor stub
	}
	
	public static void CreateConfValveINI (File file, PLC_Motorola plc) throws IOException {
		
		FileOutputStream fileoutputstream = new FileOutputStream(file);
		PrintWriter ValveIni = new PrintWriter(fileoutputstream);
		for (int i = 0; i<plc.ListValves.size(); i++){
			ValveIni.println("; Задвижка №" + Integer.toString(i+1) + " №" + plc.ListValves.get(i).getNumber());
		}
		ValveIni.close();
		fileoutputstream.close();
	}
}
