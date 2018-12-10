package com.motorola.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateConfig {
	 
	
	
	
	public static void newConfig(File file, PLC_Motorola plc) throws FileNotFoundException, IOException {
		
		 Workbook book = new HSSFWorkbook();
		 book.createSheet("Tables");
		 book.createSheet("DI link");
		 book.createSheet("AI link");
		 book.createSheet("DOc link");
		 book.createSheet("bi link");
		 book.createSheet("AO link");
		 book.createSheet("ModFail link");
				 		 
		 
		 generateOneTable(plc, book);
		 
		 generateIOlinks(plc, book);
		 
		 
		 
		 
		 // Записываем всё в файл
	     book.write(new FileOutputStream(file));
	     book.close();
		 
	}
	 
	 /**
	  * Генерирование первой таблицы в Excel
	  * Расчет кол - ва строк в таблицах и основных параметров проекта
	  * @param book
	  */
	 private static void generateOneTable (PLC_Motorola plc, Workbook book) {
		 
		Sheet sheet;
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
				
		sheet.autoSizeColumn(1);
	 }
	 
	 
	 
	 
	 /**
	  * Генерирование таблиц привязки
	  * @param book
	  */
	@SuppressWarnings("unused")
	private static void generateIOlinks (PLC_Motorola plc, Workbook book) {
		
		Sheet sheet;
//		sheet = book.getSheet("DI link");
		Row row;
		Cell text;
		int iDI = 0, iAI = 0, iDO = 0, iAO = 0;
		
		for(int i =0; i<plc.ListIOModules.size(); i++){
			
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
				System.out.println(" Заполняется DI");
				for (int j = 0, k = 0; j<plc.ListIOModules.get(i).getCntInputs(); j++){
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
				System.out.println(" Заполняется AI");
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
				System.out.println(" Заполняется DO");
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
				System.out.println(" Заполняется AO");
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
			
		}
		
			


	}
	
	
	

}//*******end class






















/* Sheet sheet = book.getSheet("DI link");

// Нумерация начинается с нуля
   Row row = sheet.createRow(0); 
   
   // Мы запишем имя и дату в два столбца
   // имя будет String, а дата рождения --- Date,
   // формата dd.mm.yyyy
   Cell name = row.createCell(0);
   name.setCellValue("John");
   
   Cell birthdate = row.createCell(1);
   
   DataFormat format = book.createDataFormat();
   CellStyle dateStyle = book.createCellStyle();
   dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
   birthdate.setCellStyle(dateStyle);
   

   // Нумерация лет начинается с 1900-го
   birthdate.setCellValue(new Date(110, 10, 10));
   
   // Меняем размер столбца
   sheet.autoSizeColumn(1);
   
   
   sheet = book.getSheet("AI link");
   row = sheet.createRow(0); 
   
   // Мы запишем имя и дату в два столбца
   // имя будет String, а дата рождения --- Date,
   // формата dd.mm.yyyy
   name = row.createCell(0);
   name.setCellValue("John");
   
   birthdate = row.createCell(1);
   
   format = book.createDataFormat();
   dateStyle = book.createCellStyle();
   dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
   birthdate.setCellStyle(dateStyle);
   

   // Нумерация лет начинается с 1900-го
   birthdate.setCellValue(new Date(110, 10, 10));
   
   // Меняем размер столбца
   sheet.autoSizeColumn(1);
   
   */


