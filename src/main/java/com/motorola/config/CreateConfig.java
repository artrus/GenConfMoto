package com.motorola.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateConfig {
	 
	
	
	
	public static void newConfig(File file, PLC_Motorola plc) throws FileNotFoundException, IOException {
		
		FileOutputStream fileoutputstream = new FileOutputStream("f:/java-workspase/Outputs/MotoGenLog.log") ;
		PrintWriter log = new PrintWriter(fileoutputstream);
		log.println("Лог newConfig()");
		Workbook book = new HSSFWorkbook();
		book.createSheet("ACE");
		book.createSheet("Tables");
		book.createSheet("DI link");
		book.createSheet("AI link");
		book.createSheet("DOc link");
		book.createSheet("bi link");
		book.createSheet("AO link");
		book.createSheet("ModFail link");
				 		 
		generateACETable(plc, book, log);
		generateOneTable(plc, book, log); 
		generateIOlinks(plc, book, log);
		 
		 
		 
		 //закрытие лога
		log.close();
		fileoutputstream.close();
		 // Записываем всё в файл
	     book.write(new FileOutputStream(file));
	     book.close();
		 
	}
	 /**
	  * Генерирование ACE таблицы в Excel
	  * Основные параметры проекта
	  * @param book
	  */
	 private static void generateACETable (PLC_Motorola plc, Workbook book, PrintWriter log) {
		 
		Sheet sheet;
		log.print("Заполнение ACE таблицы начато");
		sheet = book.getSheet("ACE");
		Row row;
		Cell text;
		//Строка 0
		row = sheet.createRow(0); 
		text = row.createCell(0);
		text.setCellValue("Состав корзины:");
		
		for (int i = 0; i<plc.ListIOModules.size(); i++) {
		text = row.createCell(i+1);
		text.setCellValue(plc.ListIOModules.get(i).getName() + " " + plc.ListIOModules.get(i).getCntInputs() 
							+ "/" + plc.ListIOModules.get(i).getCntOutputs() );
		
		}
		
		{
		sheet.autoSizeColumn(0);					
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		}
		log.println("--> окончено");
	 }
	
	 
	 
	 /**
	  * Генерирование первой таблицы в Excel
	  * Расчет кол - ва строк в таблицах и основных параметров проекта
	  * @param book
	  */
	 private static void generateOneTable (PLC_Motorola plc, Workbook book, PrintWriter log) {
		 
		Sheet sheet;
		log.print("Заполнение 1 таблицы начато");
		sheet = book.getSheet("Tables");
		Row row;
		Cell text;
		//Строка 0
		row = sheet.createRow(0); 
		text = row.createCell(0);
		text.setCellValue("Таблица");
		text = row.createCell(1);
		text.setCellValue("Кол-во строк");
		//Строка 1
		row = sheet.createRow(1); 
		text = row.createCell(0);
		text.setCellValue("LocalDI");
		text = row.createCell(1);
		text.setCellValue( plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI ) );
		//Строка 2
		row = sheet.createRow(2); 
		text = row.createCell(0);
		text.setCellValue("LocalAI");
		text = row.createCell(1);
		text.setCellValue( plc.getAllCntIO(PLC_Motorola.TYPEMODULES.AI ) );	
		//Строка 3
		row = sheet.createRow(3); 
		text = row.createCell(0);
		text.setCellValue("LocalDO");
		text = row.createCell(1);
		text.setCellValue( plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DO ) );
		//Строка 4
		row = sheet.createRow(4); 
		text = row.createCell(0);
		text.setCellValue("LocalAO");
		text = row.createCell(1);
		text.setCellValue( plc.getAllCntIO(PLC_Motorola.TYPEMODULES.AO ) );
		//Строка 5
		row = sheet.createRow(5); 
		text = row.createCell(0);
		text.setCellValue("AI_mdl_num");
		text = row.createCell(1);
		for (int i = 0; i<plc.ListIOModules.size(); i++) {
			if (plc.ListIOModules.get(i).getName() == "AI") {
				text.setCellValue(1 );
				break;
			}
				
		}
		 
		sheet.autoSizeColumn(0);						
		sheet.autoSizeColumn(1);
		
		log.println("--> окончено");
	 }
	 
	 
	 /**
	  * Генерирование таблиц привязки
	  * @param book
	 * @throws IOException 
	  */

	private static void generateIOlinks (PLC_Motorola plc, Workbook book, PrintWriter log) throws IOException {
		
		Sheet sheet;
		Row row;
		Cell text;
		int iDI = 0, iAI = 0, iDO = 0, iAO = 0; //для сквозного подсчёта
		log.println("Заполняются IO links");
		log.print("   Заполнился модуль ");
		try {
			for(int i =0; i<plc.ListIOModules.size(); i++){
					
				//Таблица ошибок модулей
				sheet = book.getSheet("ModFail link");
				row = sheet.createRow(i); 
			    text = row.createCell(0); //frame
			    text.setCellValue(0);
			    text = row.createCell(1);	//position
			    text.setCellValue(plc.ListIOModules.get(i).getPosition()+1);
			    text = row.createCell(2);	//MOD_FAIL
			    text.setCellValue("MOD_FAIL");
		
				
				switch (plc.ListIOModules.get(i).getName()) {
				case "DI":
					for (int j = 0; j<plc.ListIOModules.get(i).getCntInputs(); j++){
						sheet = book.getSheet("DI link");
						row = sheet.createRow(iDI /*j+i*plc.ListIOModules.get(i).getCntInputs()*/);
					    text = row.createCell(0); //frame
					    text.setCellValue(0);
					    text = row.createCell(1);	//position
					    text.setCellValue(plc.ListIOModules.get(i).getPosition()+1);
					    text = row.createCell(2);	//input
					    text.setCellValue("IN_" + Integer.toString(j+1));
					    text = row.createCell(3);	//Keep Last
					    text.setCellValue("Keep Last");
					    text = row.createCell(5);	//0
					    text.setCellValue(0);
					    iDI++;
					}
					break;
					
				case "AI":
					for (int j = 0; j<plc.ListIOModules.get(i).getCntInputs(); j++){
						sheet = book.getSheet("AI link");
						row = sheet.createRow(iAI /*j+i*plc.ListIOModules.get(i).getCntInputs()*/);
					    text = row.createCell(0); //frame
					    text.setCellValue(0);
					    text = row.createCell(1);	//position
					    text.setCellValue(plc.ListIOModules.get(i).getPosition()+1);
					    text = row.createCell(2);	//input
					    text.setCellValue("AN_IN_" + Integer.toString(j+1));
					    text = row.createCell(3);	//COS
					    text.setCellValue(1);
					    iAI++;
					}
					break;
	
				case "DO":
					for (int j = 0; j<plc.ListIOModules.get(i).getCntOutputs(); j++){
						//doC
						sheet = book.getSheet("DOc link");
						row = sheet.createRow(iDO /*j+i*plc.ListIOModules.get(i).getCntInputs()*/);
					    text = row.createCell(0); //frame
					    text.setCellValue(0);
					    text = row.createCell(1);	//position
					    text.setCellValue(plc.ListIOModules.get(i).getPosition()+1);
					    text = row.createCell(2);	//output
					    text.setCellValue("OUT_" + Integer.toString(j+1));
					    text = row.createCell(3);	//COS
					    text.setCellValue("Keep Last");
					    text = row.createCell(5);	//COS
					    text.setCellValue(0);
					    //bi
					    sheet = book.getSheet("bi link");
						row = sheet.createRow(iDO /*j+i*plc.ListIOModules.get(i).getCntInputs()*/);
					    text = row.createCell(0); //frame
					    text.setCellValue(0);
					    text = row.createCell(1);	//position
					    text.setCellValue(plc.ListIOModules.get(i).getPosition()+1);
					    text = row.createCell(2);	//output
					    text.setCellValue("BI_" + Integer.toString(j+1));				    
					    
					    iDO++;
					}
					break;
	
				case "AO":
					for (int j = 0; j<plc.ListIOModules.get(i).getCntOutputs(); j++){
						sheet = book.getSheet("AO link");
						row = sheet.createRow(iAO /*j+i*plc.ListIOModules.get(i).getCntInputs()*/);
					    text = row.createCell(0); //frame
					    text.setCellValue(0);
					    text = row.createCell(1);	//position
					    text.setCellValue(plc.ListIOModules.get(i).getPosition()+1);
					    text = row.createCell(2);	//output
					    text.setCellValue("AO_" + Integer.toString(j+1));
					    text = row.createCell(3);	//COS
					    text.setCellValue("Keep Last");
					    text = row.createCell(5);	//COS
					    text.setCellValue(0);
					    iAO++;
					}
					break;
	
				default:
					System.out.println("найдены не типовые модули в generateIOlinks ");
					break;
				}
				log.print("  №" + Integer.toString(i+1) + "   " + plc.ListIOModules.get(i).getName() + " | ");	
			}

		}
		catch (NullPointerException io) {
			System.out.println(io);
			
		}
		log.println();
		log.println("Заполнились IO links");

	}
	
	
	

}//*******end class






















