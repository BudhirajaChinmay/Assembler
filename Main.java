package Assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		SymbolTable s = new SymbolTable();
		LabelTable l = new LabelTable();
		literal_table lit = new literal_table();
		String file;
		File f;
		Scanner a = new Scanner(System.in);
		
		BufferedReader reader;
		try {
			
			System.out.println("Enter the File Name : ");
			file = a.next();
			f = new File("/home/chinmay/eclipse-workspace/Assembler/src/Assembler/" + file);
			//System.out.println(f.getName());
			if (!f.exists()) {
				
				try {
					throw (new FileNotFound());
				}
				catch(Exception e) {
					System.out.println(e);
				}
			}
			
			else {
				reader = new BufferedReader(new FileReader("/home/chinmay/eclipse-workspace/Assembler/src/Assembler/" + file));
				passOne p1 = new passOne(reader, s, l, lit,f);			// Parameters :- Input file, symbol table , label table, literal table
				
				p1.passone();
//				s.print();
//				l.print();
//				lit.print();
			
				PassTwo p2 = new PassTwo(s,l,lit,file);   		// Parameters :- Input file, Output file, symbol table , label table, literal table
				p2.passtwo();
//				String line = reader.readLine();
//				
//				while (line != null) {
//					System.out.println(line);
//					// read next line
//					line = reader.readLine();
//				}
//				
				reader.close();
			}
			
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
