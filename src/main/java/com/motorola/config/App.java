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
        
        File file = new File("f:/java-workspase/Outputs/MotoConfig.xls");
		CreateConfig.newConfig(file, plc);
		System.out.println("Stop!");
		
       // ScreenMain.startScreen();
        
    }  
}

