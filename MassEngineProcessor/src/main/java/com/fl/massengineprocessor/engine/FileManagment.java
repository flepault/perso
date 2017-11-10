package com.fl.massengineprocessor.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.codec.binary.Base64;

public class FileManagment {
	
	private static Logger log = Logger.getLogger(FileManagment.class.getName());
	
	public static void createFile(String line){
		PrintWriter writer;
		try {
			writer = new PrintWriter("files/"+new String(Base64.encodeBase64(line.getBytes()))+".in", "UTF-8");
			writer.append(line);
			writer.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		} 
		
	}
	
	public static void renameFile(String request){
		File f = new File("files/"+new String(Base64.encodeBase64(request.getBytes()))+".in");		
		f.renameTo(new File("files/"+new String(Base64.encodeBase64(request.getBytes()))+".out"));
	}
	
	public static void deleteFile(String request){
		File f = new File("files/"+new String(Base64.encodeBase64(request.getBytes()))+".out");		
		f.delete();
	}
	
	private static void loadFile(Stack<String> queue,String type){
		for (File fileEntry : new File("files/").listFiles()) {	

			if(fileEntry.getName().endsWith(type))	{			
			
				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(fileEntry));
					queue.add(br.readLine());
					br.close();
					
				} catch (Exception e) {
					log.log(Level.SEVERE, e.getMessage());
				} 
			}
		}
	}
	
	public static void loadInputFile(Stack<String> queue){
		loadFile(queue, ".in");
	}
	
	public static void loadOutputFile(Stack<String> queue){
		loadFile(queue, ".out");
	}
	

}
