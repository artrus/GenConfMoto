package com.motorola.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.MathContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateConfig {
	 
	private static int cntLogicStartBit = 32;
	private static int cntTSinValve = 72;
	private static int cntAllTableTS = 5*248;
	//Стартовый блок TS
	private static String[] namesStartLogicTS = {"MainFail", "BatFal", "ClockValid", "ErrLog", 
											"I/O_Fl", "fFalse", "fFalse", "control_fault"};
	//Имена всяких переменных
	private static String[] ConstNamesLogicTS = {"fFALSE", "fTRUE", "ModFail", "di", 
												"VLV_LogicDI", "VLV_OUT_TS", "AND_ORResult", "rez"};
	
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
		book.createSheet("TS");
		
		generateACETable(plc, book, log);
		generateOneTable(plc, book, log); 
		generateIOlinks(plc, book, log);
		generateTStable(plc, book, log);
		 
		 
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
		text.setCellValue("ModFail");
		text = row.createCell(1);
		text.setCellValue( plc.ListIOModules.size());
		
		//Строка 8 Таблица ApplParam
		row = sheet.createRow(7); 
		text = row.createCell(0);
		text.setCellValue("Таблица ApplParam");
		text = row.createCell(1);
		text.setCellValue("Значение");
		//Строка 9 AI_mdl_num
		row = sheet.createRow(8); 
		text = row.createCell(0);
		text.setCellValue("AI_mdl_num");
		text = row.createCell(1);
		for (int i = 0; i<plc.ListIOModules.size(); i++) {
			String str = plc.ListIOModules.get(i).getName();
			if ( str.matches("AI")) {
				text.setCellValue(i+1);
//				System.out.println("Индекс AI модуля=" + Integer.toString(i+1)  );
				break;
			}
		}
		
		//Строка 10 CountTS
		row = sheet.createRow(9); 
		text = row.createCell(0);
		text.setCellValue("CountTS");
		text = row.createCell(1);				
		text.setCellValue(getCountTS(plc));
		
		
		//Строка 11 CmdRebootNum
		row = sheet.createRow(10); 
		text = row.createCell(0);
		text.setCellValue("CmdRebootNum");
		text = row.createCell(1);				
		
		
		
		sheet.autoSizeColumn(0);						
		sheet.autoSizeColumn(1);
		
		log.println("--> окончено");
	 }
	 
	 
	 
	 /**
	  * Генерирование таблиц привязки
	  * @param book
	 * @throws IOException 
	  */

	private static void generateIOlinks (PLC_Motorola plc, Workbook book, PrintWriter log)  {
		
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

	/**
	 * Заполнение таблицы TS
	 * @param plc
	 * @param book
	 * @param log
	 */
	
	private static void generateTStable (PLC_Motorola plc, Workbook book, PrintWriter log)  {
		
		Sheet sheet;
		Row row;
		Cell text;
		int LastIndexTables = 248;   //максимальный размер таблицы в STS
		int iRow = 0, iCell = 0;	//для проверки на максимальную строку в STS
		int iVLV_Logic = 0;			//индекс таблицы для записи LogicDI
		int iVLV_OUT_TS = 0;		//Префикс _1-127
		log.print("Заполняется таблица TS  CountTS=" + Integer.toString(getCountTS(plc)));
		//Таблица TS
		sheet = book.getSheet("TS");
		try {
			//Создание необходимых строк
			for (int i = 0; i<LastIndexTables+1; i++)	sheet.createRow(i);
			//Цикл со всеми TS
			for(int i =0; i<cntAllTableTS/*getCountTS(plc)*/ ; i++){		
				row = sheet.getRow(iCell); 
			    text = row.createCell(iRow); 
			    
			    //Копирование первых константых 8 TS
			    if (i<8) text.setCellValue(namesStartLogicTS[i]); 
			    //ModFail,x
			    if ((i>=8) && (i<plc.ListIOModules.size()+8)) {
			    	text.setCellValue(ConstNamesLogicTS[2] + "," + Integer.toString(plc.ListIOModules.get(i-8).getPosition()) );
			    }
			    //все fFalse до 32го TS 
			    if ((i>plc.ListIOModules.size()-1+8 ) && (i<cntLogicStartBit)){
			    	text.setCellValue(ConstNamesLogicTS[0]);
			    }
			    //заполнение di
			    if ((i>=cntLogicStartBit) && (i<cntLogicStartBit + plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI))) {
			    	text.setCellValue(ConstNamesLogicTS[3] + "," + Integer.toString(i-cntLogicStartBit) );
			    }
			    //заполнение задвижек
			    if ((i>=cntLogicStartBit + plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI)) && 
			    		((i<cntLogicStartBit + plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI) 
			    				+ plc.ListValves.size()*cntTSinValve))) {
			    	
//			    	text.setCellValue(ConstNamesLogicTS[4] + "," );
			    	text.setCellValue(getNameValveTS(plc, cntLogicStartBit + plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI), i));
			    	
			    	
			    }
			    
			    
			    //остальные fFalse...
			    if ((i>=cntLogicStartBit + plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI) +
			    			plc.ListValves.size()*cntTSinValve)) {
			    	text.setCellValue(ConstNamesLogicTS[0]);
			    }
			    
			    
			    iCell++;
			    if (iCell==LastIndexTables) { //проверка на максимальное число строк в STS
					iRow++;
					iCell = 0;
				}			    
			}
		
		} catch (NullPointerException io) {
			System.out.println(io);
			
		}
		
		for (int i=0; i<5; i++)	sheet.autoSizeColumn(i);	//выравнивание
		log.print("  --> окончено ");

	}
	
	
	/**
	 * Подсчет countTS
	 * @param plc
	 * @return
	 */
	public static int getCountTS(PLC_Motorola plc) { 
		
		return cntLogicStartBit + plc.getAllCntIO(PLC_Motorola.TYPEMODULES.DI) 
				+ cntTSinValve*plc.ListValves.size();
	}	
	
	
	/**
	 * Получение String из текущей позиции записи в TS
	 * @param plc
	 * @param iStartinTS
	 * @param iCur
	 * @return
	 */
	public static String getNameValveTS (PLC_Motorola plc, int iStartinTS, int iCur) {
		String str = null;
		int cntLogicValve = 4;		//кол-во логических TS задвижки LogicDI
		int param1= 0, param2 = 0;
		int k = iCur - iStartinTS;
		int curZDV  =k / cntTSinValve;
		if (k>143)	k = k - (curZDV)*72;
				
		if (((k>=0) && (k<cntLogicValve)) /*|| ((k>=64) && (k<=64+cntLogicValve))*/) {
			str = ConstNamesLogicTS[4] + "," + Integer.toString(10*curZDV+curZDV+k);
			System.out.println(curZDV);
			System.out.println(str);
		}
		else if ((k>=cntTSinValve) && (k<cntTSinValve+cntLogicValve)) {
			str = ConstNamesLogicTS[4] + "," + Integer.toString(10*curZDV+curZDV+k-72);
			System.out.println(curZDV);
			System.out.println(str);
		}
		
 		return str ;
	}

}//*******end class




