package com.motorola.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadSaveConfig {
	
	enum XmlErr {NOERR, POSITIONERR, NOCORRECTMODULE};
	
	/**
	 * Парсинг всего XML	
	 * @param file
	 * @param plc
	 * @return
	 * @throws IOException
	 */
	public static XmlErr ReadAllXML (File file, PLC_Motorola plc) throws IOException {
		
		FileOutputStream fileoutputstream = new FileOutputStream("f:/java-workspase/Outputs/MotoXMLLog.log") ;
		PrintWriter log = new PrintWriter(fileoutputstream);
		log.println("Лог ReadAllXML()");
		
		ReadKPinXML(file, plc, log);
		ReadModulesInXML(file, plc, log);
		ReadZDVInXML(file, plc, log);
		
		
		log.close();
		fileoutputstream.close();
		
		return XmlErr.NOERR;
	}
	
	/**
	 * Чтение из файла xml номера и километра КП
	*/
	private static XmlErr ReadKPinXML (File file, PLC_Motorola plc, PrintWriter log) {
		
		Node root_kpnumkm = null;
		log.print("Чтение КП начато");
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// Создается дерево DOM документа из файла
        	Document document = documentBuilder.parse(file);

        	// Получаем корневой элемент
        	Node root = document.getDocumentElement();
        	NodeList nodelist = root.getChildNodes();
	            	        
        	root_kpnumkm = findNameNode(nodelist,0, "KP_NUM_KM");		//поиск узла  KP_NUM_KM
	        if (root_kpnumkm == null) {								
	        	System.out.println("No <KP_NUM_KM> in xml file");
	        }
	        else {													//если узел найден
	        	NodeList kpnumkmList = root_kpnumkm.getChildNodes();
	        	for (int i=0; i < kpnumkmList.getLength()  ; i++) {
	        		Node node = kpnumkmList.item(i);
	        		if (node.getNodeType() != Node.TEXT_NODE ) {    // если узел не нетекстовый
	        			if (node.getNodeName() == "NUM") {       
//	        				System.out.println(node.getNodeName() + "  " + type);
	        				plc.setKp(Integer.valueOf(node.getChildNodes().item(0).getTextContent()));
	        				continue;
	        			}
	        			else if (node.getNodeName() == "KM") {
//	        				System.out.println(node.getNodeName() + "  " +type);
	        				plc.setKm(Integer.valueOf(node.getChildNodes().item(0).getTextContent()));
	        				continue;
	        			}
	           		}
	        	}
	        }
	       
		} catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
		log.println(" --> окончено");
		return XmlErr.NOERR;
	}
	
	/**
	 * Чтение из файла xml модулей
	 * return List<IOModule>  список модулей
	 * @return 
	*/ 
	private static XmlErr ReadModulesInXML (File file, PLC_Motorola plc, PrintWriter log) {
		
		Node root_modules = null;
		NodeList root_listmodules = null;
		List<IOModule> ListIOModules = new LinkedList<IOModule>();
		log.print("Чтение модулей начато");
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// Создается дерево DOM документа из файла
        	Document document = documentBuilder.parse(file);

        	// Получаем корневой элемент
        	Node root = document.getDocumentElement();
        	NodeList nodelist = root.getChildNodes();
        
	        	root_modules = findNameNode(nodelist,0, "IOModules");		//поиск узла c нуля IOModules
		        if (root_modules == null) {								
		        	System.out.println("No IOModules in xml file");
		        }
		        else {														//если узел найден
		        	root_listmodules = root_modules.getChildNodes();		//вошли в IOModules
//		        	System.out.println(root_listmodules.getLength());
		        	for (int i=0; i<root_listmodules.getLength(); i++){		//проход по всем модулям Modul	
			        	Node modul = root_listmodules.item(i);			        
			        	IOModule iomodule = new IOModule( );				//Создаем новый объект модуля
//			        	System.out.println(modul.getNodeName());
			        	if (modul.getNodeType() != Node.TEXT_NODE ) {
			        		NodeList listproperty = modul.getChildNodes();	//Заход в Modul
				        	for (int j=0; j <listproperty.getLength(); j++){
				        		Node property = listproperty.item(j);		//Свойство
				        		if (property.getNodeType() != Node.TEXT_NODE){
//				        		System.out.println(property.getNodeName()+ "=" + property.getChildNodes().item(0).getTextContent());
				        			//заполнение свойств модуля
				        			if (property.getNodeName() == "Position"){
				        				iomodule.setPosition(Integer.valueOf(property.getChildNodes().item(0).getTextContent()));
//				        				iomodule.setPosition(Integer.valueOf("0"));
				        			}			        			
				        			if (property.getNodeName() == "CntInputs"){
				        				iomodule.setCntInputs((Integer.valueOf(property.getChildNodes().item(0).getTextContent())));
				        			}
				        			if (property.getNodeName() == "CntOutputs"){
				        				iomodule.setCntOutputs(Integer.valueOf(property.getChildNodes().item(0).getTextContent()));
				        			}
				        			if (property.getNodeName() == "Name"){
				        				iomodule.setName(property.getChildNodes().item(0).getTextContent());
				        			}			
				        		}				        		
				        	}
				        	ListIOModules.add(iomodule);
			        	}
		        		
		        	}	
		        }
	        
		} catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }	
		
		plc.ListIOModules = ListIOModules;
		
