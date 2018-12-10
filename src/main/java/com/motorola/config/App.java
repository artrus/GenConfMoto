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
        
//        for (int i = 0; i < plc.ListIOModules.size(); i++)  {
//        	System.out.println(plc.ListIOModules.get(i).getPosition() + " " + plc.ListIOModules.get(i).getName());
//        }
        
        
        
        File file = new File("f:/java-workspase/Outputs/MotoConfig.xls");
		CreateConfig.newConfig(file, plc);
		System.out.println("Stop!");
		
       // ScreenMain.startScreen();
        
    }  
}

