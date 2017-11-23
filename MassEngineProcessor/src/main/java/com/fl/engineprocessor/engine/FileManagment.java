package com.fl.engineprocessor.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManagment {

	private static Logger log = Logger.getLogger(FileManagment.class.getName());

	public static String stringToHex(String base)
	{
		StringBuffer buffer = new StringBuffer();
		int intValue;
		for(int x = 0; x < base.length(); x++)
		{
			int cursor = 0;
			intValue = base.charAt(x);
			String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
			for(int i = 0; i < binaryChar.length(); i++)
			{
				if(binaryChar.charAt(i) == '1')
				{
					cursor += 1;
				}
			}
			if((cursor % 2) > 0)
			{
				intValue += 128;
			}
			buffer.append(Integer.toHexString(intValue) + " ");
		}
		return buffer.toString();
	}

	public static void createFile(String line){
		PrintWriter writer;
		try {


			//new String(Base64.encodeBase64(line.getBytes()))

			writer = new PrintWriter("files/"+stringToHex(line)+".in", "UTF-8");
			writer.append(line);
			writer.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		} 

	}

	public static void renameFile(String request){

		File f = new File("files/"+stringToHex(request)+".in");		
		f.renameTo(new File("files/"+stringToHex(request)+".out"));
	}

	public static void deleteFile(String request){
		File f = new File("files/"+stringToHex(request)+".out");		
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
