package com.motorola.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        System.out.println( "Start!" );
        
        File fileKP = new File("f:/java-workspase/Inputs/82km.xml");
        PLC_Motorola plc = new PLC_Motorola();
        ReadSaveConfig.ReadAllXML(fileKP, plc);
        
        plc.CalcIOsignals(plc);//считаем сигналы        
        
        File fileTable = new File("f:/java-workspase/Outputs/MotoConfig.xls");
		CreateConfigSTS.newConfig(fileTable, plc);
		
		
		File fileValve = new File("f:/java-workspase/Outputs/Valve.250");
		CreateValveINI.CreateConfValveINI(fileValve, plc);
		System.out.println("Stop!");
       // ScreenMain.startScreen();
        
    }  
}