//		проверка на одинаковую позицию модулей
		if (CheakIOModules(plc.ListIOModules) == XmlErr.POSITIONERR) {
			log.println("Совпадение позиций модулей");   
		}
		
		log.println("--> окончено");
		return XmlErr.NOERR;
		
		
	}
		
	/**
	 * Парсинг задвижек в XML
	 * @param file
	 * @param plc
	 * @param log
	 * @return
	 */	
	private static XmlErr ReadZDVInXML (File file, PLC_Motorola plc, PrintWriter log) {
		
		Node root_valves = null;
		NodeList root_listvalves = null;
		List<Valve> ListValves = new LinkedList<Valve>();
		log.print("Чтение задвижек начато");
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// Создается дерево DOM документа из файла
        	Document document = documentBuilder.parse(file);

        	// Получаем корневой элемент
        	Node root = document.getDocumentElement();
        	NodeList nodelist = root.getChildNodes();
        
        	root_valves = findNameNode(nodelist,0, "Valves");		//поиск узла c нуля Valves
		        if (root_valves == null) {								
		        	System.out.println("No Valves in xml file");
		        }
		        else {														//если узел найден
		        	root_listvalves = root_valves.getChildNodes();			//вошли в Valves
//		        	System.out.println(root_listvalves.getLength());
		        	for (int i=0; i<root_listvalves.getLength(); i++){		//проход по всем модулям Valve	
			        	Node node = root_listvalves.item(i);			        
			        	Valve valve = new Valve( );							//Создаем новый объект модуля
			        	if (node.getNodeType() != Node.TEXT_NODE ) {
			        		NodeList listproperty = node.getChildNodes();	//Заход в Valve
				        	for (int j=0; j <listproperty.getLength(); j++){
				        		Node property = listproperty.item(j);		//Свойство
				        		if (property.getNodeType() != Node.TEXT_NODE){
//				        		System.out.println(property.getNodeName()+ "=" + property.getChildNodes().item(0).getTextContent());
				        			//заполнение свойств модуля
				        			if (property.getNodeName() == "CSPA"){
				        				valve.setCSPA(Boolean.valueOf(property.getChildNodes().item(0).getTextContent() ));
				        			}			        			
				        				        		
				        		}
				        	
				        	}
				        	ListValves.add(valve);
			        	}	
			        	
		        	}
		        }
		
	        
		} catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }	
		
		plc.ListValves = ListValves;
		
		log.println("--> окончено");
		return XmlErr.NOERR;
		
		
	}
	
	/** 
	 * Проверка на совпадение позиции модулей
	 * @param ListIOModules
	 * @return err enum XmlErr
	 */
	private static XmlErr CheakIOModules (List<IOModule> ListIOModules){
		
		int c;
		int cnt = 0;
		for (int i = 0; i < ListIOModules.size(); i++) {
			c = ListIOModules.get(i).getPosition();
			for (int j = i+1; j < ListIOModules.size(); j++) {
				if (c == ListIOModules.get(j).getPosition()) {
					cnt++;
				}
			}
		}
		
		if (cnt != 0)
		{
			return XmlErr.POSITIONERR;
		}
		
		
		return null;
		
	}

	/**
	 * Find in NodeList - Node with next name
	 * @param nodelist 
	 * @param StartIndex 
	 * @param name
	 * @return Node
	 */
	private static Node findNameNode (NodeList nodelist, int StartIndex, String name ) {
		for (int i=0; i< nodelist.getLength(); i++ ) {
	     	Node tag = nodelist.item(i);
	       	if (tag.getNodeName() == name){
	       		return tag;
	        }
	    }
		return null;
	}

}//*****************************************************************************************************************
